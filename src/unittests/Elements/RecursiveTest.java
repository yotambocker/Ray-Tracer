package unittests.Elements;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class RecursiveTest {
/**
 * Recursive Test
 * @throws Exception 
 */
	@Test
	public void recursiveTest() throws Exception {
		Scene scene = new Scene("Recursive Test");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1)), 300);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(0, 0, 100), 500, new Point3D(0.0, 0.0, -1000));
		sphere.setMaterial(new Material(1, 1, 0.5, 	1, 20));
		scene.addGeometry(sphere);

		Sphere sphere2 = new Sphere(new Color(100, 20, 20), 250, new Point3D(0.0, 0.0, -1000));
		sphere2.setMaterial(new Material(1, 1, 0, 0, 20));
		scene.addGeometry(sphere2);

		scene.addLight(
				new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150), 1, 0.00001, 0.000005, new Vector(2, 2, -3)));

		ImageWriter imageWriter = new ImageWriter("Recursive Test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}
/**
 * Recursive Test 2
 * @throws Exception 
 */
	@Test
	public void recursiveTest2() throws Exception {
		Scene scene = new Scene("Recursive Test 2");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1)), 300);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(0, 0, 100), 300, new Point3D(-550, -500, -1000));
		sphere.setMaterial(new Material(1, 1, 0.5, 0, 20));
		scene.addGeometry(sphere);

		Sphere sphere2 = new Sphere(new Color(100, 20, 20), 150, new Point3D(-550, -500, -1000));
		sphere2.setMaterial(new Material(1, 1, 0, 0, 20));
		scene.addGeometry(sphere2);

		Triangle triangle = new Triangle(new Color(20, 20, 20), new Point3D(1500, -1500, -1500),
				new Point3D(-1500, 1500, -1500), new Point3D(200, 200, -375));
		Triangle triangle2 = new Triangle(new Color(20, 20, 20), new Point3D(1500, -1500, -1500),
				new Point3D(-1500, 1500, -1500), new Point3D(-1500, -1500, -1500));
		triangle.setMaterial(new Material(1, 1, 1, 1, 20));
		triangle2.setMaterial(new Material(1, 1, 1, 0.5, 20));
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -150), 1, 0.00001, 0.000005,
				new Vector(-2, -2, -3)));

		ImageWriter imageWriter = new ImageWriter("Recursive Test 2", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

	/**
	 * Recursive Test 3
	 * @throws Exception 
	 */
	@Test
	public void recursiveTest3() throws Exception {
		Scene scene = new Scene("Recursive Test 3");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1)), 300);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(0, 0, 100), 300, new Point3D(0, 0, -1000));
		sphere.setMaterial(new Material(1, 1, 0.5, 0, 20));
		scene.addGeometry(sphere);

		Sphere sphere2 = new Sphere(new Color(100, 20, 20), 150, new Point3D(0, 0, -1000));
		sphere2.setMaterial(new Material(1, 1, 0, 0, 20));
		scene.addGeometry(sphere2);

		Triangle triangle = new Triangle(new Color(20, 20, 20), new Point3D(2000, -1000, -1500),
				new Point3D(-1000, 2000, -1500), new Point3D(700, 700, -375));
		Triangle triangle2 = new Triangle(new Color(20, 20, 20), new Point3D(2000, -1000, -1500),
				new Point3D(-1000, 2000, -1500), new Point3D(-1000, -1000, -1500));
		triangle.setMaterial(new Material(1, 1, 0, 1, 20));
		triangle2.setMaterial(new Material(1, 1, 0, 0.5, 20));
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -150), 1, 0.00001, 0.000005,
				new Vector(-2, -2, -3)));

		ImageWriter imageWriter = new ImageWriter("Recursive Test 3", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}
}