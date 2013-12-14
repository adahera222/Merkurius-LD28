package merkurius.ld28.system;

import java.net.DatagramPacket;
import java.net.InetAddress;

import merkurius.ld28.LD28GameClient;

import com.artemis.Entity;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class LD28SyncSystem extends DefaultSyncSystem {

	public LD28SyncSystem(float f, int i, boolean b) {
		super(f,i,b);
	}

	public LD28SyncSystem() {
		super();
	}

	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		return new LD28GameClient(inetAddress, port);
	}

	@Override
	protected GameClient newClient(DatagramPacket packet, int port) {
		int playerId = newPlayerId();
		return new LD28GameClient(packet.getAddress(), port, packet.getPort(), playerId, null);
	}

	@Override
	protected void connected(int clientId) {
	}

	@Override
	protected Entity newEntity(EntityUpdate entityUpdate, int id) {
		if( entityUpdate.getType().equalsIgnoreCase("player") ) {
			EntityFactoryExamples.newPlayer(world, 1, 0, 0).addComponent( new Synchronize("player", false, id) ).addToWorld();
		}
		return null;
	}

}
