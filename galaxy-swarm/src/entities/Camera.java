package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,0,100);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float zoom = 5;
	
	public Camera(){}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			position.z-=zoom*2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			position.z+=zoom*2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=zoom;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=zoom;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.y-=zoom;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.y+=zoom;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	

}
