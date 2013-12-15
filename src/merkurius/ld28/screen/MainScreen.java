package merkurius.ld28.screen;

import java.net.UnknownHostException;

import merkurius.ld28.CONST;
import merkurius.ld28.EntityFactoryLD28;
import merkurius.ld28.system.LD28InputSystem;
import merkurius.ld28.system.LD28MapSystem;
import merkurius.ld28.system.LD28PlayerSystem;
import merkurius.ld28.system.LD28ShootingSystem;
import merkurius.ld28.system.ScaledBox2DSystem;
import fr.kohen.alexandre.examples.network.NetworkExampleSyncSystem;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;

public class MainScreen extends GameScreen {


	protected boolean isServer;
	private SyncSystem syncSystem;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	@Override
	protected void setSystems() {
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryLD28.visuals) );
		world.setSystem( new ScaledBox2DSystem(CONST.SCALE) );	
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultMouseSystem() );
		world.setSystem( new LD28MapSystem() );
		world.setSystem( new LD28InputSystem() );
		world.setSystem( new LD28PlayerSystem() );
		world.setSystem( new LD28ShootingSystem() );
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultDebugSystem() );
		//world.setSystem( new DefaultControlSystem(50) );
		
		/*if( isServer ) {
			syncSystem = world.setSystem( new NetworkExampleSyncSystem(0.1f, 4445, true) );
		} else {
			syncSystem = world.setSystem( new NetworkExampleSyncSystem() );	
		}*/
	}
	
	@Override
	protected void initialize() {
		EntityFactoryLD28.newWall(world, 1, 200, 200, 20, 20).addToWorld();
		
		EntityFactoryLD28.newActor(world, 1, 100, 0).addToWorld();
		
		EntityFactoryLD28.newMap(world, 1, "data/map1.tmx", -500, -400).addToWorld();
		EntityFactoryLD28.newCamera(world, 1, 0, 0, 0, 0, 0, 800, 600, 0, "cameraFollowPlayer").addToWorld();
		//EntityFactoryLD28.newCamera(world, 1, 0, 0, 0, -200, -150, 4, 3, 0, "cameraFollowPlayer").addToWorld();
		

		if ( isServer ) {
			EntityFactoryLD28.newPlayer(world, 1, 0, 0).addComponent( new Synchronize("player", true) ).addToWorld();
		} else {
			try { ((NetworkExampleSyncSystem) syncSystem).connect("127.0.0.1", 4445); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}

}
