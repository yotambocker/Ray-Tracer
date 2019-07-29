package elements;


import primitives.Color;

/**
 * An abstract class that help us to represent a different types of light
 */
public abstract class Light {
	/**
	 * fields
	 */
	private Color _color;

	/**
	 * A getter of the Intensity of the color
	 * 
	 * @return - the Intensity of the color
	 */
	public Color getIntensity() {
		return _color;
	}

	/**
	 * A constructor that get the color of the light
	 * 
	 * @param color - The color of the light
	 */
	public Light(Color color) {
		_color = color;
	}
}
