package merkurius.ld28.system;

import java.util.Map;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Shooter;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.systems.interfaces.AnimationSystem;

/**
 * Extend this class to modify how the animations are updated
 * @author Alexandre
 *
 */
public class LD28AnimationSystem extends EntityProcessingSystem implements AnimationSystem {
	protected ComponentMapper<VisualComponent> 	visualMapper;
	protected ComponentMapper<Shooter>      	shooterMapper;
	protected Map<Entity,Visual>				visuals;
	
	
	@SuppressWarnings("unchecked")
	public LD28AnimationSystem() {
		super( Aspect.getAspectForAll(VisualComponent.class, Shooter.class) );
	}
	
	@Override
	public void initialize() {
		visualMapper 	= ComponentMapper.getFor(VisualComponent.class, world);
		shooterMapper 	= ComponentMapper.getFor(Shooter.class, world);
	}

	@Override
	protected void process(Entity e) {
		if( shooterMapper.get(e).weapon == WEAPON.BOLT ) {
			visualMapper.get(e).currentAnimationName = "bolt";
		}
		if( shooterMapper.get(e).weapon == WEAPON.SYRINGE ) {
			visualMapper.get(e).currentAnimationName = "syringe";
		}
		if( shooterMapper.get(e).weapon == WEAPON.NAILGUN ) {
			visualMapper.get(e).currentAnimationName = "nailgun";
		}
	}
	

	
	
	
	
}
