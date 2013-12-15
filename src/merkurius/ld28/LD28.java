package merkurius.ld28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class LD28 {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "One Ring To Rule All The Junkies";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;
		cfg.resizable = false;
		
		new LwjglApplication(new LD28Controller(), cfg);		
	}
}
