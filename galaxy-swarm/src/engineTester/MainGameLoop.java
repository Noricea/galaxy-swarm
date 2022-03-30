package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;

public class MainGameLoop {

	public static void main(String[] args) {

		final int objectCount = 4;
		int angle = 0;

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		RawModel model = OBJLoader.loadObjModel("star2", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));

		// Entity entity = new Entity(staticModel, new Vector3f(0, -1.5f, -3), 0, 0, 0,
		// 1);

		Entity[] entityList = new Entity[objectCount];
		for (int i = 0; i < objectCount; i++) {
			entityList[i] = new Entity(staticModel, new Vector3f(3f * i - 1.5f, 0, -100), 0, 0, 0, 1);
		}

		Camera camera = new Camera();

		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			for (int i = 0; i < objectCount; i++) {
				// entityList[i].increaseRotation(0, 0.2f, 0);
				entityList[i].increasePosition((float) Math.cos(Math.toRadians(angle)) / 5f,
						(float) Math.sin(Math.toRadians(angle)) / 5f, 0);
				renderer.render(entityList[i], shader);
			}
			angle++;
			angle = angle % 360;
			// renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
