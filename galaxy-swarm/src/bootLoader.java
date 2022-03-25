import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class bootLoader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Display.create();
			Thread.sleep(5000);
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
