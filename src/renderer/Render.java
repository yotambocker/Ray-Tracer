package renderer;

import primitives.*;
import scene.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * class Render - represent a renderer
 */
public class Render {
	/**
	 * field
	 */
	private Scene _scene;
	private ImageWriter _imageWriter;
	private Boolean thread = true;
	private int numOfRays = 20;
	private boolean _gridOpt = false;
	private Grid _grid;
	private double _lambdaGrid = 5.0;

	private static final double MIN_CALC_COLOR_K = 0.005;
	private static final int MAX_CALC_COLOR_LEVEL = 5;

	/**
	 * A constructor that get the image-writer and the scene
	 * 
	 * @param iw - The image-writer
	 * @param s  - The scene
	 */
	public Render(ImageWriter imageWriter, Scene s) {
		_imageWriter = imageWriter;
		_scene = s;
	}

	/**
	 * Getter of GridOpt
	 * 
	 * @return true if we want to use grid
	 */
	public boolean getGridOpt() {
		return _gridOpt;
	}

	/**
	 * Getter of LambdaGrid
	 * 
	 * @return LambdaGrid
	 */
	public double getLambdaGrid() {
		return _lambdaGrid;
	}

	/**
	 * Getter of image-writer
	 * 
	 * @return _imageWriter
	 */
	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * Getter of the scene
	 * 
	 * @return - The scene
	 */
	public Scene getScene() {
		return _scene;
	}

	/**
	 * setter for LambdaGrid
	 * 
	 * @param lambdaGrid
	 */
	public void setLambdaGrid(double lambdaGrid) {
		_lambdaGrid = lambdaGrid;
	}

	/**
	 * setter for GridOpt (true if we want to use grid)
	 * 
	 * @param gridOpt
	 */
	public void setGridOpt(boolean gridOpt) {
		_gridOpt = gridOpt;
		if (gridOpt)
			_grid = new Grid(_scene.getGeometries(), getLambdaGrid());
	}

	/**
	 * setter for numOfRays
	 * 
	 * @param num
	 */
	public void setDiffusionRays(int num) {
		numOfRays = num;
	}

	/**
	 * Disable multithreading
	 * 
	 * @param num
	 */
	public void disableMultiThreading() {
		thread = false;
	}

	
	
	private int counter;
	private int prev;

	/**
	 * A function that render the image
	 * 
	 * @throws Exception
	 */
	public void renderImage() {
		double screenW = _imageWriter.getWidth();
		double screenH = _imageWriter.getHeight();
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		double screenD = _scene.getScreenDistance();
		java.awt.Color background = _scene.getBackground().getColor();
		ThreadPoolExecutor executor = null;
		if (thread) {
			int cores = Runtime.getRuntime().availableProcessors();
			executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(cores);
		}
		final int total = nX * nY;
		counter = prev = 0;
		// DecimalFormat df = new DecimalFormat("00.0");
		for (int i = 0; i < nY; i++) { // columns
			final int fi = i;
			Runnable worker = () -> {
				for (int j = 0; j < nX; j++) {
					++counter;
					int check = 1000 * counter / total;
					if (check != prev) {
						prev = check;
						System.out.println((double) check / 10);
					}
					Ray ray = _scene.getCamera().constructRayThroughPixel(nX, nY, fi, j, screenD, screenW, screenH);
					GeoPoint intersection = findClosestIntersection(ray);
					_imageWriter.writePixel(j, fi,
							intersection == null ? background : calcColor(intersection, ray).getColor());

				}
			};
			if (thread)
				executor.execute(worker);
			else
				worker.run();
		}
		if (thread) {
			executor.shutdown();
			try {
				while (!executor.awaitTermination(300, TimeUnit.SECONDS))
					;
			} catch (InterruptedException e) {
			}
		}

	}

	
	/**
	 * calcColor from renderImage() function. call for the other calcColor function
	 * @param geopoint
	 * @param inRay
	 * @return
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay) {
		return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(_scene.getAmbientLight().getIntensity());
	}
	
	
	/**
	 * A function that get a geoPoint and calculate the color with the right
	 * intensity of that point
	 * 
	 * @param point - The point
	 * @return the color of the pixel with the right Intensity
	 */
	private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
		if (level == 0 || k < MIN_CALC_COLOR_K)
			return Color.BLACK;

