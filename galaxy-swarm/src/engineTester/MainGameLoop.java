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

		RawModel model = OBJLoader.loadObjModel("star2", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));
		Camera camera = new Camera();

		Entity solentity = new Entity(staticModel);

		List<Star> galaxy = new ArrayList<Star>();	
		
		/*
		 * //Distance 100 = 1AU
		 * Planet Radius 1 = 10.000km = 0,000067AU -> roughly 200th the distance
		 * Masse 1 = 1x10^24kg
		 */
		
		float e23 = 0.1f;
		float e24 = 1;
		float e30 = 1000000;
		
		for (int i = 0; i < 100; i++) {
			galaxy.add(new Star(solentity, 
					new Vector3f((float) (Math.random() * 1000 - 500),(float) (Math.random() * 1000 - 500),0), 
					new Vector3f(0,0,0), 
					new Vector3f(0,0,0), 
					(float) (Math.random() * 4 * e30), 5));
		}
		int totalMass = 0;
		for (int i = 0; i < 100; i++) {
			totalMass += galaxy.get(i).getMass();
		}
		System.out.print(totalMass);
		
		//replace 100 with formula for stable orbit around totalMass center
		for (int i = 0; i < 100; i++) {
			galaxy.get(i).setMovementVector(new Vector3f(
					-galaxy.get(i).getPosition().y / 100,
					galaxy.get(i).getPosition().x / 100,
					0));
		}
		

		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			
			for(int i = 0; i < galaxy.size(); i++) {
				for(int j = 0; j < galaxy.size(); j++) {
					if (i != j) {
						galaxy.get(i).setMovementVector(Maths.calculateVelocity(galaxy.get(i), galaxy.get(j)));
					}
				}
			}
			
			for(int i = 0; i < galaxy.size(); i++) {
				galaxy.get(i).increasePosition(new Vector3f(
						galaxy.get(i).getMovementVector().x,
						galaxy.get(i).getMovementVector().y, 
						galaxy.get(i).getMovementVector().z));
			};
			
			float r = (float) Math.pow(
					Math.pow(galaxy.get(1).getMovementVector().x, 2)	+ 
					Math.pow(galaxy.get(1).getMovementVector().y, 2)	+ 
					Math.pow(galaxy.get(1).getMovementVector().z, 2), 0.5d);
			
			for(int i = 0; i < galaxy.size(); i++) {
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
