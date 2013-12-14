package merkurius.ld28.screen;

import java.util.Random;

import merkurius.ld28.EntityFactoryLD28;
import merkurius.ld28.system.LD28MapSystem;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MenuScreen extends GameScreen {


	protected boolean isServer;

	public MenuScreen(boolean server) {
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
		world.setSystem( new LD28MapSystem() );
	}
	
	@Override
	protected void initialize() {
		Random rand = new Random();
		EntityFactoryLD28.newWall(world, 1, 0, 0, 10, 10).addToWorld();
		EntityFactoryLD28.newWall(world, 1, rand.nextInt(50), rand.nextInt(50), 23, 23).addToWorld();
		EntityFactoryLD28.newWall(world, 1, rand.nextInt(50), rand.nextInt(50), 23, 23).addToWorld();
		
		EntityFactoryLD28.newPlayer(world, 1, 50, 50).addToWorld();
		
		EntityFactoryLD28.newMap(world, 1, "data/map1.tmx", -500, -400).addToWorld();  
		EntityFactoryLD28.newCamera(world, 1, 0, 0, 0, 0, 0, 800, 600, 0, "cameraFollowPlayer").addToWorld();
	}

}
