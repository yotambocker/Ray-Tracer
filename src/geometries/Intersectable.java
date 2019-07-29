package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * interface Intersectable for geometric and findIntersections func
 */
public interface Intersectable {
	/**
	 * A static class that represents a point in a geometry, include the geometry
	 * that it belongs to it
	 * 
	 * @author יגאל
	 *
	 */
	static class GeoPoint {

		public Geometry geometry;
		public Point3D point;

		/**
		 * A constructor that get the geometry and the position of the point and build
		 * the geoPoint
		 * 
		 * @param p - The position of the point
		 * @param g - The geometry, which the point belongs to
		 */
		public GeoPoint(Point3D p, Geometry g) {
			geometry = g;
			point = p;
		}
	};

	/**
	 * A static empty list when there are no cutting point
	 */
	public static final List<GeoPoint> EMPTY_LIST = new ArrayList<>();

	/**
	 * A function that return a list of all the points of geometries that the ray
	 * passes through them
	 * 
	 * @param r - The ray
	 * @return List of Point3D
	 */
	List<GeoPoint> findIntersections(Ray ray);

	/**
	 * @return the max point of the box contains the Intersectable
	 */
	Point3D getMax();

	/**
	 * @return the min point of the box contains the Intersectable
	 */
	Point3D getMin();
}