package merkurius.ld28;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MainScreen extends GameScreen {


	protected boolean isServer;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryLD28.visuals) );
		world.setSystem( new DefaultBox2DSystem() );	
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );
	}
	
	@Override
	protected void initialize() {
		EntityFactoryLD28.newWall(world, 1, 0, 0, 10, 10);
		EntityFactoryLD28.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraWorld1").addToWorld();
	}


}
