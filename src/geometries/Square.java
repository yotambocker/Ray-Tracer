package geometries;

import java.util.List;
import primitives.*;

/**
 * class of Square extends Triangle
 */
public class Square extends Plane {
	private Point3D _p1;
	private Point3D _p2;
	private Point3D _p3;
	private Point3D _p4;

	/**
	 * A constructor that get the 4 points
	 * 
	 * @param emmission
	 * @param p1        - the first point
	 * @param p2        - the second point
	 * @param p3        - the third point
	 * @param p4        - the fourth point
	 */
	public Square(Color emmission, Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		super(emmission, p1, p2, p3);
		_p1 = p1;
		_p2 = p2;
		_p3 = p3;
		_p4 = p4;
	}

	/**
	 * A constructor with material
	 * 
	 * @param emmission
	 * @param material
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public Square(Color emmission, Material material, Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		super(emmission, material, p1, p2, p3);
		_p1 = p1;
		_p2 = p2;
		_p3 = p3;
		_p4 = p4;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return super.getNormal(point);
	}

	/**
	 * Getter of the p4
	 * 
	 * @return _p4
	 */
	public Point3D getP4() {
		return _p4;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray r) {
		List<GeoPoint> intersections = super.findIntersections(r);
		if (intersections.isEmpty())
			return EMPTY_LIST;

		Vector ab = _p1.sub(_p2).normalize();
		Vector cb = _p3.sub(_p2).normalize();
		Point3D p = intersections.get(0).point;

		if (p.equals(_p2))
			return intersections;

		Vector pbNormal = p.sub(_p2).normalize();
		Vector pb = p.sub(_p2);

		double abDist = _p1.distance(_p2);
		double cbDist = _p3.distance(_p2);

		if (ab.dotProduct(pbNormal) < 0 || cb.dotProduct(pbNormal) < 0 || pb.dotProduct(ab) > abDist
				|| pb.dotProduct(cb) > cbDist) {
			return EMPTY_LIST;
		}
		return intersections;
	}

	@Override
	public Point3D getMax() {
		Point3D p = _p2.sub(_p1).add(_p3.sub(_p1)).getHead();
		double x = Double.max(_p1.getX().get(),
				Double.max(_p2.getX().get(), Double.max(_p3.getX().get(), p.getX().get())));
		double y = Double.max(_p1.getY().get(),
				Double.max(_p2.getY().get(), Double.max(_p3.getY().get(), p.getY().get())));
		double z = Double.max(_p1.getZ().get(),
				Double.max(_p2.getZ().get(), Double.max(_p3.getZ().get(), p.getZ().get())));
		return new Point3D(x, y, z);
	}

	@Override
	public Point3D getMin() {
		Point3D p = _p2.sub(_p1).add(_p3.sub(_p1)).getHead();
		double x = Double.min(_p1.getX().get(),
				Double.min(_p2.getX().get(), Double.min(_p3.getX().get(), p.getX().get())));
		double y = Double.min(_p1.getY().get(),
				Double.min(_p2.getY().get(), Double.min(_p3.getY().get(), p.getY().get())));
		double z = Double.min(_p1.getZ().get(),
				Double.min(_p2.getZ().get(), Double.min(_p3.getZ().get(), p.getZ().get())));
		return new Point3D(x, y, z);
	}

}
