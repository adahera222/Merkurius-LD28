package merkurius.ld28.screen;

import java.net.UnknownHostException;

import merkurius.ld28.CONST;
import merkurius.ld28.EntityFactoryLD28;
import merkurius.ld28.component.Actor;
import merkurius.ld28.component.Bullet;
import merkurius.ld28.component.Health;
import merkurius.ld28.component.Input;
import merkurius.ld28.component.Shooter;
import merkurius.ld28.system.LD28ActorSystem;
import merkurius.ld28.system.LD28AnimationSystem;
import merkurius.ld28.system.LD28InputSystem;
import merkurius.ld28.system.LD28MapSystem;
import merkurius.ld28.system.LD28PlayerSystem;
import merkurius.ld28.system.LD28ShootingSystem;
import merkurius.ld28.system.LD28SyncSystem;
import merkurius.ld28.system.ScaledBox2DSystem;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.components.Expires;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.DefaultActionSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem.SyncTypes;

public class MainScreen extends GameScreen {


	protected boolean isServer;
	private SyncSystem syncSystem;
	private String address;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	public void setAddress(String address) {
		this.isServer = false;
		this.address  = address;
	}
	
	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultRenderSystem(0,0,0,0) );
		world.setSystem( new DefaultDebugSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryLD28.visuals) );
		world.setSystem( new ScaledBox2DSystem(CONST.SCALE) );	
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultMouseSystem() );
		world.setSystem( new DefaultActionSystem(EntityFactoryLD28.actions) );
		world.setSystem( new LD28MapSystem() );
		world.setSystem( new LD28InputSystem() );
		world.setSystem( new LD28PlayerSystem() );
		world.setSystem( new LD28AnimationSystem() );
		
		
		
		if( isServer ) {
			world.setSystem( new LD28ShootingSystem() );
			syncSystem = world.setSystem( new LD28SyncSystem(0.05f, 4445, true) );
			world.setSystem( new LD28ActorSystem(0) );
			new merkurius.ld28.ServerlistThread().start();
		} else {
			world.setSystem( new LD28ActorSystem() );
			syncSystem = world.setSystem( new LD28SyncSystem() );	
		}
		syncSystem.addSyncedComponent(Transform.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Velocity.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Actor.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Input.class, SyncTypes.Both);
		syncSystem.addSyncedComponent(Parent.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Shooter.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Bullet.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Health.class, SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Expires.class, SyncTypes.ServerToClient);
	}
	
	@Override
	protected void initialize() {
		EntityFactoryLD28.newMap(world, 1, "data/carte_V4.tmx", -500, -1200).addToWorld();
		EntityFactoryLD28.newCamera(world, 1, 0, 0, 0, 0, 0, 800, 600, 0, "cameraFollowPlayer").addToWorld();
		

		if ( isServer ) {
			EntityFactoryLD28.newRingbearer(world, 1, 0, 0, 0, 0, true).addToWorld();
		} else {
			try {  syncSystem.connect("127.0.0.1", 4445); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}

}
