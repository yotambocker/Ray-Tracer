package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class of Box that contain the Intersectable
 */
public class Box extends Geometry implements Intersectable {
	private Point3D minPoint, maxPoint;

	/**
	 * ctor of Box that get 2 point (minPoint and maxPoint) and contain the
	 * Intersectable
	 * 
	 * @param minPoint
	 * @param maxPoint
	 */
	public Box(Point3D min, Point3D max) {
		super(Color.BLACK);
		minPoint = min;
		maxPoint = max;
	}
/**
 * ctor with material and color
 * @param color
 * @param material
 * @param min
 * @param max
 */
	public Box(Color color, Material material, Point3D min, Point3D max) {
		super(color, material);
		minPoint = min;
		maxPoint = max;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> list = new ArrayList<>();
		Point3D p0 = ray.getP0();
		Vector dir = ray.getDirection();

		double maxPointX = maxPoint.getX().get();
		double maxPointY = maxPoint.getY().get();
		double maxPointZ = maxPoint.getZ().get();

		double minPointX = minPoint.getX().get();
		double minPointY = minPoint.getY().get();
		double minPointZ = minPoint.getZ().get();

		double p0X = p0.getX().get();
		double p0Y = p0.getY().get();
		double p0Z = p0.getZ().get();

		double dirX = 1 / dir.getHead().getX().get();
		double dirY = 1 / dir.getHead().getY().get();
		double dirZ = 1 / dir.getHead().getZ().get();

		double minX, maxX, minY, maxY, minZ, maxZ;

		if (dirX < 0) {
			minX = (maxPointX - p0X) * dirX;
			maxX = (minPointX - p0X) * dirX;

		} else {
			maxX = (maxPointX - p0X) * dirX;
			minX = (minPointX - p0X) * dirX;
		}

		if (dirY < 0) {
			minY = (maxPointY - p0Y) * dirY;
			maxY = (minPointY - p0Y) * dirY;

		} else {
			maxY = (maxPointY - p0Y) * dirY;
			minY = (minPointY - p0Y) * dirY;
		}

		if (Double.isNaN(minX))
			minX = Double.NEGATIVE_INFINITY;
		if (Double.isNaN(maxX))
			minX = Double.POSITIVE_INFINITY;
		if (Double.isNaN(minY))
			minX = Double.NEGATIVE_INFINITY;
		if (Double.isNaN(maxY))
			minX = Double.POSITIVE_INFINITY;

		if ((minX > maxY) || (minY > maxX))
			return list;
		if (minY > minX)
			minX = minY;
		if (maxY < maxX)
			maxX = maxY;

		if (dirZ < 0) {
			minZ = (maxPointZ - p0Z) * dirZ;
			maxZ = (minPointZ - p0Z) * dirZ;

		} else {
			maxZ = (maxPointZ - p0Z) * dirZ;
			minZ = (minPointZ - p0Z) * dirZ;
		}

		if (Double.isNaN(minZ))
			minX = Double.NEGATIVE_INFINITY;

		if (Double.isNaN(maxZ))
			minX = Double.POSITIVE_INFINITY;

		if ((minX > maxZ) || (minZ > maxX))
			return list;
		if (minZ > minX)
			minX = minZ;
		if (maxZ < maxX)
			maxX = maxZ;

		if (!Util.isZero(minX) && minX > 0 && Double.isFinite(minX))
			list.add(new GeoPoint(p0.add(dir.scalarMult(minX)), this));

		if (!Util.isZero(maxX) && maxX > 0 && Double.isFinite(maxX))
			list.add(new GeoPoint(p0.add(dir.scalarMult(maxX)), this));

		return list;

	}

	@Override
	public Point3D getMax() {
		return maxPoint;
	}

	@Override
	public Point3D getMin() {
		return minPoint;
	}

	@Override
	public Vector getNormal(Point3D point) {
		Point3D center = new Point3D((minPoint.getX().get() + maxPoint.getX().get()) / 2,
				(minPoint.getY().get() + maxPoint.getY().get()) / 2,
				(minPoint.getZ().get() + maxPoint.getZ().get()) / 2);
		return point.sub(center).normal();
	}
}