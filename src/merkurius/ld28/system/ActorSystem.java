package merkurius.ld28.system;

import merkurius.ld28.CONST.WEAPON;

import com.artemis.Entity;

public interface ActorSystem {
	public void setPlayerId(int playerId);
	public void dealDamage(Entity shooter, Entity hit, WEAPON weapon);
}
