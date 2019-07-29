package unittests.Renderer;

import static java.awt.Color.*;
import org.junit.Test;
import renderer.*;

public class ImageWriterTest {

	/**
	 * test for ImageWriter
	 */
	@Test
	public void test() {
		ImageWriter imageWriter = new ImageWriter("Image writer test", 500, 500, 500, 500);
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				if (i % 50 == 0 || j % 50 == 0)
					imageWriter.writePixel(i, j, WHITE);
				else
					imageWriter.writePixel(i, j, BLACK);
			}
		}
		imageWriter.writeToimage();
	}
}