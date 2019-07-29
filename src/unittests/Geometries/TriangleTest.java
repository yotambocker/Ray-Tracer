package unittests.Geometries;

import static geometries.Intersectable.GeoPoint;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import geometries.*;
import primitives.*;
import org.junit.Test;
import elements.*;

/**
 * test for Triangle
 */
public class TriangleTest {

	/**
	 * vector and points for test
	 */
	Point3D p1 = new Point3D(1.0, 2.0, 3.0);
	Point3D p2 = new Point3D(4.0, 5.0, 6.0);
	Point3D p3 = new Point3D(9.0, 11.0, 13.0);

	Point3D _p1 = new Point3D(0, 0, 1);
	Point3D _p2 = new Point3D(0, 0, 6.0);
	Point3D _p3 = new Point3D(0, 0, 13.0);


	@Test
	public void testTriangleIntersections() {

		// building the triangle
		Triangle t = new Triangle(new Color(100, 100, 100), new Point3D(0, 100, -200), new Point3D(100, -100, -200),
				new Point3D(-100, -100, -200));

		// creating the expected values
		List<GeoPoint> answerList = new ArrayList<GeoPoint>();
		answerList.add(new GeoPoint(new Point3D(0.0, 0.0, -200.0), t));

		// building the ray that will intersect the triangle
		Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, -5));

		// testing the findIntersection function
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		list = t.findIntersections(ray);

		/**
		 * testing tangent
		 */
		assertEquals(answerList.get(0).point, list.get(0).point);

		/**************/
		t = new Triangle(new Color(100, 100, 100), new Point3D(0, 0, -3), new Point3D(1, 1, -3), new Point3D(2, 0, -3));
		list = new ArrayList<GeoPoint>();
		list = t.findIntersections(ray);

		answerList = new ArrayList<GeoPoint>();
		/**
		 * testing endpoint
		 */

		assertEquals(answerList, list);

		/**************/
		t = new Triangle(new Color(100, 100, 100), new Point3D(1, 1, -3), new Point3D(3, 3, -3), new Point3D(1, 3, -3));
		list = new ArrayList<GeoPoint>();
		list = t.findIntersections(ray);

		answerList = new ArrayList<GeoPoint>();

		/**
		 * testing empty list
		 */
		assertEquals(answerList, list);

	}

	@Test
	public void TriangleIntersectionPointsTest() throws Exception {
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray[HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
		Triangle triangle = new Triangle(new Color(100, 100, 100), new Point3D(0, 1, -2), new Point3D(1, -1, -2),
				new Point3D(-1, -1, -2));
		Triangle triangle2 = new Triangle(new Color(100, 100, 100), new Point3D(0, 10, -2), new Point3D(1, -1, -2),
				new Point3D(-1, -1, -2));
		List<GeoPoint> intersectionPointsTriangle = new ArrayList<GeoPoint>();
		List<GeoPoint> intersectionPointsTriangle2 = new ArrayList<GeoPoint>();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				List<GeoPoint> rayIntersectionPoints = triangle.findIntersections(rays[i][j]);
				List<GeoPoint> rayIntersectionPoints2 = triangle2.findIntersections(rays[i][j]);
				for (GeoPoint iPoint : rayIntersectionPoints)
					intersectionPointsTriangle.add(iPoint);
				for (GeoPoint iPoint : rayIntersectionPoints2)
					intersectionPointsTriangle2.add(iPoint);
			}
		}
		assertTrue(intersectionPointsTriangle.size() == 1);
		assertTrue(intersectionPointsTriangle2.size() == 2);
		System.out.println("Intersection Points:");
		for (GeoPoint iPoint : intersectionPointsTriangle)
			System.out.println(iPoint);
		System.out.println("--");
		for (GeoPoint iPoint : intersectionPointsTriangle2)
			System.out.println(iPoint);
	}
}
