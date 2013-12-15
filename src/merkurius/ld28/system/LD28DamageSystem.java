package merkurius.ld28.system;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Health;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.PhysicsSystem;

public class LD28DamageSystem extends EntityProcessingSystem implements DamageSystem {

    protected ComponentMapper<Health>		healthMapper;
	protected PhysicsSystem physicsSystem;

	@SuppressWarnings("unchecked")
	public LD28DamageSystem() {
        super( Aspect.getAspectForAll(Health.class) );
    }

    @Override
    public void initialize(){
        healthMapper   = ComponentMapper.getFor(Health.class, world);
        physicsSystem	= Systems.get(PhysicsSystem.class, world);
    }


	@Override
	public void dealDamage(Entity shooter, Entity hit, WEAPON weapon) {
		if( healthMapper.has(hit) ) {
			Gdx.app.log("Damage dealt", "" +weapon.damage);
			healthMapper.get(hit).damage(weapon.damage);
		}
	}

	@Override
	protected void process(Entity e) {
		if( healthMapper.get(e).health == 0 ) {
			e.deleteFromWorld();
		}
		
	}
}
