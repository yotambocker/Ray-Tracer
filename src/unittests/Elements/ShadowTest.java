package unittests.Elements;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class ShadowTest {

	/**
	 * shadow test
	 * @throws Exception 
	 */
	@Test
	public void shadowTest() throws Exception {
		Scene scene = new Scene("shadow test");
		scene.setCamera(new Camera(new Point3D(0, 0, 10), new Vector(0, -1, 0), new Vector(0, 0, -1)), 100);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		Sphere sphere = new Sphere(new Color(0, 0, 100), 500, new Point3D(0.0, 0.0, -1000));

		sphere.setMaterial(new Material(1, 1, 0, 0, 20));
		scene.addGeometry(sphere);

		Triangle triangle1 = new Triangle(new Color(0, 0, 0), new Point3D(3500, 3500, -2000),
				new Point3D(-3500, -3500, -1000), new Point3D(3500, -3500, -2000));
		Triangle triangle2 = new Triangle(new Color(0, 0, 0), new Point3D(3500, 3500, -2000),
				new Point3D(-3500, 3500, -1000), new Point3D(-3500, -3500, -1000));
			
		scene.addGeometry(triangle1);
		scene.addGeometry(triangle2);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100),  1, 0.000001, 0.0000005,
				new Vector(-2, -2, -3)));
		ImageWriter imageWriter = new ImageWriter("shadow test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

	/**
	 * Spot Test2
	 * @throws Exception 
	 */
	@Test
	public void spotLightTest2() throws Exception {
		Scene scene = new Scene("Spot Test2");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1)), 200);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(0, 0, 100), 500, new Point3D(0, 0, -1000));
		sphere.setMaterial(new Material(1, 1, 0, 0, 20));
		scene.addGeometry(sphere);

		Triangle triangle = new Triangle(new Color(0, 0, 100), new Point3D(-125, -225, -300),
				new Point3D(-225, -125, -300), new Point3D(-225, -225, -310));
		triangle.setMaterial(new Material(1, 1, 0, 0, 4));
		scene.addGeometry(triangle);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),  1, 0.00001, 0.000005,
				new Vector(2, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("Spot Test2", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

	/**
	 * Tube Test
	 * @throws Exception 
	 */
	@Test
	public void spotLightTest3() throws Exception {
		Scene scene = new Scene("Tube Test");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1)), 200);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Ray axis = new Ray(new Point3D(0, 0, -1000), new Vector(1, -1, 0));
		Tube sphere = new Tube(new Color(0, 0, 100), 500, axis);
		// Cylinder sphere=new Cylinder(new Color(0, 0, 100), 500, axis,100);
		// Sphere sphere = new Sphere(new Color(0, 0, 100), 500, new Point3D(0, 0,
		// -1000));
		sphere.setMaterial(new Material(1, 1, 0, 0, 20));
		scene.addGeometry(sphere);
		Triangle triangle = new Triangle(new Color(0, 0, 100), new Point3D(-125, -225, -300),
				new Point3D(-225, -125, -300), new Point3D(-225, -225, -310));
		triangle.setMaterial(new Material(1, 1, 0, 0, 4));
		scene.addGeometry(triangle);

		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),  1, 0.00001, 0.000005,
				new Vector(2, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("Tube Test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

	/**
	 * 3 sphere test
	 * @throws Exception 
	 */
	@Test
	public void refTest() throws Exception {
		Scene scene = new Scene("3 sphere test");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1)), 150);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(10, 100, 20), 50, new Point3D(-50, -100, -150));
		sphere.setMaterial(new Material(0.55, 0.1, 0, 0, 20));
		scene.addGeometry(sphere);

		Sphere sphere1 = new Sphere(new Color(110, 20, 10), 70, new Point3D(-30, 0, -250));
		sphere1.setMaterial(new Material(0.55, 0.1, 0.5, 0, 20));
		scene.addGeometry(sphere1);

		Sphere sphere2 = new Sphere(new Color(20, 20, 100), 90, new Point3D(-10, 150, -350));
		sphere2.setMaterial(new Material(0.55, 0.1, 0, 0, 20));
		scene.addGeometry(sphere2);

		Plane plane = new Plane(new Color(133, 133, 133), new Point3D(-100, 0, 0), new Vector(1, 0, 0));
		plane.setMaterial(new Material(1, 1, 0, 1, 15));
		scene.addGeometry(plane);

		scene.addLight(new PointLight(new Color(130, 100, 130), new Point3D(150, 150, -50),  1, 0.00001, 0.000005));
		scene.addLight(new PointLight(new Color(110, 130, 30), new Point3D(150, 150, -50),  1, 0.00001, 0.000005));
		scene.addLight(new PointLight(new Color(90, 30, 130), new Point3D(50, 150, -150), 1, 0.00001, 0.000005));
		ImageWriter imageWriter = new ImageWriter("3 sphere test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}
}