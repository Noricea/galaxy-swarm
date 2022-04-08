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

		final int objectCount = 200;
		int angle = 0;

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		RawModel model = OBJLoader.loadObjModel("star2", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));

		Entity center = new Entity(staticModel, new Vector3f(0, 0, 0), 0, 0, 0, 0.8f);
		Entity[] entityList = new Entity[objectCount];
		for (int i = 0; i < objectCount; i++) {
			entityList[i] = new Entity(staticModel, new Vector3f((float) (Math.random() + 2d), 0, 2), 0, 0, 0, 0.1f);
		}

		Camera camera = new Camera();

		Vector3f[] velocity = new Vector3f[objectCount];
		for (int i = 0; i < objectCount; i++) {
			velocity[i] = new Vector3f(0f, 0.06f, 0f);
		}

		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);

			for (int i = 0; i < objectCount; i++) {
				float r = (float) Math.pow(
						Math.pow(entityList[i].getPosition().x - 0, 2) + Math.pow(entityList[i].getPosition().y - 0, 2)
								+ Math.pow(entityList[i].getPosition().z - 0, 2),
						0.5d);
				float g = gravity(r);

				velocity[i].x = velocity[i].x - (entityList[i].getPosition().x * g / r);
				velocity[i].y = velocity[i].y - (entityList[i].getPosition().y * g / r);
				velocity[i].z = velocity[i].z - (entityList[i].getPosition().z * g / r);

				entityList[i].increasePosition(velocity[i].x, velocity[i].y, velocity[i].z);
				renderer.render(entityList[i], shader);
			}
			renderer.render(center, shader);

			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

	static float gravity(float r) {
		final float G = 0.00066743015f;
		float R = 1f;
		float M = 15f;

		if (r < R) {
			return 0;
		} else {
			float g = (G * M) / (r * r);
			return g;
		}
	}

}
