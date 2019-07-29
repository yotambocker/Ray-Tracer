package primitives;
/**
 * class Ray
 */
public class Ray {
	private Point3D _p0;
	private Vector _direction;
	
	private static final double EPS = 0.1;

	/**** Constructors *****/
/**
 * ctor that get Point3D and Vector. and we normal the vector
 * @param p
 * @param v
 */
	
	public Ray(Point3D p, Vector v) {
		_p0 =new Point3D(p);
		_direction =new Vector(v.normal());
	}

	/**
	 * Ray constructor moving the point by EPS on the normal line in v direction
	 * @param point point before moving
	 * @param v a normalized vector
	 * @param n a normal unit vector
	 */
	public Ray(Point3D point, Vector v, Vector n) {
		_direction = v.normal();
		Vector epsVector = n.scalarMult(n.dotProduct(_direction) > 0 ? EPS : -EPS);
		_p0 = point.add(epsVector);
	}
	
	/***** Admin *******/
	/**
	 * return true if its equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		return _p0.equals(((Ray) obj)._p0) && _direction.equals(((Ray) obj)._direction);
	}
	/**
	 * getter of p0 
	 * @return p0
	 */
	public Point3D getP0() {
		return _p0;
	}
	/**
	 * getter of the direction
	 * @return the direction
	 */
	public Vector getDirection() {
		return _direction;
	}
/**
 * return string of the ray
 */
	@Override
	public String toString() {
		return "_p0: " + _p0 + " " + _direction;
	}
}