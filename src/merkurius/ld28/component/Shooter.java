package merkurius.ld28.component;

import merkurius.ld28.CONST.WEAPON;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public class Shooter extends Component implements Syncable {

    public int timer 		= 0;
    public WEAPON weapon 	= WEAPON.NAILGUN;
	private boolean firing;
    
  

    public Shooter(WEAPON weapon) {
    	this.weapon = weapon;
    }
    //public void setAim(float direction) { aim = direction; }
    public boolean canShoot() {
        return timer <= 0;
    }

    public void decrementTimer(int duration){
        timer = timer - duration;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean isShooting() {
        return firing;
    }

    public void setShooting(boolean wantToShoot) {
        this.firing = wantToShoot;
    }
    
    public void setWeapon(WEAPON weapon) { this.weapon = weapon; }
    public WEAPON getWeapon() { return this.weapon; }

	@Override
	public void sync(EntityUpdate update) {
		this.timer		= update.getNextInteger();
		this.weapon		= WEAPON.valueOf(update.getNext());
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(timer).append(" ").append(weapon);
	}
}
