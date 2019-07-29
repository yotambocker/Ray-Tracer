package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * Collection of geometric bodies. The department will implement the interface
 * Intersectable
 */
public class Geometries implements Intersectable {

	protected List<Intersectable> _geometries = new ArrayList<>();

	/**
	 * The function add a geometry to the list
	 * 
	 * @param g - the geometry we want to add
	 */
	public void add(Intersectable... gs) {
		for (Intersectable g : gs)
			_geometries.add(g);
	}

	@Override
	public List<GeoPoint> findIntersections(Ray r) {
		List<GeoPoint> intersectionPoints = new ArrayList<>();
		for (Intersectable g : _geometries)
			intersectionPoints.addAll(g.findIntersections(r));
		return intersectionPoints;
	}

	@Override
	public Point3D getMax() {
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		double maxZ = Double.MIN_VALUE;
		for (Intersectable geometry : _geometries) {
			Point3D maxP = geometry.getMax();
			maxX = maxP.getX().get() > maxX ? maxP.getX().get() : maxX;
			maxY = maxP.getY().get() > maxY ? maxP.getY().get() : maxY;
			maxZ = maxP.getZ().get() > maxZ ? maxP.getZ().get() : maxZ;
		}
		return new Point3D(maxX, maxY, maxZ);
	}

	@Override
	public Point3D getMin() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		for (Intersectable geometry : _geometries) {
			Point3D minP = geometry.getMin();
			minX = minP.getX().get() < minX ? minP.getX().get() : minX;
			minY = minP.getY().get() < minY ? minP.getY().get() : minY;
			minZ = minP.getZ().get() < minZ ? minP.getZ().get() : minZ;
		}
		return new Point3D(minX, minY, minZ);
	}

	/**
	 * @return the number of Geometries
	 */
	public int numOfGeometries() {
		return _geometries.size();
	}

	/**
	 * @return the list of Geometries
	 */
	public List<Intersectable> getGeometries() {
		return _geometries;
	}
}