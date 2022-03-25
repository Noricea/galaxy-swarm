package Tester;

import org.lwjgl.opengl.Display;

import renderEngine.*;

public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		float[] verticles = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f
		};
		
		int[] indices = {
				0,1,3,
				3,1,2
		};
		
		RawModel model = loader.loadToVAO(verticles, indices);
		
		while(!Display.isCloseRequested()) {
			//Game Logic
			renderer.prepare();
			renderer.render(model);
			
			DisplayManager.updateDisplay();
		}
		
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}

}
