package unittests.Elements;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import static java.awt.Color.*;
import renderer.*;
import scene.Scene;
/**
 * test for emmissionTest, point light and spot light
 */
public class LightTest {
	/**
	 * test for emmission. get 4 triangles and one sphere
	 * @throws Exception 
	 */
	@Test
	public void emmissionTest() throws Exception {
		Scene scene = new Scene("scene");
		scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1)), 50);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(yellow), 50, new Point3D(0, 0, -50));
		scene.addGeometry(sphere);
		Triangle triangle = new Triangle(new Color(blue), new Point3D(100, 0, -51), new Point3D(0, 100, -51),
				new Point3D(100, 100, -51));
		Triangle triangle2 = new Triangle(new Color(green), new Point3D(-100, 0, -51), new Point3D(0, 100, -51),
				new Point3D(-100, 100, -51));
		Triangle triangle3 = new Triangle(new Color(red), new Point3D(100, 0, -51), new Point3D(0, -100, -51),
				new Point3D(100, -100, -51));
		Triangle triangle4 = new Triangle(new Color(gray), new Point3D(-100, 0, -51), new Point3D(0, -100, -51),
				new Point3D(-100, -100, -51));
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		scene.addGeometry(triangle3);
		scene.addGeometry(triangle4);

		ImageWriter imageWriter = new ImageWriter("Render test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	/**
	 * test for point light on sphere
	 * @throws Exception 
	 */
	@Test
	public void pointLightTest1() throws Exception {
		Scene scene = new Scene("scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 10), new Vector(0, -1, 0), new Vector(0, 0, -1)), 100);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(0, 0, 100), 800, new Point3D(0, 0, -1000));
		sphere.setMaterial(new Material(1,1,0,0, 20));
		scene.addGeometry(sphere);
		scene.addLight(new PointLight(new Color(255, 100, 100), new Point3D(-200, -200, -100), // -200, -200, -100),
				1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("Point Test1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

	/**
	 * test for point light on 2 triangles
	 * @throws Exception 
	 */
	@Test
	public void pointLightTest2() throws Exception {
		Scene scene = new Scene("scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 10), new Vector(0, -1, 0), new Vector(0, 0, -1)), 100);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Triangle triangle = new Triangle(new Color(0, 0, 0), new Point3D(3500, 3500, -2000),
				new Point3D(-3500, -3500, -1000), new Point3D(3500, -3500, -2000));
		Triangle triangle2 = new Triangle(new Color(0, 0, 0), new Point3D(3500, 3500, -2000),
				new Point3D(-3500, 3500, -1000), new Point3D(-3500, -3500, -1000));
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		scene.addLight(new PointLight(new Color(255, 100, 100), new Point3D(-200, 200, -100), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("Point Test2", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}
	
	/**
	 * test for spot light on sphere
	 * @throws Exception 
	 */
	@Test
	public void spotLightTest1() throws Exception {
		Scene scene = new Scene("scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 10), new Vector(0, -1, 0), new Vector(0, 0, -1)), 100);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Sphere sphere = new Sphere(new Color(0, 0, 100), 800, new Point3D(0, 0, -1000));
		sphere.setMaterial(new Material(1, 1,0,0, 20));
		scene.addGeometry(sphere);
		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -100), 1, 0, 0,
				new Vector(2, 2, -3)));

		ImageWriter imageWriter = new ImageWriter("Spot Test1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();
	}

	/**
	 * test for spot light on 2 triangles
	 * @throws Exception 
	 */
	@Test
	public void spotLightTest3() throws Exception {
		Scene scene = new Scene("scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 10), new Vector(0, -1, 0), new Vector(0, 0, -1)), 100);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		Triangle triangle = new Triangle(new Color(0, 0, 0), new Point3D(3500, 3500, -2000),
				new Point3D(-3500, -3500, -1000), new Point3D(3500, -3500, -2000));
		Triangle triangle2 = new Triangle(new Color(0, 0, 0), new Point3D(3500, 3500, -2000),
				new Point3D(-3500, 3500, -1000), new Point3D(-3500, -3500, -1000));
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100), 0, 0.000001, 0.0000005,
				new Vector(-2, -2, -3)));

		ImageWriter imageWriter = new ImageWriter("Spot Test3", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		imageWriter.writeToimage();

	}
}
