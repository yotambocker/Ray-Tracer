package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * This class represents a tube by radius and axis
 */
public class Tube extends RadialGeometry {

	Ray _axisRay;

	/**
	 * a constructor that get the radius and a ray of axis
	 * 
	 * @param r    - radius of the tube
	 * @param axis - the ray of the tube
	 */
	public Tube(Color emmission, double r, Ray axis) {
		super(emmission, r);
		_axisRay = new Ray(axis.getP0(), axis.getDirection());
	}

	/**
	 * A constructor with material
	 * 
	 * @param emmission - The emmission color of the tube
	 * @param material  - The material of the tube
	 * @param r         - radius of the tube
	 * @param axis      - the ray of the tube
	 */
	public Tube(Color emmission, Material material, double r, Ray axis) {
		super(emmission, material, r);
		_axisRay = new Ray(axis.getP0(), axis.getDirection());
	}

	/**
	 * Getter of the axis -ray
	 * 
	 * @return the ray
	 */
	public Ray getAxisRay() {
		return _axisRay;
	}

	@Override
	public String toString() {
		return super.toString() + " _axisRay: " + _axisRay.toString();
	}

	@Override
	public Vector getNormal(Point3D p) {
		Point3D p0 = _axisRay.getP0();
		Vector v = _axisRay.getDirection();
		double t = v.dotProduct(p.sub(p0));

		if (t != 0) {
			v = v.scalarMult(t);
		}

		Point3D o = p0.add(v);
		return p.sub(o).normal();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		Vector v = ray.getDirection();
		Point3D p = ray.getP0();
		Vector va = _axisRay.getDirection();
		Point3D p0 = _axisRay.getP0();
		Vector vecA, vecB;
		double a, b, c;
		double vDotVa = v.dotProduct(va);
		if (v.equals(va)) {
			return EMPTY_LIST;
		}
		if (Util.isZero(vDotVa)) {
			a = v.dotProduct(v);
		} else {
			if (v.equals(va.scalarMult(vDotVa))) {
				a = 0;
			} else {
				vecA = v.sub(va.scalarMult(vDotVa));
				a = vecA.dotProduct(vecA);
			}
		}
		if (p.equals(p0)) {
			b = 0;
			c = -_radius * _radius;
		} else {
			Vector dp = p.sub(p0);
			double dpDotVa = dp.dotProduct(va);
			if (!Util.isZero(dpDotVa) && dp.equals(va.scalarMult(dp.dotProduct(va)))) {
				b = 0;
				c = -_radius * _radius;
			} else {
				if (Util.isZero(dpDotVa)) {
					vecB = dp;
				} else {
					vecB = dp.sub(va.scalarMult(dpDotVa));
				}
				c = vecB.dotProduct(vecB) - _radius * _radius;
				if (Util.isZero(vDotVa)) {
					b = 2 * v.dotProduct(vecB);
				} else {
					if (v.equals(va.scalarMult(vDotVa))) {
						b = 0;
					} else {
						vecA = v.sub(va.scalarMult(v.dotProduct(va)));
						b = 2 * vecA.dotProduct(vecB);
					}
				}
			}
		}
		double discr = (b * b) - (4.0 * a * c);
		if (discr < 0)
			return EMPTY_LIST;
		double delta = Math.sqrt(discr);
		double a2 = 2.0 * a;
		double t1 = (-b - delta) / a2;
		double t2 = (-b + delta) / a2;
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		if (t1 > 0) {
			list.add(new GeoPoint(p.add(v.scalarMult(t1)), this));
		}
		if (t2 > 0) {
			list.add(new GeoPoint(p.add(v.scalarMult(t2)), this));
		}
		return list;
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