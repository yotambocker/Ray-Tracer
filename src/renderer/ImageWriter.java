package renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.imageio.stream.*;

/**
 * A class that represent the image-writer
 */
public class ImageWriter {

	// fields
	private double _imageWidth, _imageHeight;
	private int _nX, _nY;

	final String PROJECT_PATH = System.getProperty("user.dir");

	private BufferedImage _image;

	private String _imageName;

	/**
	 * A constructor that get all the parameters
	 * 
	 * @param imageName - The name of the image
	 * @param width     - The width of the image
	 * @param height    - The height of the image
	 * @param nX        - The number of pixels in the X axis
	 * @param nY        - The number of pixels in the Y axis
	 */
	public ImageWriter(String imageName, double width, double height, int nX, int nY) {
		_imageName = imageName;
		_imageWidth = width;
		_imageHeight = height;
		_nX = nX;
		_nY = nY;

		_image = new BufferedImage(_nX, _nY, BufferedImage.TYPE_INT_RGB);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter of the width of the image
	 * 
	 * @return - the width of the image
	 */
	public double getWidth() {
		return _imageWidth;
	}

	/**
	 * Getter of the height of the image
	 * 
	 * @return - the height of the image
	 */
	public double getHeight() {
		return _imageHeight;
	}

	/**
	 * Getter of the number of pixels in the Y axis
	 * 
	 * @return - The number of the pixels
	 */
	public int getNy() {
		return _nY;
	}

	/**
	 * Getter of the number of pixels in the X axis
	 * 
	 * @return - The number of the pixels
	 */
	public int getNx() {
		return _nX;
	}

	/**
	 * Setter of the number of pixels in the Y axis
	 * 
	 * @param _Ny - The number of the pixels
	 */
	public void setNy(int _Ny) {
		this._nY = _Ny;
	}

	/**
	 * Setter of the number of pixels in the X axis
	 * 
	 * @param _Nx - The number of the pixels
	 */
	public void setNx(int _Nx) {
		this._nX = _Nx;
	}

	// ***************** Operations ******************** //

	/**
	 * A function that write to a image
	 */
	public void writeToimage() {
		File ouFile = new File(PROJECT_PATH + "/" + _imageName + ".jpg");
		try {
			javax.imageio.ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
			ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
			jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			jpgWriteParam.setCompressionQuality(1f);
			jpgWriter.setOutput(new FileImageOutputStream(ouFile));
			jpgWriter.write(null, new IIOImage(_image, null, null), jpgWriteParam);
			// ImageIO.write(_image, "jpg", ouFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A function that write a pixel
	 * 
	 * @param xIndex - the pixel's index in the X axis
	 * @param yIndex - the pixel's index in the Y axis
	 * @param color  - The color of the pixel
	 */
	public void writePixel(int xIndex, int yIndex, Color color) {
		_image.setRGB(xIndex, yIndex, color.getRGB());
	}

}