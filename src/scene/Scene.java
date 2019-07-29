package scene;

import java.util.ArrayList;
import java.util.List;
import elements.*;
import geometries.*;
import primitives.*;

/**
 * That class represent a graphic scene
 */
public class Scene {

	/**
	 * field
	 */
	private String _sceneName;
	private Color _background;
	private AmbientLight _ambientLight;
	private Geometries _geometries = new Geometries();
	private Camera _camera;
	private double _screenDistance;
	List<LightSource> _lights = new ArrayList<LightSource>();

	/**
	 * A constructor that get the name of the scene
	 * 
	 * @param name - The name of the scene
	 */
	public Scene(String name) {
		_sceneName = name;
		_background = Color.BLACK;
	}

	/**
	 * Getter of all the LightSource in our scene
	 * 
	 * @return - A list of all the light sources
	 */
	public List<LightSource> getLight() {
		return _lights;
	}

	/**
	 * getter of the color of the background of our scene
	 * 
	 * @return Background - The backgrount's color of our scene
	 */
	public Color getBackground() {
		return _background;
	}

	/**
	 * getter of AmbientLight
	 * 
	 * @return AmbientLight - The ambientLight of our scene
	 */
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	/**
	 * A getter of the name
	 * 
	 * @return the _sceneName - The name of the scene
	 */
	public String getSceneName() {
		return _sceneName;
	}

	/**
	 * Getter of the camera of our scene
	 * 
	 * @return the _camera - The camera
	 */
	public Camera getCamera() {
		return _camera;
	}

	/**
	 * Getter of the ScreenDistanc of our scene
	 * 
	 * @return the _screenDistance - The distance from the screen
	 */
	public double getScreenDistance() {
		return _screenDistance;
	}

	/**
	 * setter of the Background
	 * 
	 * @param _background - The color of the background
	 */
	public void setBackground(Color background) {
		_background = _background.setColor(background);
	}

	/**
	 * setter of a AmbientLight
	 * 
	 * @param _ambientLight - The AmbientLight of out scene
	 */
	public void setAmbientLight(AmbientLight ambientLight) {
		_ambientLight = ambientLight;
	}

	/**
	 * Setter of the camera
	 * 
	 * @param camera         - The camera
	 * @param screenDistance - The distance from the screen
	 */
	public void setCamera(Camera camera, double screenDistance) {
		_camera = camera;
		_screenDistance = screenDistance;
	}

	/**
	 * add Geometry to the scene
	 * 
	 * @param a - The geometries we want to add to the scene
	 */
	public void addGeometry(Geometry... a) {
		_geometries.add(a);
	}

	/**
	 * getter of Geometries
	 * 
	 * @return Iterator<Geometry>
	 */
	public Geometries getGeometries() {
		return _geometries;
	}

	/**
	 * setter of Geometries
	 * 
	 * @param geometries - The geometries we set
	 */
	public void setGeometries(Geometries geometries) {
		_geometries = geometries;
	}

	/**
	 * add a sources of light to the scene
	 * 
	 * @param light -The light
	 */
	public void addLight(LightSource light) {
		_lights.add(light);
	}

	/**
	 * setter of Light Sources
	 * 
	 * @param lightSources - The light Sources
	 */
	public void setLightSources(List<LightSource> lightSources) {
		_lights = lightSources;
	}

}