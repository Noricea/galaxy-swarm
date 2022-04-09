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
		
		//Sun
		galaxy.add(new Star(solentity, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Mercury
		galaxy.add(new Star(solentity, 
				new Vector3f(1f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Venus
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Earth
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Mars
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Jupiter
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Saturn
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Uranus
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
		//Neptune
		galaxy.add(new Star(solentity, 
				new Vector3f(2f, 0f, 0f), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0),
				2, 1));
		
//		for (int i = 0; i < 1000; i++) {
//			float spawnX = (float) Math.random()*1000-500;
//			float spawnY = (float) Math.random()*1000-500;
//			float spawnZ = (float) Math.random()*1000-500;
//			
//			galaxy.add(new Star(solentity, 
//					new Vector3f(spawnX, spawnY, spawnZ), 
//					new Vector3f(0, 0, 0), 
//					new Vector3f((float) Math.random()-0.5f, (float) Math.random()-0.5f, (float) Math.random()-0.5f), 
//					2, 1));
//		}

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
