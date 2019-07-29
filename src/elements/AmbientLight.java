package elements;


import primitives.*;

/**
 * An ambient light source represents a fixed-intensity and fixed-color light
 * source that affects all objects in the scene equally.
 */
public class AmbientLight extends Light {

	/**
	 * constructor that get the color of the ambient light and the ka
	 * 
	 * @param color - The color of the light
	 * @param ka    - Coefficient of displacement
	 */
	public AmbientLight(Color color, double ka) {
		super(color.scale(ka));
	}


	
	
	
}
