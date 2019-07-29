package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * This class represents a point-light by its position and its exclusion
 * coefficients
 */
public class PointLight extends Light implements LightSource {
	// fields
	private Point3D _position;
	private double _kC, _kL, _kQ;

	/**
	 * A constructor that get all the properties of the light and build the
	 * point-light
	 * 
	 * @param color    - the color of the light
	 * @param position - the position of the light
	 * @param kC
	 * @param kL
	 * @param kQ
	 */
	public PointLight(Color color, Point3D position, double kC, double kL, double kQ) {
		super(color);
		_kC = kC;
		_kL = kL;
		_kQ = kQ;
		_position = position;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double d = _position.distance(p);
		//System.out.println(d);
		return getIntensity().reduce(_kC + _kL * d + _kQ * d * d);
	}

	@Override
	public Vector getL(Point3D p) {
		return p.sub(_position).normal();
	}

}
