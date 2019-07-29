package primitives;

/**
 * class vector
 */
public class Vector {
	private Point3D head;

	/********** Constructors ***********/
	/**
	 * constructor that get Point3D
	 * 
	 * @param p - The point where the vector is end
	 */
	public Vector(Point3D p) {
		if (p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("zero vector is illegal");
		head = new Point3D(p);
	}

	/**
	 * constructor that gets 3 double
	 * 
	 * @param x - The x parameter of the end point
	 * @param y - The y parameter of the end point
	 * @param z - The z parameter of the end point
	 */
	public Vector(double x, double y, double z) {
		head = new Point3D(x, y, z);
		if (Point3D.ZERO.equals(head))
			throw new IllegalArgumentException("zero vector is illegal");
	}

	/**
	 * copy constructor
	 * 
	 * @param v - The vector we copy
	 */
	public Vector(Vector v) {
		if (v.head.equals(Point3D.ZERO))
			throw new IllegalArgumentException("zero vector is illegal");
		head = new Point3D(v.getHead());
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
		if (!(obj instanceof Vector))
			return false;
		return head.equals(((Vector) obj).head);
	}

	/**
	 * return string of the vector
	 */
	@Override
	public String toString() {
		return "vec:" + head;
	}

	/************** Operations ***************/
	/**
	 * sub v with our Vector
	 * 
	 * @param v - The vector we sub
	 * @return new vector - The result vector
	 */
	public Vector sub(Vector v) {
		return new Vector(head.sub(v.head));
	}

	/**
	 * add v with our Vector
	 * 
	 * @param v - The vector we add
	 * @return new vector - The result vector
	 */
	public Vector add(Vector v) {
		return new Vector(head.add(v));
	}

	/**
	 * multiply double with our vector
	 * 
 * @param alpha - The lenght we scale the vector
	 * @return new vector - The result vector
	 */
	public Vector scalarMult(double alpha) {
		return new Vector((head.getX().scale(alpha)).get(), (head.getY().scale(alpha)).get(),
				(head.getZ().scale(alpha)).get());
	}

	/**
	 * multiply v with our vector
	 * 
 * @param v - The vector we scale
	 * @return double - The result
	 */
	public double dotProduct(Vector v) {
		return (head.getX().multiply(v.head.getX())
				.add(head.getY().multiply(v.head.getY()).add(head.getZ().multiply(v.head.getZ())))).get();
	}

	/**
	 * crossProduct of v with our vector
	 * 
	 * @param v - The vector we scale
	 * @return new vector - The result vector
	 */
	public Vector crossProduct(Vector v) {
		Coordinate x = head.getX();
		Coordinate y = head.getY();
		Coordinate z = head.getZ();
		Coordinate xv = v.head.getX();
		Coordinate yv = v.head.getY();
		Coordinate zv = v.head.getZ();

		return new Vector((y.multiply(zv)).subtract(z.multiply(yv)).get(),
				(z.multiply(xv)).subtract(x.multiply(zv)).get(), (x.multiply(yv)).subtract(y.multiply(xv)).get());
	}

	/**
	 * length of our vector
	 * 
	 * @return The length of the vector
	 */
	public double lenght() {
		return head.distance(Point3D.ZERO);
	}

	/**
	 * pow length of our vector
	 * 
	 * @return - The pow length of the vector
	 */
	public double powLenght() {
		return head.powDistance(Point3D.ZERO);
	}

	/**
	 * Create new unit vector in the same direction (normal)
	 * 
	 * @return new normal vector
	 */
	public Vector normal() {
		return scalarMult(1 / lenght());
	}

	/**
	 * Normalize this vector (keep the direction but make it unit vector)
	 * 
	 * @return the vector itself
	 */
	public Vector normalize() {
		double lfactor = 1/lenght();
		head.x._coord *= lfactor;
		head.y._coord *= lfactor;
		head.z._coord *= lfactor;
		return this;
	}

	
	/**
	 * Getter of the head
	 * 
	 * @return the point that represent the plane
	 */
	public Point3D getHead() {
		return head;
	}
}
