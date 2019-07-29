package geometries;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import primitives.*;
import static primitives.Util.*;

/**
 * This class represents a plane by point on the plane and vector that
 * orthogonal to the plane
 */
public class Plane extends Geometry {

	protected Point3D _p1;
	protected Vector _normal;

	/**
	 * A constructor that get the orthogonal vector and a point on the plane
	 * 
	 * @param p - the point on the plane
	 * @param n - the orthogonal vector
	 */
	public Plane(Color emmission, Point3D p, Vector n) {
		super(emmission);
		_p1 = p;
		_normal = n.normal();

	}

	/**
	 * A constructor with material
	 * 
	* @param emmission - The emmission color of the plane
	 * @param material  - The material of the plane
	 * @param p         - A point on the plane
	 * @param n         - The orthogonal vector
	 */
	public Plane(Color emmission, Material material, Point3D p, Vector n) {
		super(emmission, material);
		_p1 = p;
		_normal = n.normal();

	}

	/**
	 * A constructor that get 3 points and calculate the normal of the plane
	 * 
	 * 
	 * @param p1 - first point
	 * @param p2 - second point
	 * @param p3 - third point
	 */
	public Plane(Color emmission, Point3D p1, Point3D p2, Point3D p3) {
		super(emmission);

		Vector v1 = p2.sub(p1);
		Vector v2 = p3.sub(p1);
		_normal = v1.crossProduct(v2).normal();
		_p1 = p1;
	}

	/**
	 * A constructor with material
	 * 
	 * @param emmission - The emmission color of the plane
	 * @param material  - The material of the plane
	 * @param p1        - first point
	 * @param p2        - second point
	 * @param p3        - third point
	 */
	public Plane(Color emmission, Material material, Point3D p1, Point3D p2, Point3D p3) {
		super(emmission, material);

		Vector v1 = p2.sub(p1);
		Vector v2 = p3.sub(p1);
		_normal = v1.crossProduct(v2).normal();
		_p1 = p1;
	}

	/**
	 * Getter of the point
	 * 
	 * @return the point that represent the plane
	 */
	public Point3D getP() {
		return _p1;
	}

	/**
	 * Getter of the orthogonal vector
	 * 
	 * @return the 'normal' vector of the plane
	 */
	public Vector getNormal() {
		return _normal;
	}

	@Override
	public Vector getNormal(Point3D P) {
		return _normal;
	}

	@Override
	public String toString() {
		return "_p: " + _p1.toString() + " _normal: " + _normal.toString();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray r) {
		Vector v = r.getDirection();
		Point3D p0 = r.getP0();
		double denom = _normal.dotProduct(v);
		if (isZero(denom))
			return EMPTY_LIST;
		double t;
		try {
			t = alignZero(_normal.dotProduct(_p1.sub(p0)) / denom);
		} catch (Exception e) {
			return EMPTY_LIST;
		}
		if (t < 0.0)
			return EMPTY_LIST;
		List<GeoPoint> intersections = new ArrayList<GeoPoint>();
		try {
			intersections.add(new GeoPoint(p0.add(v.scalarMult(t)), this));
		} catch (Exception e) {
			return EMPTY_LIST;
		}

		return intersections;

	}

	/**
	 * create plane from XML
	 * 
	 * @param planeAttributes
	 */
	public Plane(Map<String, String> planeAttributes) {
		super(Color.BLACK);

		String[] p1coords = planeAttributes.get("p0").split("\\s+");
		Point3D p1 = new Point3D(Double.valueOf(p1coords[0]), Double.valueOf(p1coords[1]), Double.valueOf(p1coords[2]));

		String[] p2coords = planeAttributes.get("p1").split("\\s+");
		Point3D p2 = new Point3D(Double.valueOf(p2coords[0]), Double.valueOf(p2coords[1]), Double.valueOf(p2coords[2]));

		String[] p3coords = planeAttributes.get("p2").split("\\s+");
		Point3D p3 = new Point3D(Double.valueOf(p3coords[0]), Double.valueOf(p3coords[1]), Double.valueOf(p3coords[2]));

		Vector v1 = p2.sub(p1);
		Vector v2 = p3.sub(p1);
		_normal = v1.crossProduct(v2).normal();
		_p1 = p1;
	}

	@Override
	public Point3D getMax() {
		return null;
	}

	@Override
	public Point3D getMin() {
		return null;
	}

}