package unittests.Geometries;

import geometries.*;
import static org.junit.Assert.*;
import primitives.*;
import org.junit.Test;
import elements.*;
import java.util.*;
import primitives.Vector;
import static geometries.Intersectable.GeoPoint;

/**
 * test for Plane
 */
public class PlaneTest {

	/**
	 * vector and points for test
	 */
	Point3D p1 = new Point3D(1.0, 2.0, 3.0);
	Point3D p2 = new Point3D(4.0, 5.0, 6.0);
	Point3D p3 = new Point3D(9.0, 11.0, 13.0);

	

	@Test
	public void testPlaneIntersections() {
		Plane plane = new Plane(new Color(100, 100,100 ),new Point3D(0, 100, -200), new Vector(new Point3D(0, 0, -1)));
		// creating the expected values
		List<GeoPoint> answerList = new ArrayList<GeoPoint>();
		answerList.add(new GeoPoint(new Point3D(0, 0, -200),plane));

		// building the plane

		// building the ray that will intersect the plane
		Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, -5));

		// testing the findIntersection function

		List<Point3D> list = new ArrayList<Point3D>();
		list.add(plane.findIntersections(ray).get(0).point);
		// testing the findIntersection function
		assertEquals(answerList.get(0).point, list.get(0));

		// test for ray that parallel to the plane
		Plane plane2 = new Plane(new Color(100, 100,100 ),new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(1, 1, 0));
		Ray ray2 = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1));
		list.clear();
		answerList.clear();
		answerList = plane2.findIntersections(ray2);
		// test for ray that parallel to the plane
		assertEquals(answerList, list);
	}

	/* Plane test */
	@Test
	public void testIntersectionPoints() {
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray[HEIGHT][WIDTH];
		Camera camera = new Camera(Point3D.ZERO, new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
		// plane orthogonal to the view plane
		Plane plane = new Plane(new Color(100, 100,100 ),new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.0, -1.0));
		// 45 degrees to the view plane
		Plane plane2 = new Plane(new Color(100, 100,100 ),new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.25, -1.0));
		List<GeoPoint> intersectionPointsPlane = new ArrayList<GeoPoint>();
		List<GeoPoint> intersectionPointsPlane2 = new ArrayList<GeoPoint>();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				try {
					rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				} catch (Exception e) {
					System.out.println("ecorrect input");
				}
				List<GeoPoint> rayIntersectionPoints = plane.findIntersections(rays[i][j]);
				List<GeoPoint> rayIntersectionPoints2 = plane2.findIntersections(rays[i][j]);
				for (GeoPoint iPoint : rayIntersectionPoints)
					intersectionPointsPlane.add(iPoint);
				for (GeoPoint iPoint : rayIntersectionPoints2)
					intersectionPointsPlane2.add(iPoint);
			}
		}
		assertTrue(intersectionPointsPlane.size() == 9);
		assertTrue(intersectionPointsPlane2.size() == 9);
		for (GeoPoint iPoint : intersectionPointsPlane)
			System.out.println(iPoint);
		System.out.println("---");
		for (GeoPoint iPoint : intersectionPointsPlane2)
			System.out.println(iPoint);
	}
}