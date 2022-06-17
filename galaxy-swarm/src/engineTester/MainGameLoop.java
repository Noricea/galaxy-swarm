package engineTester;

import models.RawModel;
import models.TexturedModel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
import entities.Camera;
import entities.Entity;
import entities.Star;

public class MainGameLoop {

	public static void main(String[] args) {

		System.out.print(System.getProperty("os.name") + "\n");

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		RawModel model = OBJLoader.loadObjModel("NewRedSun2" + "", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("blueSun")));
		Camera camera = new Camera();

		Entity solentity = new Entity(staticModel);

		List<Star> galaxy = new ArrayList<Star>();

		/*
		 * //Distance 100 = 1AU Planet Radius 1 = 10.000km = 0,000067AU -> roughly 200th
		 * the distance Masse 1 = 1x10^24kg
		 */

		float e30 = 1000000;
		int count = 500;

		for (int i = 0; i < count; i++) {
			galaxy.add(new Star(solentity,
					new Vector3f((float) (Math.random() * 10000 - 5000), (float) (Math.random() * 10000 - 5000), 0),
					new Vector3f(0, 300, 0), new Vector3f(0, 0, 0), (float) (Math.random() * 6 * e30), 10));
		}

		// count their total mass and average position
		int totalMass = 0;
		Vector3f totalCenter = new Vector3f(0, 0, 0);
		for (int i = 0; i < galaxy.size(); i++) {
			totalMass += galaxy.get(i).getMass();
			totalCenter.x += galaxy.get(i).getPosition().x;
			totalCenter.y += galaxy.get(i).getPosition().y;
			totalCenter.z += galaxy.get(i).getPosition().z;
		}
		totalCenter.x /= count;
		totalCenter.y /= count;
		totalCenter.z /= count;

		// replace 100 with formula for stable orbit around totalMass center
		for (int i = 0; i < galaxy.size(); i++) {
			
			float radius = (float) Math.sqrt(Math.pow((galaxy.get(i).getPosition().x - totalCenter.x), 2)
					+ Math.pow((galaxy.get(i).getPosition().y - totalCenter.y), 2));			
			if (i != 0) {
				galaxy.get(i).setMovementVector(new Vector3f(
						(float) (Math.sqrt(6.6743015f * Math.pow(10, -7f) * totalMass / radius) * 
						(galaxy.get(i).getPosition().y < 0 ? -5.6f : 5.6f)), 
						
						(float) (Math.sqrt(6.6743015f * Math.pow(10, -7f) * totalMass / radius) * 
								(galaxy.get(i).getPosition().x < 0 ? 5.6f : -5.6f)), 0));
			}
		}
		
		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);

			for (int i = 0; i < galaxy.size(); i++) {
				for (int j = 0; j < galaxy.size(); j++) {
					if (i != j) {
						galaxy.get(i).setMovementVector(Maths.calculateVelocity(galaxy.get(i), galaxy.get(j)));
					}
				}
			}

			for (int i = 0; i < galaxy.size(); i++) {
				galaxy.get(i).increasePosition(new Vector3f(galaxy.get(i).getMovementVector().x,
						galaxy.get(i).getMovementVector().y, galaxy.get(i).getMovementVector().z));
			}

			for (int i = 0; i < galaxy.size(); i++) {
				renderer.render(galaxy.get(i), shader);
			}

			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}
}
