package shaders;

import org.lwjgl.util.vector.Matrix4f;

import toolbox.Maths;

import entities.Camera;

public class StaticShader extends ShaderProgram {

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;

	public StaticShader() {
		super((
				System.getProperty("os.name").toLowerCase().contains("nix")
					|| System.getProperty("os.name").toLowerCase().contains("nux") 
						? "src/shaders/vertexShader.txt"
							: "galaxy-swarm/src/shaders/vertexShader.txt"),
				(System.getProperty("os.name").toLowerCase().contains("nix")
					|| System.getProperty("os.name").toLowerCase().contains("nux")
						? "src/shaders/fragmentShader.txt"
							: "galaxy-swarm/src/shaders/fragmentShader.txt"));
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");

	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