		Color color = intersection.geometry.getEmission();
		Vector v = inRay.getDirection();
		Vector n = intersection.geometry.getNormal(intersection.point);
		int nShininess = intersection.geometry.getMaterial().getNShininess();
		double kd = intersection.geometry.getMaterial().getKd();
		double ks = intersection.geometry.getMaterial().getKs();
		double kr = intersection.geometry.getMaterial().getKr();
		double kt = intersection.geometry.getMaterial().getKt();
		double rr = intersection.geometry.getMaterial().getRr();
		double rt = intersection.geometry.getMaterial().getRt();
		for (LightSource lightSource : _scene.getLight()) {
			Vector l = lightSource.getL(intersection.point);
			if (n.dotProduct(l) * n.dotProduct(v) > 0) {
				double ktr = transparency(l, n, intersection);
				if (!Util.isZero(ktr * k)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}

		double kk = kr * k;
		if (kk > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, intersection.point, v);
			if (reflectedRay != null) {
				List<Ray> rays = createRayList(reflectedRay, n, numOfRays, rr);
				color = color.add(calcColorForList(rays, kk, level - 1));
			}
		}

		kk = kt * k;
		if (kk > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, intersection.point, v);
			List<Ray> rays = createRayList(refractedRay, n, numOfRays, rt);
			color = color.add(calcColorForList(rays, kk, level - 1));
		}
		return color;
	}

	/**
	 * calcColor For List of rays
	 * @param rayList
	 * @param kk
	 * @param level
	 * @return
	 */
	Color calcColorForList(List<Ray> rayList, double kk, int level) {
		Color color = Color.BLACK;
		for (Ray ray : rayList) {
			GeoPoint gp = findClosestIntersection(ray);
			if (gp != null)
				color = color.add(calcColor(gp, ray, level, kk));
		}
		return color.reduce(rayList.size());
	}

	/**
	 * glossy ray that get "numOfRays" of random point and get average color of them
	 * for Reflected and Refracted Rays
	 * 
	 * @param ray
	 * @return
	 */
	public List<Ray> createRayList(Ray ray, Vector n, int count, double radius) {
		List<Ray> list = new LinkedList<>(Collections.singletonList(ray));
		if (count == 0 || Util.isZero(radius))
			return list;
		Vector v = ray.getDirection();
		Point3D p0 = ray.getP0();
		double x0 = p0.getX().get();
		double y0 = p0.getY().get();
		double z0 = p0.getZ().get();
		Vector vX, vY;

		if (x0 <= y0 && x0 <= z0) {
			vX = new Vector(0, -z0, y0).normalize();
		} else if (y0 <= x0 && y0 <= z0) {
			vX = new Vector(z0, 0, -x0).normalize();
		} else if (y0 == 0 && x0 == 0) {
			vX = new Vector(1, 1, 0).normalize();
		} else {
			vX = new Vector(y0, -x0, 0).normalize();
		}
		vY = v.crossProduct(vX).normalize();

		Point3D pc = p0.add(v);
		int counter = count;
		while (counter > 0) {
			double xFactor = ThreadLocalRandom.current().nextDouble(-1, 1);
			double yFactor = Math.sqrt(1 - xFactor * xFactor);
			Point3D p = pc;
			if (!Util.isZero(xFactor)) {
				p = p.add(vX.scalarMult(xFactor));
			}
			if (!Util.isZero(yFactor)) {
				p = p.add(vY.scalarMult(yFactor));
			}
			double scale = 0;
			while (Util.isZero(scale))
				scale = ThreadLocalRandom.current().nextDouble(-radius, radius);
			p = pc.add(p.sub(pc).scalarMult(scale));
			Vector v1 = p.sub(p0);
			if (v.dotProduct(n) * v1.dotProduct(n) > 0) {
				--counter;
				list.add(new Ray(p0, v1));
			}
		}
		return list;
	}




	/**
	 * find Closest Intersection
	 * 
	 * @param ray
	 * @return GeoPoint the closestPoint
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersectionPoints = _gridOpt ? _grid.findIntersection(ray)
				: _scene.getGeometries().findIntersections(ray);
		return intersectionPoints.isEmpty() ? null : getClosestPoint(intersectionPoints);
	}

	/**
	 * get Closest Point
	 * 
	 * @param intersectionPoints - A list of intersection points
	 * @return the Closest Point from the list
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
		double distance = Double.MAX_VALUE;
		Point3D P0 = _scene.getCamera().getP0();
		GeoPoint minDistancePoint = null;
		for (GeoPoint point : intersectionPoints) {
			double d = P0.distance(point.point);
			if (d < distance) {
				minDistancePoint = point;
				distance = d;
			}
		}
		return minDistancePoint;
	}

	/**
	 * construct Refracted Ray
	 * 
	 * @param geoPoint
	 * @param inRay
	 * @return
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Vector direction) {
		return new Ray(point, direction, n);
	}

	/**
	 * construct Reflected Ray
	 * 
	 * @param n
	 * @param point
	 * @param inRay
	 * @return
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Vector v) {
		double a = 2 * v.dotProduct(n);
		if (Util.isZero(a))
			return null;
		Vector rVector = v.sub(n.scalarMult(a));
		return new Ray(point, rVector, n);
	}

	/**
	 * check transparency and shadow on the point
	 * 
	 * @param l
	 * @param n
	 * @param geopoint
	 * @return
	 */
	private double transparency(Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scalarMult(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);
		List<GeoPoint> intersections = _gridOpt ? _grid.findIntersection(lightRay)
				: _scene.getGeometries().findIntersections(lightRay);
		double ktr = 1;
		for (GeoPoint gp : intersections)
			ktr *= gp.geometry.getMaterial().getKt();
		return ktr;
	}

	/**
	 * A function that get all the parameters and return the correct color with the
	 * correct intensity
	 * 
	 * @param ks             - Specular attenuation coefficient
	 * @param l              - The 'L' vector
	 * @param n              - The 'N' vector
	 * @param v              - The 'V' vector
	 * @param nShininess     - Refraction index
	 * @param lightIntensity - The light's intensity
	 * @return - The correct color
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.sub(n.scalarMult(2 * l.dotProduct(n))).normal();
		double max = -1 * (v.dotProduct(r));
		if (max <= 0)
			return Color.BLACK;
		return lightIntensity.scale(Math.pow(max, nShininess) * ks);
	}

	/**
	 * calcDiffusive: lightIntensity*|l*n*kd|
	 * 
	 * @param kd
	 * @param l
	 * @param n
	 * @param lightIntensity
	 * @return
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale(Math.abs(l.dotProduct(n) * kd));
	}

	/**
	 * write to image
	 */
	public void writeToImage() {
		_imageWriter.writeToimage();
	}

	/**
	 * A function that print a white grid on the picture
	 * 
	 * @param interval - The size of a grid slot
	 */
	public void printGrid(int interval) {
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				if (i % interval == 0 || j % interval == 0)
					_imageWriter.writePixel(i, j, java.awt.Color.WHITE);
			}
		}
	}

}
