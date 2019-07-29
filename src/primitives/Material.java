package primitives;

/**
 * A class that represents the material of a geometries
 */
public class Material {

	/**
	 * new Material(1, 1, 0, 0, 20);
	 */
	public static final Material regular = new Material(1, 1, 0, 0, 20);
	/**
	 * new Material(0, 1, 0, 0.8, 20, 0.0, 0.2)
	 */
	public static final Material mirror = new Material(0, 1, 0, 0.8, 20, 0.0, 0.2);
	/**
	 * new Material(0, 0, 1, 0, 20, 0.05, 0.0);
	 */
	public static final Material glass = new Material(0, 0, 1, 0, 20, 0.5, 0.0);

	double _kD, _kS, _kR, _kT, _rR, _rT;
	int _nShininess;

	/**
	 * A constructor that get all the coefficients
	 * 
	 * @param kD - Diffusion attenuation coefficient
	 * @param kS - Specular attenuation coefficient
	 * @param kT - Refraction coefficient (1 for transparent)
	 * @param kR - Reflection coefficient (1 for mirror)
	 * @param    nShininess- Refraction index
	 */
	public Material(double kD, double kS, double kT, double kR, int nShininess) {
		_nShininess = nShininess;
		_kD = kD;
		_kS = kS;
		_kR = kR;
		_kT = kT;
		_rR = 0.0;
		_rT = 0.0;
	}

	/**
	 * A constructor that get all the parameters
	 * 
	 * @param kD         - Diffusion attenuation coefficient
	 * @param kS         - Specular attenuation coefficient
	 * @param kT         - Refraction coefficient (1 for transparent)
	 * @param kR         - Reflection coefficient (1 for mirror)
	 * @param nShininess - Refraction index
	 * @param rT         - The Diffused
	 * @param rR         - The Glossy
	 */
	public Material(double kD, double kS, double kT, double kR, int nShininess, double rT, double rR) {
		_nShininess = nShininess;
		_kD = kD;
		_kS = kS;
		_kR = kR;
		_kT = kT;
		_rR = rR;
		_rT = rT;
	}

	/**
	 * Getter of the Refraction diffusion
	 * 
	 * @return _RT
	 */
	public double getRt() {
		return _rT;
	}

	/**
	 * Getter of the Reflection diffusion
	 * 
	 * @return _rR
	 */
	public double getRr() {
		return _rR;
	}

	/**
	 * Getter of the Refraction
	 * 
	 * @return _kT
	 */
	public double getKt() {
		return _kT;
	}

	/**
	 * Getter of the Reflection
	 * 
	 * @return _kR
	 */
	public double getKr() {
		return _kR;
	}

	/**
	 * Getter of the specular attenuation coefficient
	 * 
	 * @return - ks
	 */
	public double getKs() {
		return _kS;
	}

	/**
	 * Getter of the diffusion attenuation coefficient
	 * 
	 * @return - kd
	 */
	public double getKd() {
		return _kD;
	}

	/**
	 * Getter of the refraction index
	 * 
	 * @return - _nShininess
	 */
	public int getNShininess() {
		return _nShininess;
	}
}