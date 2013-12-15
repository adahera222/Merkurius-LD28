package merkurius.ld28.component;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public class Health extends Component implements Syncable {
	public int health = 0;
	public int max = 100;

	public Health() { }
	
	public Health(int health, int max) {
		this.health = health;
		this.max = max;
	}

	public int getHealth() { return this.health; }
	public void setHealth(int health) { this.health = health; }
	public void damage(int damage) { this.health = Math.max(0, health-damage); }
	public void heal(int heal) { this.health = Math.min(max, health+heal); }
	
	@Override
	public void sync(EntityUpdate update) {
		this.health = update.getNextInteger();
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(health);
	}
}
