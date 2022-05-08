package toolbox;

public class Astrophysics {
	//Constants
	final static double G = 0.0000000000667430; //Gravitational Constant
	final static double c = 300000; //Speed of Light
	
	
	
	public static float velocityVector(float r) {
		return r;
	}
	
	public static void centripetalForce() {
		
	}
	
	public static float momentum(float mass, float velocity) {
		return mass * velocity;
	}
	
	//supposed to be F = delta-momentum / delta-time; time assumed to be 1 (interval);
	public static float netForceThroughMomentum(float momentum) {
		return momentum;
	}
	
	public static float netForceThroughAcceleration(float mass, float acceleration) {
		return mass * acceleration;
	}
	
	public static float tangentialSpeed(float mass, float netForce) {
		return mass / netForce;
	}
	
	private static float gravity(float r, float R, float M) {
		if (r < R) {
			return 0;
		} else {
			float g = (float) ((G * M) / Math.pow(r, 2));
			return g;
		}
	}
}
