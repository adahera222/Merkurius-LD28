package merkurius.ld28.component;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public class Actor extends Component implements Syncable {
	public int playerId = 0;

	public Actor(int playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public void sync(EntityUpdate update) {
		this.playerId 		= update.getNextInteger();
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(playerId);
	}
}