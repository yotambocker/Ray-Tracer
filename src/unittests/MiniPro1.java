package unittests;

import java.util.List;
import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import java.util.ArrayList;
import static primitives.Color.*;
/**
 * the main test for the mini project
 * @author yotam.nehama
 *
 */
public class MiniPro1 {

	Geometries geometries = new Geometries();
	List<LightSource> lights = new ArrayList<>();

	/**
	 * A function that build a spheres for the scene
	 */
	private void sphere() {
		geometries.add(
		new Sphere(yellow, 40, new Point3D(150, 40, -300)),
		new Sphere(blue,40, new Point3D(50, 80, -300)),
		new Sphere(red, 40, new Point3D(-50, 20, -300)),//
		new Sphere(green, 40, new Point3D(-150, 0, -300)),
		
		new Sphere(yellow, 40, new Point3D(550, 40, -300)),
		new Sphere(blue, 40, new Point3D(450, 80, -300)),
		new Sphere(red, 40, new Point3D(350, 20, -300)),
		new Sphere(green, 40, new Point3D(250, 0, -300)),
		
		new Sphere(green, 40, new Point3D(-550, 40, -300)),
		new Sphere(red, 40, new Point3D(-450, 80, -300)),
		new Sphere(blue,40, new Point3D(-350, 20, -300)),
		new Sphere(yellow, 40, new Point3D(-250, 0, -300)));	
	}

	/**
	 * A function that build a cylinders for the scene
	 */
	private void cylinder() {
		geometries.add(
		new Cylinder(ctubes, 5, new Ray(new Point3D(150, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(50, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(-50, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(-150, -80, -300), new Vector(0, 1, 0)), 400),

		new Cylinder(ctubes, 5, new Ray(new Point3D(550, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(450, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(350, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(250, -80, -300), new Vector(0, 1, 0)), 400),
		
		new Cylinder(ctubes, 5, new Ray(new Point3D(-550, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(-450, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(-350, -80, -300), new Vector(0, 1, 0)), 400),
		new Cylinder(ctubes, 5, new Ray(new Point3D(-250, -80, -300), new Vector(0, 1, 0)), 400),

		//on the net
		new Cylinder(red, 40, new Ray(new Point3D(250, -120, -300), new Vector(0, 1, 0)), 80),
		new Cylinder(blue, 40, new Ray(new Point3D(150, -50, -300), new Vector(0, 1, 0)), 80),
		new Cylinder(green, 40, new Ray(new Point3D(50, -80, -300), new Vector(0, 1, 0)), 80),
		new Cylinder(yellow, 40, new Ray(new Point3D(-50, -150, -300), new Vector(0, 1, 0)), 80),
		new Cylinder(red, 40, new Ray(new Point3D(-150, -100, -300), new Vector(0, 1, 0)), 80),		
		new Cylinder(blue, 40, new Ray(new Point3D(-250, -180, -300), new Vector(0, 1, 0)), 80));

	}

	/**
	 * A function that build a boxes as cubes for the scene
	 */
	private void box() {
	geometries.add(
		new Box(green, Material.regular, new Point3D(-590.0,-240.0,-340.0),new Point3D(-510.0,-160.0,-260.0) ),
		new Box(yellow, Material.regular, new Point3D(-490.0,-60.0,-340.0), new Point3D(-410.0,20.0,-260.0)),
		new Box(red, Material.regular,new Point3D(-390.0,-140.0,-340.0), new Point3D(-310.0,-60.0,-260.0)),
		new Box(blue, Material.regular, new Point3D(510.0,-220.0,-340.0), new Point3D(590.0,-140.0,-260.0)),
		new Box(yellow, Material.regular,new Point3D(410.0,-90.0,-340.0), new Point3D(490.0,-10.0,-260.0)),
		new Box(green, Material.regular, new Point3D(310.0,-140.0,-340.0),new Point3D(390.0,-60.0,-260.0)));
	}

	/**
	 * A function that build a circles for the scene
	 */
	private void circle() {
		Point3D p=new Point3D(650, 120, -300);
		Vector v=new Vector(-100, 0, 0);
		for (int i = 0; i < 12; i++) {
			p=p.add(v);
			geometries.add(new Circle(red,Material.regular,p , new Vector(0,1,0),40));
		}
	}

	/**
	 * A function that build a special elements for the scene
	 */
	private void Special() {
		geometries.add(
		new Sphere(BLACK, Material.mirror,50, new Point3D(200, 100, -250)),
		new Sphere(BLACK,Material.mirror, 50, new Point3D(-200, 100, -250)),
		new Circle(BLACK,Material.regular, new Point3D(0, 10, -250), new Vector(0,0,1),80,78),
		new Cylinder(BLACK,new Material(1, 1, 0.9, 0.9, 20),2, new Ray(new Point3D(0,130,-250), new Vector(0,1,0)), 80),
		new Circle(BLACK,Material.glass, new Point3D(0, 10, -250), new Vector(0,0,1),80)
		);		
	}

	/**
	 * A function that build a light sources for the scene
	 */
	private void light() {
		lights.add(new PointLight(new Color(130, 100, 130), new Point3D(0, -200, -450), 1, 0.00001, 0.000005));
		lights.add(new PointLight(new Color(130, 100, 130), new Point3D(0, -200, -200), 1, 0.00001, 0.000005));
	}

/**
 * main test
 */
	@Test
	public void miniP() {
		Scene scene = new Scene("matt");
		scene.setCamera(new Camera(new Point3D(0, -300, 1000), new Vector(0, -1, -0.2), new Vector(0, 0.2, -1)), 1200);
		scene.setAmbientLight(new AmbientLight(new Color(0, 0, 0), 0.1));
	
		//Call for functions above
		sphere();
		cylinder();
		box();
		circle();
		Special();
		light();
	
		// Adding the light sources and the geometries to the scene
		scene.setLightSources(lights);
		scene.setGeometries(geometries);

		ImageWriter imageWriter = new ImageWriter("miniProject", 1200, 400, 2400, 800);
		Render render = new Render(imageWriter, scene);
		render.setDiffusionRays(20);
		//render.disableMultiThreading();
	
		render.setGridOpt(true);// using a grid to improve performance
		render.renderImage();
		imageWriter.writeToimage();	
	}
}