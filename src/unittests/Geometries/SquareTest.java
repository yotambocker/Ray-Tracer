package unittests.Geometries;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class SquareTest {

	/**
	 * test for point light on square
	 * @throws Exception 
	 */
	@Test
	public void pointLightTest1() throws Exception {
		Scene scene = new Scene("scene");
		scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1)), 100);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

	Square r1= new Square(new Color(0, 0, 100), new Point3D(500, 500, -1000),new Point3D(-500, 500, -1000)
										      , new Point3D(-500, -500, -1000),new Point3D(500, -500, -1000));
	
	Square r2= new Square(new Color(0, 0, 100), new Point3D(500, 500, -1000),new Point3D(-500, 500, -1000)
											  , new Point3D(0, 750, -1000),new Point3D(750, 750, -1000));
	
	Square r3= new Square(new Color(0, 0, 100), new Point3D(500, 500, -1000),new Point3D(500, -500, -1000)
											  , new Point3D(750, -250, -1000),new Point3D(750, 750, -1000));

/*	Square r4= new Square(new Color(0, 0, 100), new Point3D(0, 750, -1000),new Point3D(-500, 500, -1000)
											  , new Point3D(-500, -500, -1000),new Point3D(0, -250, -1000));

	Square r5= new Square(new Color(0, 0, 100), new Point3D(500, -500, -1000),new Point3D(-500, -500, -1000)
											  ,new Point3D(0, -250, -1000), new Point3D( 750,-250, -1000));

	Square r6= new Square(new Color(0, 0, 100), new Point3D(750, 750, -1000),new Point3D(0, 750, -1000)
											  ,new Point3D(0, -250, -1000), new Point3D(750, -250, -1000));*/
		scene.addGeometry(r1);
		scene.addGeometry(r2);
		scene.addGeometry(r3);
//		scene.addGeometry(r4);
//		scene.addGeometry(r5);
//		scene.addGeometry(r6);

		scene.addLight(new PointLight(new Color(255, 100, 100), new Point3D(-200, -200, -100), // -200, -200, -100),
				1, 0, 0));		
		ImageWriter imageWriter = new ImageWriter("square Test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		 render.disableMultiThreading();

		render.renderImage();
		imageWriter.writeToimage();
	}
}