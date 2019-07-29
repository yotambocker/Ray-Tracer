package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;

/**
 * class circle that extends Plane
 */
public class Circle extends Plane {
	protected double _radius;
	protected double _anRadius;

	/**
	 * ctor of Circle extends Plane
	 * 
	 * @param emmission - The emmission of the circle
	 * @param material  - The material of the circle
	 * @param p         - The center point of the circle
	 * @param n         - The normal vector of the circle
	 */
	public Circle(Color emmission, Material material, Point3D p, Vector n, double _radius) {
		super(emmission, material, p, n);
		this._radius = _radius;
		_anRadius = 0;
	}

	/**
	 * constructor that get all the parameters
	 * 
	 * @param emmission - The emmission of the circle
	 * @param material  - The material of the circle
	 * @param p         - The center point of the circle
	 * @param n         - The normal vector of the circle
	 * @param _radius   - The radius of the circle
	 * @param anRadius  - option to make it a ring
	 */
	public Circle(Color emmission, Material material, Point3D p, Vector n, double _radius, double anRadius) {
		super(emmission, material, p, n);
		this._radius = _radius;
		_anRadius = anRadius;
	}

	/**
	 * to make the circle like a ring
	 * 
	 * @param radius
	 */
	public void setAnnulus(double radius) {
		_anRadius = radius;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray rayIn) {
		List<GeoPoint> cList = new ArrayList<GeoPoint>();
		List<GeoPoint> intersectionPoints = super.findIntersections(rayIn);
		if (!intersectionPoints.isEmpty()) {
			for (GeoPoint point : intersectionPoints) {
				double distence = point.point.distance(_p1);
				if (distence < _radius && (Util.isZero(_anRadius) || distence > _anRadius)) {
					cList.add(point);
				}
			}
		}
		return cList;
	}

	@Override
	public Point3D getMax() {
		return new Point3D(getP().getX().get() + _radius, getP().getY().get() + _radius, getP().getZ().get() + _radius);
	}

	@Override
	public Point3D getMin() {
		return new Point3D(getP().getX().get() - _radius, getP().getY().get() - _radius, getP().getZ().get() - _radius);
	}

}