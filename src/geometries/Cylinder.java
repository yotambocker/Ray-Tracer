package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;

/**
 * This class represents a cylinder by height and tube
 */
public class Cylinder extends Tube {

	private double _height;
	private Plane up;
	private Plane down;

	/**
	 * A constructor that get the radius, the axis and the height.
	 * 
	 * @param r     - The radius
	 * @param axis  - The ray that represent the axis
	 * @param hight - The height of the cylinder
	 */
	public Cylinder(Color emmission, double r, Ray axis, double height) {
		super(emmission, r, axis);
		_height = height;
		up = new Plane(emmission, axis.getP0().add(axis.getDirection().scalarMult(height / 2)), axis.getDirection());
		down = new Plane(emmission, axis.getP0().add(axis.getDirection().scalarMult(-height / 2)),
				axis.getDirection().scalarMult(-1));
	}

	/**
	 * A constructor with material
	 * 
	 * @param emmission - The emmission color of the cylinder
	 * @param material  - The material of the cylinder
	 * @param r         - The radius
	 * @param axis      - The ray that represent the axis
	 * @param height    - The height of the cylinder
	 */
	public Cylinder(Color emmission, Material material, double r, Ray axis, double height) {
		super(emmission, material, r, axis);
		_height = height;
		up = new Plane(emmission, axis.getP0().add(axis.getDirection().scalarMult(height / 2)), axis.getDirection());
		down = new Plane(emmission, axis.getP0().add(axis.getDirection().scalarMult(-height / 2)),
				axis.getDirection().scalarMult(-1));
	}

	/**
	 * getter of the height
	 * 
	 * @return the height of the cylinder
	 */
	public double getHight() {
		return _height;
	}

	@Override
	public Vector getNormal(Point3D p) {
		Vector dir = _axisRay.getDirection();
		double d = dir.dotProduct(p.sub(_axisRay.getP0()));

		if (Util.isZero(d - _height / 2)) {
			return dir;
		}
		if (Util.isZero(d + _height / 2)) {
			return dir.scalarMult(-1);
		}
		return super.getNormal(p);
	}

	@Override
	public String toString() {
		return super.toString() + "_height: " + _height;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray rayIn) {
		Point3D p0 = _axisRay.getP0();
		Vector dir = _axisRay.getDirection();
		List<GeoPoint> upList = up.findIntersections(rayIn);
		List<GeoPoint> downList = down.findIntersections(rayIn);
		List<GeoPoint> cList = new ArrayList<GeoPoint>();
		List<GeoPoint> intersectionPoints = super.findIntersections(rayIn);
		if (intersectionPoints.isEmpty()) {
			return EMPTY_LIST;
		}
		Point3D pUP = up.getP();
		Point3D pDown = down.getP();
		for (GeoPoint point : intersectionPoints) {
			Point3D p = point.point;
			if (Math.abs(p.sub(p0).dotProduct(dir)) <= _height / 2) {
				cList.add(point);
			}
		}
		for (GeoPoint point : upList) {
			if (Util.isZero(point.point.distance(pUP) - _radius) || point.point.distance(pUP) < _radius)
				cList.add(point);
		}
		for (GeoPoint point : downList) {
			if (Util.isZero(point.point.distance(pDown) - _radius) || point.point.distance(pDown) < _radius)
				cList.add(point);
		}
		return cList;
	}

	@Override
	public Point3D getMax() {
		Circle upSphere = new Circle(null, Material.regular, up.getP(), _axisRay.getDirection(), _radius);
		Circle downSphere = new Circle(null, Material.regular, down.getP(), _axisRay.getDirection(), _radius);

		double maxX = downSphere.getMax().getX().get();
		double maxY = downSphere.getMax().getY().get();
		double maxZ = downSphere.getMax().getZ().get();

		Point3D maxP = upSphere.getMax();
		maxX = maxP.getX().get() > maxX ? maxP.getX().get() : maxX;
		maxY = maxP.getY().get() > maxY ? maxP.getY().get() : maxY;
		maxZ = maxP.getZ().get() > maxZ ? maxP.getZ().get() : maxZ;
		return new Point3D(maxX, maxY, maxZ);
	}

	@Override
	public Point3D getMin() {
		Circle upSphere = new Circle(null, Material.regular, up.getP(), _axisRay.getDirection(), _radius);
		Circle downSphere = new Circle(null, Material.regular, down.getP(), _axisRay.getDirection(), _radius);

		double minX = downSphere.getMin().getX().get();
		double minY = downSphere.getMin().getY().get();
		double minZ = downSphere.getMin().getZ().get();

		Point3D minP = upSphere.getMin();
		minX = minP.getX().get() < minX ? minP.getX().get() : minX;
		minY = minP.getY().get() < minY ? minP.getY().get() : minY;
		minZ = minP.getZ().get() < minZ ? minP.getZ().get() : minZ;
		return new Point3D(minX, minY, minZ);
	}
}

















/*
@Override
public Point3D getMax() {
	double x, y, z;
	Point3D p0 = _axisRay.getP0();
	Vector dir = _axisRay.getDirection();
	Point3D p = p0.add(dir.scalarMult(_height / 2));

	if (p.getX().get() > p0.getX().get())
		x = p.getX().get() + _radius;
	else
		x = p0.getX().get() + _radius;
	if (p.getY().get() > p0.getY().get())
		y = p.getY().get() + _radius;
	else
		y = p0.getY().get() + _radius;
	if (p.getZ().get() > p0.getZ().get())
		z = p.getZ().get() + _radius;
	else
		z = p0.getZ().get() + _radius;
	return new Point3D(x, y, z);
}

@Override
public Point3D getMin() {
	double x, y, z;
	Point3D p0 = _axisRay.getP0();
	Vector dir = _axisRay.getDirection();
	Point3D p = p0.add(dir.scalarMult(_height / 2));

	if (p.getX().get() < p0.getX().get())
		x = p.getX().get() - _radius;
	else
		x = p0.getX().get() - _radius;
	if (p.getY().get() < p0.getY().get())
		y = p.getY().get() - _radius;
	else
		y = p0.getY().get() - _radius;
	if (p.getZ().get() < p0.getZ().get())
		z = p.getZ().get() - _radius;
	else
		z = p0.getZ().get() - _radius;
	return new Point3D(x, y, z);
}*/