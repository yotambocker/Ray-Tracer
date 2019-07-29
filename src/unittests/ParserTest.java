package unittests;

import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import scene.SceneBuilder;
import org.junit.Test;

/**
 * test for xml. 4 triangle and 1 sphere.
 */
public class ParserTest {

	@Test
	public void basicRendering() throws Exception {
		SceneBuilder sceneBuilder = new SceneBuilder("xml.txt");
		Scene scene = sceneBuilder.getScene();

		ImageWriter imageWriter = new ImageWriter("Render test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.printGrid(50);
		render.getImageWriter().writeToimage();
	}

}