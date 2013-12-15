package merkurius.ld28.component;

import merkurius.ld28.CONST.WEAPON;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public class Bullet extends Component implements Syncable {

	public WEAPON weapon;
	
	public Bullet(WEAPON weapon) {
		this.weapon = weapon;
	}
	
	@Override
	public void sync(EntityUpdate update) {
		this.weapon		= WEAPON.valueOf(update.getNext());
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(weapon);
	}
}
