package unittests.Renderer;

import org.junit.Test;
import static java.awt.Color.*;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.*;
import scene.*;

public class RenderTest {
	@Test
	public void basicRendering() throws Exception {

		Scene scene = new Scene("jojo");
		scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, -1)), 50);
		scene.setAmbientLight(new AmbientLight(new Color(255,0,0), 0.7));
		Sphere sphere = new Sphere(new Color(yellow),50, new Point3D(0, 0, -50));

		scene.addGeometry(sphere);

		Triangle triangle = new Triangle(new Color(blue),new Point3D(100, 0, -51), new Point3D(0, 100, -51),
				new Point3D(100, 100, -51));
		Triangle triangle2 = new Triangle(new Color(green),new Point3D(-100, 0, -51), new Point3D(0, 100, -51),
				new Point3D(-100, 100, -51));

		Triangle triangle3 = new Triangle(new Color(red),new Point3D(100, 0, -51), new Point3D(0, -100, -51),
				new Point3D(100, -100, -51));

		Triangle triangle4 = new Triangle(new Color(gray),new Point3D(-100, 0, -51), new Point3D(0, -100, -51),
				new Point3D(-100, -100, -51));

		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		scene.addGeometry(triangle3);
		scene.addGeometry(triangle4);

		ImageWriter imageWriter = new ImageWriter("Render test", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		render.getImageWriter().writeToimage();
	}
}
