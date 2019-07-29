package unittests.Geometries;
import static geometries.Intersectable.GeoPoint;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import primitives.*;
import org.junit.Test;
import geometries.*;
import elements.*;

/**
 * test for Sphere
 */
public class SphereTest {
	/**
	 * points for the test
	 */
	Point3D p1 = new Point3D(1.0, 1.0, 1.0);
	Point3D p2 = new Point3D(2.0, 2.0, Math.sqrt(2) + 1);


	
	/**
	 * Test of testFindIntersections
	 */
	@Test
	public void testFindIntersections() {
		/**
		 * test for p0==o
		 */
		Sphere sphere = new Sphere(new Color(100, 100,100 ),5, new Point3D(0, 0, 1));
		Ray ray = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, -2));
		List<Point3D> list = new ArrayList<Point3D>();

		list.add(new Point3D(0, 0, -4));
		assertEquals(sphere.findIntersections(ray).get(0).point, list.get(0));

		/**
		 * test for p0 inside the sphere with the same Direction
		 */

		sphere = new Sphere(new Color(100, 100,100 ),5, new Point3D(0, 0, 1));
		ray = new Ray(new Point3D(0, 0, 2), new Vector(0, 0, 1));
		list.clear();
		list.add(new Point3D(0, 0, 6));
		assertEquals(sphere.findIntersections(ray).get(0).point, list.get(0));

		/**
		 * test for p0 inside the sphere with the different Direction
		 */

		sphere = new Sphere(new Color(100, 100,100 ),4, Point3D.ZERO);
		ray = new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0));
		list = new ArrayList<Point3D>();
		list.add(new Point3D(Math.sqrt(15), 1, 0));
		assertEquals(sphere.findIntersections(ray).get(0).point, list.get(0));

		/**
		 * Test for ray Parallel to the sphere without passing through it
		 */
		sphere = new Sphere(new Color(100, 100,100 ),1, Point3D.ZERO);
		ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 0));
		list = new ArrayList<Point3D>();
		assertEquals(sphere.findIntersections(ray), list);

		/**
		 * Test for getting 2 point on the sphere
		 */
		ray = new Ray(new Point3D(0, 0, 3), new Vector(0, 0, -1));
		list.add(new Point3D(0, 0, 1));
		list.add(new Point3D(0, 0, -1));
		assertEquals(sphere.findIntersections(ray).get(0).point, list.get(0));
		assertEquals(sphere.findIntersections(ray).get(1).point, list.get(1));
		/**
		 * test for ray that tangent to the sphere
		 */
		ray = new Ray(new Point3D(0, 1, 1), new Vector(0, -5, 0));
		list.clear();
		list.add(new Point3D(0, 0, 1));
		assertEquals(sphere.findIntersections(ray).get(0).point, list.get(0));

		/**
		 * test for when the ray start on the surface of the sphere and go away from it
		 */
		ray = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1));
		// System.out.println(sphere.findIntersections(ray));
		list.clear();
		assertEquals(sphere.findIntersections(ray), Intersectable.EMPTY_LIST);
	}

	/**
	 * Sphere test
	 * (new GeoPoint(p0.add(v.scalarMult(t)),this));

	 * @throws Exception
	 **/
	@Test
	public void testIntersectionPoints() throws Exception {
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray[HEIGHT][WIDTH];
		Camera camera = new Camera(Point3D.ZERO, new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
		Sphere sphere = new Sphere(new Color(100, 100,100 ),1, new Point3D(0.0, 0.0, -3.0));
		Sphere sphere2 = new Sphere(new Color(100, 100,100 ),10, new Point3D(0.0, 0.0, -3.0));
		Sphere sphere3 = new Sphere(new Color(100, 100,100 ),1, new Point3D(0.0, 0.0, 1));
		// Only the center ray intersect the sphere in two locations
		List<GeoPoint> intersectionPointsSphere = new ArrayList<GeoPoint>();
		// The sphere encapsulates the view plane - all rays intersect with the sphere
		// once
		List<GeoPoint> intersectionPointsSphere2 = new ArrayList<GeoPoint>();
		// empty list when its difference direction
		List<GeoPoint> intersectionPointsSphere3 = new ArrayList<GeoPoint>();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				List<GeoPoint> rayIntersectionPoints = sphere.findIntersections(rays[i][j]);
				List<GeoPoint> rayIntersectionPoints2 = sphere2.findIntersections(rays[i][j]);
				List<GeoPoint> rayIntersectionPoints3 = sphere3.findIntersections(rays[i][j]);

				for (GeoPoint iPoint : rayIntersectionPoints)
					intersectionPointsSphere.add(iPoint);
				for (GeoPoint iPoint : rayIntersectionPoints2)
					intersectionPointsSphere2.add(iPoint);
				for (GeoPoint iPoint : rayIntersectionPoints3)
					intersectionPointsSphere3.add(iPoint);
			}
		}
		assertTrue(intersectionPointsSphere.size() == 2);
		assertTrue(intersectionPointsSphere2.size() == 9);
		assertTrue(intersectionPointsSphere3.size() == 0);

		System.out.println("Intersection Points:");
		for (GeoPoint iPoint : intersectionPointsSphere) {
			
			assertTrue(iPoint.point.equals(new Point3D(0.0, 0.0, -2.0)) || iPoint.point.equals(new Point3D(0.0, 0.0, -4.0)));
			System.out.println(iPoint);
		}
	}
	
	@Test
	public void ddd() {
		Sphere sphere = new Sphere(new Color(100, 100,100 ),50.0, new Point3D(0.0,0.0,-50.0));
		Ray ray = new Ray(new Point3D(-159.0,156.0,-100.0), new Vector(-0.6511965114485825,0.6389097848174772,-0.4095575543701777));
		System.out.println(sphere.findIntersections(ray));

	
	}
}