package unittests.Elements;

import static org.junit.Assert.*;
import org.junit.Test;
import primitives.*;
import elements.*;

public class CameraTest {
	/**
	 * camera for tests
	 */
	Camera camera = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, -1));

	/**
	 * Camera test for constructRayThroughPixel func
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRaysConstruction1() throws Exception {
		Ray ray = camera.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray answer = new Ray(new Point3D(100, -100, -100),
				new Vector(0.5773502691896257, -0.5773502691896257, -0.5773502691896257));
		assertEquals(answer, ray);
	}

	/***
	 * Camera test for x,y,z and constructRayThroughPixel func
	 * 
	 * @throws Exception
	 ***/
	 @Test
	public void testRaysConstruction2() throws Exception {

		final int WIDTH = 3;
		final int HEIGHT = 3;
		Point3D[][] screen = new Point3D[HEIGHT][WIDTH];

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				System.out.println(i + "," + j);

				Ray ray = camera.constructRayThroughPixel(WIDTH, HEIGHT, i, j, 1, 3 * WIDTH, 3 * HEIGHT);

				screen[i][j] = ray.getP0();
				System.out.println(screen[i][j]);

				assertTrue(Double.compare(screen[i][j].getZ().get(), -1.0) == 0);// Checking z-coordinate
				// Checking all options
				double x = screen[i][j].getX().get();
				double y = screen[i][j].getX().get();

				System.out.println();

				if (Double.compare(x, 3) == 0 || Double.compare(x, 0) == 0 || Double.compare(x, -3) == 0) {
					if (Double.compare(y, 3) == 0 || Double.compare(y, 0) == 0 || Double.compare(y, -3) == 0)
						assertTrue(true);
					else
						fail("Wrong y coordinate");
				} else
					fail("Wrong x coordinate");
			}
			System.out.println("---");
		}

	}

	/***
	 * Camera test for x,y,z and constructRayThroughPixel func in 4*4
	 * 
	 * @throws Exception
	 ***/
	@Test
	public void testRaysConstruction3() throws Exception {

		int WIDTH2 = 4;
		int HEIGHT2 = 4;
		Point3D[][] screen = new Point3D[HEIGHT2][WIDTH2];

		for (int i = 0; i < HEIGHT2; i++) {
			for (int j = 0; j < WIDTH2; j++) {
				System.out.println(i + "," + j);

				Ray ray2 = camera.constructRayThroughPixel(WIDTH2, HEIGHT2, i, j, 1, 4 * WIDTH2, 4 * HEIGHT2);

				screen[i][j] = ray2.getP0();
				System.out.println(screen[i][j]);

				assertTrue(Double.compare(screen[i][j].getZ().get(), -1.0) == 0);// Checking z-coordinate
				// Checking all options
				double x = screen[i][j].getX().get();
				double y = screen[i][j].getX().get();

				System.out.println();

				if (Double.compare(x, 8) == 0 || Double.compare(x, -8) == 0 || Double.compare(x, 4) == 0
						|| Double.compare(x, 0) == 0 || Double.compare(x, -4) == 0) {
					if ( Double.compare(y, 8) == 0 ||  Double.compare(y, -8) == 0 ||Double.compare(y, 4) == 0 || Double.compare(y, 0) == 0 || Double.compare(y, -4) == 0)
						assertTrue(true);
					else
						fail("Wrong y coordinate");
				} else
					fail("Wrong x coordinate");
			}
			System.out.println("---");
		}

	}

}
