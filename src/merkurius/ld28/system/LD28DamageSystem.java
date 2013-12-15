package merkurius.ld28.system;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Health;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.interfaces.PhysicsSystem;

public class LD28DamageSystem extends VoidEntitySystem implements DamageSystem {

    protected ComponentMapper<Health>		healthMapper;
    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Velocity>     velocityMapper;
	protected PhysicsSystem physicsSystem;

	public LD28DamageSystem() {
        super();
    }

    @Override
    public void initialize(){
        healthMapper   = ComponentMapper.getFor(Health.class, world);
        
        
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        physicsSystem	= Systems.get(PhysicsSystem.class, world);
    }


	@Override
	public void dealDamage(Entity shooter, Entity hit, WEAPON weapon) {
		healthMapper.get(hit).damage(weapon.damage);	
	}

	@Override
	protected void processSystem() {
	}
}
