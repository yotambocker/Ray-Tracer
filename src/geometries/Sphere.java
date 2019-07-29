package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * This class represents a sphere by 3D-point of the center and the length of
 * the radius
 */
public class Sphere extends RadialGeometry {

	private Point3D _center;

	/**
	 * A constructor that get the radius and the center
	 * 
	 * @param rad - the radius of the sphere
	 * @param cen - the center of the sphere
	 */
	public Sphere(Color emmission, double rad, Point3D cen) {
		super(emmission, rad);
		_center = cen;
	}

	/**
	 * A constructor with material
	 * 
	 * @param emmission
	 * @param material
	 * @param rad
	 * @param cen
	 */
	public Sphere(Color emmission, Material material, double rad, Point3D cen) {
		super(emmission, material, rad);
		_center = cen;
	}

	/**
	 * Getter of the center
	 * 
	 * @return the center of the sphere
	 */
	public Point3D getCenter() {
		return _center;
	}

	@Override
	public String toString() {
		return super.toString() + "_center" + _center.toString();
	}

	@Override
	public Vector getNormal(Point3D p) {
		return p.sub(_center).normal();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray r) {
		Vector v = r.getDirection();
		Point3D p0 = r.getP0();

		if (_center.equals(p0)) {
			List<GeoPoint> list = new ArrayList<GeoPoint>();

			list.add(new GeoPoint(p0.add(v.scalarMult(_radius)), this));
			return list;
		}
		Vector u = _center.sub(p0);
		double tm = alignZero(v.dotProduct(u));
		double dSquared = u.powLenght() - tm * tm;
		double d = Math.sqrt(dSquared);
		if (alignZero(d - _radius) > 0)
			return EMPTY_LIST;

		double th = alignZero(Math.sqrt(_radius * _radius - dSquared));
		if (th == 0) {
			if (tm > 0) {
				List<GeoPoint> list = new ArrayList<GeoPoint>();
				list.add(new GeoPoint(p0.add(v.scalarMult(tm)), this));
				return list;
			}
			return EMPTY_LIST;
		}
		double t1 = alignZero(tm - th);
		double t2 = alignZero(tm + th);
		if (t1 <= 0 && t2 <= 0)
			return EMPTY_LIST;
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		if (t1 > 0)
			list.add(new GeoPoint(p0.add(v.scalarMult(t1)), this));// P1
		if (t2 > 0)
			list.add(new GeoPoint(p0.add(v.scalarMult(t2)), this));// P2
		return list;
	}

	@Override
	public Point3D getMax() {
		return new Point3D(_center.getX().get() + _radius, _center.getY().get() + _radius,
				_center.getZ().get() + _radius);
	}

	@Override
	public Point3D getMin() {
		return new Point3D(_center.getX().get() - _radius, _center.getY().get() - _radius,
				_center.getZ().get() - _radius);
	}

	/**
	 * create sphere from XML
	 * 
	 * @param sphereAttributes
	 */
	public Sphere(Map<String, String> sphereAttributes) {
		super(Color.BLACK, Double.valueOf(sphereAttributes.get("radius")));

		String[] center = sphereAttributes.get("center").split("\\s+");
		_center = new Point3D(Double.valueOf(center[0]), Double.valueOf(center[1]), Double.valueOf(center[2]));
	}
}