package geometries;

import primitives.*;

/**
 * An abstract class of geometry that implements Intersectable
 */
public abstract class Geometry implements Intersectable {

	/**
	 * This function return the orthogonal vector to the geometry in a specific
	 * point
	 * 
	 * @return the 'normal' vector
	 */
	public abstract Vector getNormal(Point3D p);

	private Color _emmission;
	Material _material;

	/**
	 * getter of _emission
	 * 
	 * @return the color's emission of the geometry
	 */
	public Color getEmission() {
		return _emmission;
	}

	/**
	 * A constructor that get the color's emission.
	 * 
	 * @param emmission - The color's emission of the geometry
	 */
	public Geometry(Color emmission) {
		_emmission = emmission;
		_material = new Material(1, 1, 0, 0, 20);
	}

	/**
	 * A constructor that get the color's emission.
	 * 
	 * @param emmission - The color's emission of the geometry
	 */
	public Geometry(Color emmission, Material material) {
		_emmission = emmission;
		_material = material;
	}

	/**
	 * Getter of material
	 * 
	 * @return - The material of the geometry
	 */
	public Material getMaterial() {
		return _material;
	}

	/**
	 * Setter of material
	 * 
	 * @param material - the material of the geometry
	 */
	public void setMaterial(Material material) {
		_material = material;
	}
}