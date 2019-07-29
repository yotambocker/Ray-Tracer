package geometries;

import primitives.Color;
import primitives.Material;

/**
 * An abstract class that help us to determined radial shapes
 */
public abstract class RadialGeometry extends Geometry {

	protected double _radius;

	/**
	 * A constructor that get the radius
	 * 
	 * @param rad - The radius
	 */
	public RadialGeometry(Color emmission, double rad) {
		super(emmission);
		_radius = rad;

	}
	/**
	 * A constructor with material
	 * @param emmission
	 * @param material
	 * @param rad
	 */
	public RadialGeometry(Color emmission,Material material, double rad) {
		super(emmission,material);
		_radius = rad;

	}

	/**
	 * A copy-constructor
	 * 
	 * @param emmission - The radial geometry emission color
	 * @param r         - The radial-shape we are copying
	 */
	public RadialGeometry(Color emmission, RadialGeometry r) {
		super(emmission);
		_radius = r.getRad();
	}

	/**
	 * Getter of the radius
	 * 
	 * @return - The radius of the radial geometry shape
	 */
	public double getRad() {
		return _radius;
	}

	@Override
	public String toString() {
		return "_radius: " + _radius;
	}
}