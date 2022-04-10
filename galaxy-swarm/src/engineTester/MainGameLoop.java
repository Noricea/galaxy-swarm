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
		
		//Sun
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				1.9885f*e30, 6.96f));
		
		//Mercury
		galaxy.add(new Star(solentity, 
				new Vector3f(-46.0012f, 0, 0),  
				new Vector3f(0, 0, 0), 
				new Vector3f(0, -0.005898f, 0),
				3.3011f*e23, 0.244f));
		
		//Venus
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				0, 1));
		
		//Earth
		galaxy.add(new Star(solentity, 
				new Vector3f(147.095f, 0, 0),  
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0.002978f, 0),
				5.97237f*e24, 0.64f));
		
		//Mars
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				0, 1));
		
		//Jupiter
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				0, 1));
		
		//Saturn
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				0, 1));
		
		//Uranus
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				0, 1));
		
		//Neptune
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0),  
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				0, 1));

		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			
			for(int i = 0; i < galaxy.size(); i++) {
				for(int j = 0; j < galaxy.size(); j++) {
					if (i != j) {
						galaxy.get(i).setVelocity(Maths.calculateVelocity(galaxy.get(i), galaxy.get(j)));
					}
				}
			}
			
			for(int i = 0; i < galaxy.size(); i++) {
				galaxy.get(i).setPosition(new Vector3f(
						galaxy.get(i).getPosition().x + galaxy.get(i).getVelocity().x,
						galaxy.get(i).getPosition().y + galaxy.get(i).getVelocity().y, 
						galaxy.get(i).getPosition().z + galaxy.get(i).getVelocity().z));
			};
			
			System.out.format("x = %12f  y = %12f  z = %12f\n", galaxy.get(1).getPosition().x,galaxy.get(1).getPosition().y,galaxy.get(1).getPosition().z);
			
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
