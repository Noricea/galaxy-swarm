package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import entities.Camera;
import entities.Star;

public class Maths {

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
			float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
				viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static Vector3f calculateVelocity(Star actor, Star attractor){
		float r = (float) Math.pow(
				Math.pow(actor.getPosition().x - attractor.getPosition().x, 2)	+ 
				Math.pow(actor.getPosition().y - attractor.getPosition().y, 2)	+ 
				Math.pow(actor.getPosition().z - attractor.getPosition().z, 2), 0.5d);
		float R = actor.getRadius() + attractor.getRadius();
		float M = actor.getMass() * attractor.getMass();
		float g = gravity(r, R, M);
		float Fz = centrifugalForce(r, g);
		
		float p = attractor.getMass()/(actor.getMass()+attractor.getMass());

		if(r != 0) {
//			actor.setVelocity(new Vector3f(
//					actor.getVelocity().x - ((actor.getPosition().x - attractor.getPosition().x) * g / r)*(attractor.getMass()/(actor.getMass()+attractor.getMass())),
//					actor.getVelocity().y - ((actor.getPosition().y - attractor.getPosition().y) * g / r)*(attractor.getMass()/(actor.getMass()+attractor.getMass())),
//					actor.getVelocity().z - ((actor.getPosition().z - attractor.getPosition().z) * g / r)*(attractor.getMass()/(actor.getMass()+attractor.getMass()))));
			
			actor.setVelocity(new Vector3f(
			actor.getVelocity().x - (((actor.getPosition().x - attractor.getPosition().x) * g / r)) * p,
			actor.getVelocity().y - (((actor.getPosition().y - attractor.getPosition().y) * g / r)) * p,
			actor.getVelocity().z - (((actor.getPosition().z - attractor.getPosition().z) * g / r)) * p));
		}
		return actor.getVelocity();
	}
	
	private static float gravity(float r, float R, float M) {
		final float G = 6.6743015f;

		if (r < R) {
			return 0;
		} else {
			float g = (float) ((G * Math.pow(10, -11) * M) / Math.pow(r, 2));
			return g;
		}
	}
	
	private static float centrifugalForce(float R, float g) {
		float v;
		if (R < 0) {
			R *= -1;
			v = (float) Math.pow(R*g, 0.5);
			v *= -1;
		}
		else {
			v = (float) Math.pow(R*g, 0.5);
		}
		
		return v;
	}
}
