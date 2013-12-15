package merkurius.ld28.component;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public class Input extends Component implements Syncable {
	public int input = 0;
	public float rotation = 0;

	@Override
	public void sync(EntityUpdate update) {
		this.input 		= update.getNextInteger();
		this.rotation 	= update.getNextFloat();
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(input).append(" ").append(this.rotation);
	}
}
