package merkurius.ld28.system;

import merkurius.ld28.CONST.WEAPON;

import com.artemis.Entity;

public interface ShootingSystem {
	public void shoot(Entity e, WEAPON weapon);
}
