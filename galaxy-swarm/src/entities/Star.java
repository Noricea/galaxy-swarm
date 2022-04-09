package entities;

import org.lwjgl.util.vector.Vector3f;

public class Star {

	private Entity object;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f velocity;
	private float mass;
	private float radius;

	public Star(Entity object, Vector3f position, Vector3f rotation, Vector3f velocity, float mass, float radius) {
		this.object = object;
		this.position = position;
		this.rotation = rotation;
		this.velocity = velocity;
		this.mass = mass;
		this.radius = radius;
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public Entity getObject() {
		return object;
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public float getMass() {
		return mass;
	}

	public float getRadius() {
		return radius;
	}

	public void setObject(Entity object) {
		this.object = object;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
