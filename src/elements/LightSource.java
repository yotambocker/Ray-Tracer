package elements;

import primitives.*;

/**
 * An interface of different kinds of light
 */
public interface LightSource {
	/**
	 * A function that get the color with the right intensity in a specific point
	 * 
	 * @param p - The position of the pixel
	 * @return - The the color(include the intensity) of this point
	 */
	Color getIntensity(Point3D p);

	/**
	 * A getter of the 'L' vector to the point we get
	 * 
	 * @param p - The point we want to find there the vector
	 * @return - The 'L' vector
	 */
	Vector getL(Point3D p);
}
