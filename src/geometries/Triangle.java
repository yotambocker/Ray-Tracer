package geometries;

import java.util.List;
import java.util.Map;

import primitives.*;
import static primitives.Util.*;

/**
 * This class represents a triangle by 3 3D-points
 */
public class Triangle extends Plane {

	Point3D _p2;
	Point3D _p3;

	/**
	 * A constructor that get the 3 3D-points
	 * 
	 * @param p1 - the first point
	 * @param p2 - the second point
	 * @param p3 - the third point
	 */
	public Triangle(Color emmission, Point3D p1, Point3D p2, Point3D p3) {
		super(emmission, p1, p2, p3);
		_p2 = p2;
		_p3 = p3;
	}

	/**
	 * A constructor with material
	 * 
	 * @param emmission
	 * @param material
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Color emmission, Material material, Point3D p1, Point3D p2, Point3D p3) {
		super(emmission, material, p1, p2, p3);
		_p2 = p2;
		_p3 = p3;
	}

	/**
	 * Getter of the first 3D-point
	 * 
	 * @return the point
	 */
	public Point3D getP1() {
		return _p1;
	}

	/**
	 * Getter of the second 3D-point
	 * 
	 * @return the point
	 */
	public Point3D getP2() {
		return _p2;
	}

	/**
	 * Getter of the third 3D-point
	 * 
	 * @return the point
	 */
	public Point3D getP3() {
		return _p3;
	}

	@Override
	public String toString() {
		return "p1: " + _p1.toString() + " p2: " + _p2.toString() + " p3: " + _p3.toString();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray r) {
		List<GeoPoint> tmp = super.findIntersections(r);
		if (tmp.isEmpty())
			return EMPTY_LIST;
		GeoPoint p = tmp.get(0);
		Point3D p0 = r.getP0();
		Vector v1 = _p1.sub(p0);
		Vector v2 = _p2.sub(p0);
		Vector v3 = _p3.sub(p0);

		Vector n1 = v1.crossProduct(v2).normal();
		Vector n2 = v2.crossProduct(v3).normal();
		Vector n3 = v3.crossProduct(v1).normal();

		Vector ptmp = p.point.sub(p0);

		double s1 = alignZero(ptmp.dotProduct(n1));
		double s2 = alignZero(ptmp.dotProduct(n2));
		double s3 = alignZero(ptmp.dotProduct(n3));
		if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))
			return tmp;
		return EMPTY_LIST;

	}

	/**
	 * create triangle from XML
	 * 
	 * @param triangleAttributes
	 */
	public Triangle(Map<String, String> triangleAttributes) {
		super(triangleAttributes);

		String[] p2coords = triangleAttributes.get("p1").split("\\s+");
		Point3D p2 = new Point3D(Double.valueOf(p2coords[0]), Double.valueOf(p2coords[1]), Double.valueOf(p2coords[2]));

		String[] p3coords = triangleAttributes.get("p2").split("\\s+");
		Point3D p3 = new Point3D(Double.valueOf(p3coords[0]), Double.valueOf(p3coords[1]), Double.valueOf(p3coords[2]));

		_p2 = p2;
		_p3 = p3;

	}

	@Override
	public Point3D getMax() {
		double x = Double.max(_p1.getX().get(), Double.max(_p2.getX().get(), _p3.getX().get()));
		double y = Double.max(_p1.getY().get(), Double.max(_p2.getY().get(), _p3.getY().get()));
		double z = Double.max(_p1.getZ().get(), Double.max(_p2.getZ().get(), _p3.getZ().get()));
		return new Point3D(x, y, z);
	}

	@Override
	public Point3D getMin() {
		double x = Double.min(_p1.getX().get(), Double.min(_p2.getX().get(), _p3.getX().get()));
		double y = Double.min(_p1.getY().get(), Double.min(_p2.getY().get(), _p3.getY().get()));
		double z = Double.min(_p1.getZ().get(), Double.min(_p2.getZ().get(), _p3.getZ().get()));
		return new Point3D(x, y, z);
	}

}