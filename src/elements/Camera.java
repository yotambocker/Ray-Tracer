package elements;

import static primitives.Util.*;

import java.util.Map;

import primitives.*;

/**
 * class of the camera
 * 
 * @author yotam.nehama
 *
 */
public class Camera {
	/**
	 * fields
	 */
	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;


	/**
	 * Getter of _p0
	 * 
	 * @return the place of the camera
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * Getter of vUp
	 * 
	 * @return vUp - Direction up from the camera
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * Getter of vTo
	 * 
	 * @return vTo - The direction, which the camera is looking at it
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * Getter of vRight
	 * 
	 * @return vRight - Direction right from the camera
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * constructor of Camera that get the location of the camera and two vector's of
	 * directions and build the camera accordingly
	 * 
	 * @param _p0  - Camera's location
	 * @param _vUp - The direction of up from the camera - helps to determine the
	 *             direction of the image
	 * @param _vTo - The direction of where the camera is looking at
	 */
	public Camera(Point3D _p0, Vector _vUp, Vector _vTo) {
		if (!isZero(_vUp.dotProduct(_vTo)))
			throw new IllegalArgumentException("ecurrecct input");
		p0 = _p0;
		vUp = _vUp.normal();
		vTo = _vTo.normal();
		vRight = vTo.crossProduct(vUp).normal();
	}

	/**
	 * constructRayThroughPixel
	 * 
	 * 
	 * @param nX           - The number of pixels in the X axis
	 * @param nY           - The number of pixels in the Y axis
	 * @param i            - The specific place in the Y axis of this pixel
	 * @param j            - The specific place in the X axis of this pixel
	 * @param screenDist   - The camera distance from the screen
	 * @param sWidth  - The width of the screen
	 * @param sHeight - The height of the screen
	 * @return ray from - The that we build throw this pixel
	 * @throws Exception
	 */
	public Ray constructRayThroughPixel(int nX, int nY, double i, double j, double sDistance, double sWidth, double sHeight) {
		Point3D pc = p0.add(vTo.scalarMult(sDistance));
		double x = (sWidth / nX) * (j - (nX - 1) / 2);
		double y = (sHeight / nY) * (i - (nY - 1) / 2);
		if (!isZero(x)) pc = pc.add(vRight.scalarMult(x));
		if (!isZero(y)) pc = pc.add(vUp.scalarMult(-y));
		return new Ray(pc, pc.sub(p0).normal());
	}
	
	
	/**
	 * Camera from xml
	 * @param attributes
	 */
	public Camera(Map<String, String> attributes) {
		String[] P0params = attributes.get("p0").split("\\s+");
		p0 = new Point3D(Double.valueOf(P0params[0]), Double.valueOf(P0params[1]), Double.valueOf(P0params[2]));
		String[] vToParam = attributes.get("vTo").split("\\s+");
		vTo = new Vector(Double.valueOf(vToParam[0]), Double.valueOf(vToParam[1]), Double.valueOf(vToParam[2]));
		String[] vUpParam = attributes.get("vUp").split("\\s+");
		vUp = new Vector(Double.valueOf(vUpParam[0]), Double.valueOf(vUpParam[1]), Double.valueOf(vUpParam[2]));
		vRight = vUp.crossProduct(vTo);
		vUp = vTo.crossProduct(vRight);
		vUp = vUp.normal();
		vTo = vTo.normal();
		vRight = vRight.normal();
	}

	
	
	
	
}