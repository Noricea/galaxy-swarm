package entities;

import org.lwjgl.util.vector.Vector3f;

public class Star {

	private int mass;
	private Vector3f velocity;
	private Entity object;
	
	public Star(int mass, Vector3f vel, Entity obj) {
		this.mass = mass;
		this.velocity.x = vel.x;
		this.velocity.x = vel.y;
		this.velocity.x = vel.z;
		this.object = obj;
	}
}
