package merkurius.ld28.system;

import java.net.DatagramPacket;
import java.net.InetAddress;

import merkurius.ld28.EntityFactoryLD28;
import merkurius.ld28.LD28GameClient;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class LD28SyncSystem extends DefaultSyncSystem {

	private ActorSystem actorSystem;

	public LD28SyncSystem(float f, int i, boolean b) {
		super(f,i,b);
	}

	public LD28SyncSystem() {
		super();
	}
	protected void initialize() {
		super.initialize();
		actorSystem	= Systems.get(ActorSystem.class, world);
	}
	
	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		return new LD28GameClient(inetAddress, port);
	}

	@Override
	protected GameClient newClient(DatagramPacket packet, int port) {
		int playerId = newPlayerId();
		Entity e = EntityFactoryLD28.newActor(world, 1, 0, 0, playerId, 0, true);
		e.addToWorld();
		return new LD28GameClient(packet.getAddress(), port, packet.getPort(), playerId, e);
	}

	@Override
	protected void connected(int clientId) {
		actorSystem.setPlayerId(clientId);
	}

	@Override
	protected Entity newEntity(EntityUpdate entityUpdate, int id) {
		if( !isServer && entityUpdate.getType().equalsIgnoreCase("player") ) {
			EntityFactoryLD28.newActor(world, 1, 0, 0, Integer.valueOf(entityUpdate.getData()[9]), id, false).addToWorld();
		}
		if( !isServer && entityUpdate.getType().equalsIgnoreCase("syringe") ) {
			EntityFactoryLD28.newClientSyringe(world, id).addToWorld();
		}
		
		return null;
	}
	
	protected void removed(Entity e) {
		if( isServer ) {
			send("removed " + e.getId());
		}
		
	};

}
