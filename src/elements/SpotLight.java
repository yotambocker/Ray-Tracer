package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * This class implement pointLight class and represent a spot-light by vector of
 * the direction, position and all the fields that we have in PointLight class
 */
public class SpotLight extends PointLight {
	private Vector _direction;

	/**
	 * A constructor that get the color the position and all the properties and
	 * build the spot-light
	 * 
	 * @param color     - The color of the light
	 * @param position  - The position of the light source
	 * @param kC        - Real numbers represent the attenuation factors with the
	 *                  distance d (the thinness of the light)
	 * @param kL        - Real numbers represent the attenuation factors with the
	 *                  distance d (the thinness of the light)
	 * @param kQ        - Real numbers represent the attenuation factors with the
	 *                  distance d (the thinness of the light)
	 * @param direction - The direction of the light
	 */
	public SpotLight(Color color, Point3D position, double kC, double kL, double kQ, Vector direction) {
		super(color, position, kC, kL, kQ);
		_direction = direction.normal();
	}

	@Override
	public Color getIntensity(Point3D p) {
		double d = _direction.dotProduct(getL(p));
		if (d <= 0) return Color.BLACK;
		return super.getIntensity(p).scale(d);
	}
}