package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * A class that represent a direction light by vector of the direction (extends
 * light and LightSourse)
 */
public class DirectionalLight extends Light implements LightSource {

	private Vector _direction;

	/**
	 * A constructor that get the parameters
	 * @param c- The color of the light
	 * @param direction - The direction of the light
	 */
	public DirectionalLight(Color c, Vector direction) {
		super(c);
		_direction = direction.normal();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity();
	}

	@Override
	public Vector getL(Point3D p) {
		return _direction;
	}

}
