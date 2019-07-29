package scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import parser.SceneDescriptor;
import renderer.ImageWriter;

/**
 * class of Scene that build from xml
 */
public class SceneBuilder {

	private SceneDescriptor _sceneDescriptor;
	private Scene _scene;
	private ImageWriter _imageWriter;
	private final String SCENE_FILE_PATH = System.getProperty("user.dir") + "/src/";

	String sceneXMLDesc;

	/**
	 * ctor of SceneBuilder that get sceneFileName and destinationDirectory
	 * 
	 * @param sceneFileName
	 * @param destinationDirectory
	 */
	public SceneBuilder(String sceneFileName, String destinationDirectory) {
		File sceneFile = new File(SCENE_FILE_PATH + sceneFileName);
		_scene = new Scene("Scene");
		loadSceneFromFile(sceneFile);

		_sceneDescriptor = new SceneDescriptor();

		try {
			_sceneDescriptor.fromXML(sceneXMLDesc);
		} catch (ParseException e) {
			System.out.println("Syntactical error in scene description:");
			e.printStackTrace();
		}

		// System.out.print(sceneXMLDesc);

		// Creating an AmbientLight object
		String[] aLColors = _sceneDescriptor.getAmbientLightAttributes().get("color").split("\\s+");
		primitives.Color color = new primitives.Color((Double.valueOf(aLColors[0])), (Double.valueOf(aLColors[1])),
				(Double.valueOf(aLColors[2])));

		_scene.setAmbientLight(new AmbientLight(color, 0.7));

		// Creating a camera object
		Camera camera = new Camera(_sceneDescriptor.getCameraAttributes());

		// creating a scene
		String[] backgroundColor = _sceneDescriptor.getSceneAttributes().get("background-color").split("\\s+");

		primitives.Color background = new primitives.Color((Double.valueOf(backgroundColor[0])),
				(Double.valueOf(backgroundColor[1])), (Double.valueOf(backgroundColor[2])));

		double screenDist = Double.valueOf(_sceneDescriptor.getSceneAttributes().get("screen-dist"));
		_scene.setBackground(background);
		_scene.setCamera(camera, screenDist);

		// creating an imageWriter
		int screenWidth = Integer.valueOf(_sceneDescriptor.getSceneAttributes().get("screen-width"));
		int screenHeight = Integer.valueOf(_sceneDescriptor.getSceneAttributes().get("screen-width"));
		_imageWriter = new ImageWriter(destinationDirectory + "scene", screenWidth, screenHeight, screenWidth,
				screenHeight);

		// creating and adding spheres
		for (Map<String, String> sphereAttributes : _sceneDescriptor.getSpheres()) {
			Sphere sphere = new Sphere(sphereAttributes);
			_scene.addGeometry(sphere);
		}

		// creating and adding triangles
		for (Map<String, String> triangleAttributes : _sceneDescriptor.getTriangles()) {
			Triangle triangle = new Triangle(triangleAttributes);
			_scene.addGeometry(triangle);
		}
	}

	/**
	 * ctor of SceneBuilder that get sceneFileName only
	 * @param sceneFileName
	 */
	public SceneBuilder(String sceneFileName) {

		this(sceneFileName, "");
	}

	/**
	 * return _scene
	 * @return _scene
	 */
	public Scene getScene() {
		return _scene;
	}

	/**
	 * return _imageWriter
	 * @return _imageWriter
	 */
	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * get file and check if the file load
	 * @param file
	 * @return  if file load
	 */
	private boolean loadSceneFromFile(File file) {
		if (file == null) return false;
		try {
			byte[] buffer = new byte[(int) file.length()];
			FileInputStream fin = new FileInputStream(file);
			fin.read(buffer);
			sceneXMLDesc = (new String(buffer));
			fin.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}