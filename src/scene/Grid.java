package scene;

import primitives.*;
import geometries.*;
import static geometries.Intersectable.GeoPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represent a grid that hold the scene in it for Shortcut running
 * time
 */
public class Grid {
	
	private Box box;
	private Point3D min, max;
	private Voxel voxMaxIndex, voxMinIndex;// The two Voxel's in the extreme corners of the grid
	private double gridResX, gridResY, gridResZ; // Resolution of the grid
	private double voxSizeX, voxSizeY, voxSizeZ; // Size of Voxel
	private Voxel voxels[];

	/**
	 * An internal class that represent a 3D cell in the grid
	 */
	private class Voxel {

		private int _x, _y, _z;
		private Geometries _geometries = new Geometries();

		/**
		 * A constructor that get 3 parameters
		 * 
		 * @param x - first parameter
		 * @param y - second parameter
		 * @param z - third parameter
		 */
		private Voxel(int x, int y, int z) {
			_x = x;
			_y = y;
			_z = z;
		}

		/**
		 * A function that insert An geometry (intersectable) to the list
		 * 
		 * @param intersectable - the geometry we want to insert
		 */
		void insert(Intersectable intersectable) {
			_geometries.add(intersectable);
		}

		/**
		 * A function that get a ray and map of geometry and boolean and find all the
		 * intersection points
		 * 
		 * @param ray     - the ray that we check if it intersect
		 * @param pathRay - a map of geometry and boolean that indicates if we already
		 *                check its intersections
		 * @return - A list of intersection points (GeoPoints)
		 */
		public List<GeoPoint> findIntersections(Ray ray, Map<Geometry, Boolean> pathRay) {
			List<GeoPoint> intersections = new ArrayList<>();
			for (Intersectable intersectable : _geometries.getGeometries())
				if (pathRay.get(intersectable) == null || pathRay.size() == 0 || !pathRay.get(intersectable)) {
					pathRay.put((Geometry) intersectable, true);
					intersections.addAll(intersectable.findIntersections(ray));//can be empty
				}
			return intersections;
		}

	}
///////////end of class Voxel//////////////


	/**
	 * A constructor that get the parameters
	 * 
	 * @param geometries - A geometries
	 * @param lambda     - A factor of the resolution of the grid [3,5]
	 */
	public Grid(Geometries geometries, double lambda) {
		box = new Box(geometries.getMin(), geometries.getMax());
		max = box.getMax();
		min = box.getMin();
		// calculates the size of the grid
		double gridSizeX = max.getX().get() - min.getX().get();
		double gridSizeY = max.getY().get() - min.getY().get();
		double gridSizeZ = max.getZ().get() - min.getZ().get();

		// make sure that the grid has some volume
		if (Util.isZero(gridSizeX))
			gridSizeX = 0.01;
		if (Util.isZero(gridSizeY))
			gridSizeY = 0.01;
		if (Util.isZero(gridSizeZ))
			gridSizeZ = 0.01;

		double cubeRoot = Math.pow(lambda * geometries.numOfGeometries() / (gridSizeX * gridSizeY * gridSizeZ),	1 / 3);
		//beetwin [1,28]
		gridResX = Math.max(1, Math.min(Math.floor(gridSizeX * cubeRoot), 128));
		gridResY = Math.max(1, Math.min(Math.floor(gridSizeY * cubeRoot), 128));
		gridResZ = Math.max(1, Math.min(Math.floor(gridSizeZ * cubeRoot), 128));

		// the size of the grid
		voxSizeX = gridSizeX / gridResX;
		voxSizeY = gridSizeY / gridResY;
		voxSizeZ = gridSizeZ / gridResZ;

		voxMaxIndex = convertPointToVoxel(max);
		voxMinIndex = convertPointToVoxel(min);
	
		voxels = new Voxel[(int) (gridResX * gridResY * gridResZ)];// allocate memory for the grid voxel's
		insertToVoxel(geometries.getGeometries());// Insert all the geometry in scene into the voxel's
	}

