package primitives;

/**
 * class Point3D
 */
public class Point3D {
	/**
	 * Point3D ZERO
	 */
	public static final Point3D ZERO = new Point3D(0, 0, 0);

	 Coordinate x;
	 Coordinate y;
	 Coordinate z;

	/********** Constructors ***********/
	/**
	 * ctor that gets 3 Coordinate
	 * 
	 * @param x to Coordinate x
	 * @param y to Coordinate y
	 * @param z to Coordinate z
	 */
	public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
		x = _x;
		y = _y;
		z = _z;
	}

	/**
	 * ctor that gets 3 double
	 * 
	 * @param x to Coordinate x
	 * @param y to Coordinate y
	 * @param z to Coordinate z
	 */
	public Point3D(double _x, double _y, double _z) {
		x = new Coordinate(_x);
		y = new Coordinate(_y);
		z = new Coordinate(_z);
	}

	/**
	 * copy ctor
	 * 
	 * @param p
	 */
	public Point3D(Point3D p) {
		x = new Coordinate(p.getX());
		y = new Coordinate(p.getY());
		z = new Coordinate(p.getZ());
	}

	/*************** Admin *****************/
	/**
	 * return true if its equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D tmp = (Point3D) obj;
		return (x.equals(tmp.x) && y.equals(tmp.y) && z.equals(tmp.z));
	}

	/**
	 * return string of the point
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	/************** Operations ***************/
	/**
	 * sub p with our point
	 * 
	 * @param p
	 * @return new Vector
	 */
	public Vector sub(Point3D p) {
		return new Vector(x.subtract(p.x).get(), y.subtract(p.y).get(), z.subtract(p.z).get());
	}

	/**
	 * add p with our point (Helper for Vector)
	 * 
	 * @param v
	 * @return new point
	 */
	public Point3D add(Vector v) {
		return new Point3D(x.add(v.getHead().getX()), y.add(v.getHead().getY()), z.add(v.getHead().getZ()));
	}

	/**
	 * return distance between our point and p
	 * 
	 * @param p
	 * @return
	 */
	public double distance(Point3D p) {
		return Math.sqrt(powDistance(p));
	}

	/**
	 * return pow of distance between our point and p
	 * 
	 * @param p
	 * @return
	 */
	public double powDistance(Point3D p) {
		double dx = (x.subtract(p.x)).get();
		double dy = (y.subtract(p.y)).get();
		double dz = (z.subtract(p.z)).get();
		return dx * dx + dy * dy + dz * dz;
	};

	/**
	 * Getter of the Coordinate x
	 * 
	 * @return the point that represent the plane
	 */
	public Coordinate getX() {
		return x;
	}

	/**
	 * Getter of the Coordinate y
	 * 
	 * @return the point that represent the plane
	 */
	public Coordinate getY() {
		return y;
	}

	/**
	 * Getter of the Coordinate z
	 * 
	 * @return the point that represent the plane
	 */
	public Coordinate getZ() {
		return z;
	}
}