	/**
	 * A function that get a ray and find all the intersection points
	 * 
	 * @param ray - The ray
	 * @return - a list of the intersection points
	 */
	public List<GeoPoint> findIntersection(Ray ray) {
		List<GeoPoint> intersectionList = new ArrayList<>();
		Map<Geometry, Boolean> pathOfRayGeometries = new HashMap<>();

		Point3D p0 = ray.getP0();
		// check if the point is outside the grid
		if (p0.getX().get() > max.getX().get() || p0.getX().get() < min.getX().get()
				|| p0.getY().get() > max.getX().get() || p0.getY().get() < min.getX().get()
				|| p0.getZ().get() > max.getX().get() || p0.getZ().get() < min.getX().get()) {
			p0 = findClosestPoint(ray);
		}

		double dirX = 1 / ray.getDirection().getHead().getX().get();
		double dirY = 1 / ray.getDirection().getHead().getY().get();
		double dirZ = 1 / ray.getDirection().getHead().getZ().get();

		double deltaX, deltaY, deltaZ, nctX, nctY, nctZ;

		if (p0 == null)
			return intersectionList;

		Voxel p0Vox = convertPointToVoxel(p0);
		Point3D pRayGrid = p0.sub(min).getHead();
		double rayGridX = pRayGrid.getX().get();
		double rayGridY = pRayGrid.getY().get();
		double rayGridZ = pRayGrid.getZ().get();

		deltaX = (dirX < 0 ? -voxSizeX : voxSizeX) * dirX;
		nctX = ((dirX < 0 ? p0Vox._x : p0Vox._x +1)*voxSizeX - rayGridX)* dirX;
				
		deltaY = (dirY < 0 ? -voxSizeY : voxSizeY) * dirY;
		nctY = ((dirY < 0 ? p0Vox._y : p0Vox._y +1)*voxSizeY - rayGridY)* dirY;
		
		deltaZ = (dirZ < 0 ? -voxSizeZ : voxSizeZ) * dirZ;
		nctZ = ((dirZ < 0 ? p0Vox._z : p0Vox._z +1)*voxSizeZ - rayGridZ)* dirZ;


		while (true) {
			int o = (int) (p0Vox._z * gridResX * gridResY + p0Vox._y * gridResX + p0Vox._x);
			if (voxels[o] != null) {
				intersectionList.addAll(voxels[o].findIntersections(ray, pathOfRayGeometries));
				List<GeoPoint> geoIntersections = voxels[o].findIntersections(ray, pathOfRayGeometries);
				intersectionList.addAll(geoIntersections);
				if (geoIntersections.size() > 0) {
					break;
				}
			}
		
			if (nctX < nctY) {
				if (nctX < nctZ) {
					nctX += deltaX;
					p0Vox._x += (dirX < 0) ? -1 : 1;
					if (p0Vox._x < voxMinIndex._x || p0Vox._x > voxMaxIndex._x)
						break;
				} else {
					nctZ += deltaZ;
					p0Vox._z += (dirZ < 0) ? -1 : 1;
					if (p0Vox._z < voxMinIndex._z || p0Vox._z > voxMaxIndex._z)
						break;
				}
			} else {
				if (nctY < nctZ) {
					nctY += deltaY;
					p0Vox._y += (dirY < 0) ? -1 : 1;
					if (p0Vox._y < voxMinIndex._y || p0Vox._y > voxMaxIndex._y)
						break;
				} else {
					nctZ += deltaZ;
					p0Vox._z += (dirZ < 0) ? -1 : 1;
					if (p0Vox._z < voxMinIndex._z || p0Vox._z > voxMaxIndex._z)
						break;
				}
			}
		}
		return intersectionList;
	}

	/**
	 * A function that get a ray and find the closest intersection point with the
	 * overall box of the scene
	 * 
	 * @param ray - The ray
	 * @return - The closest point
	 */
	private Point3D findClosestPoint(Ray ray) {
		List<GeoPoint> intersectionPoints = box.findIntersections(ray); // The closest intersection enter into the grid
		double tempDist, minDist = Double.MAX_VALUE;
		Point3D p0 = ray.getP0();
		Point3D closestPoint = null;
		// find the minimum point
		if (!intersectionPoints.isEmpty()) {
			for (GeoPoint g : intersectionPoints) {
				tempDist = g.point.distance(p0);
				if (tempDist < minDist) {
					closestPoint = g.point;
					minDist = tempDist;
				}
			}
		}
		return closestPoint;
	}

	/**
	 * A function that get a list of "intersectable" and insert them to the voxels
	 * array
	 * 
	 * @param geometries - The list of the geometries
	 */
	private void insertToVoxel(List<Intersectable> geometries) {
		for (Intersectable intersectable : geometries) {
			Voxel maxVox = convertPointToVoxel(intersectable.getMax());
			Voxel minVox = convertPointToVoxel(intersectable.getMin());

			// fill the voxel's with the geometries
			for (int z = minVox._z; z <= maxVox._z; ++z) {
				for (int y = minVox._y; y <= maxVox._y; ++y) {
					for (int x = minVox._x; x <= maxVox._x; ++x) {
						int o = (int) (z * gridResX * gridResY + y * gridResX + x);
						if (voxels[o] == null)
							voxels[o] = new Voxel(x, y, z);
						voxels[o].insert(intersectable);
					}
				}
			}
		}
	}

	/**
	 * A function that get a 3D point and convert it to a voxel
	 * 
	 * @param point - The 3D point
	 * @return - the voxel
	 */
	private Voxel convertPointToVoxel(Point3D point) {
		Voxel voxel = new Voxel((int) ((point.getX().get() - min.getX().get()) / voxSizeX),
				(int) ((point.getY().get() - min.getY().get()) / voxSizeY),
				(int) ((point.getZ().get() - min.getZ().get()) / voxSizeZ));

		voxel._x = Math.max(0, Math.min(voxel._x, (int) (gridResX - 1)));
		voxel._y = Math.max(0, Math.min(voxel._y, (int) (gridResY - 1)));
		voxel._z = Math.max(0, Math.min(voxel._z, (int) (gridResZ - 1)));
		return voxel;
	}
}