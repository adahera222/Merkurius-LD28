package merkurius.ld28.system;

import merkurius.ld28.CONST;
import merkurius.ld28.component.Input;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Velocity;

/**
 * Extend this class to modify how the animations are updated
 * @author Alexandre
 *
 */
public class LD28InputSystem extends EntityProcessingSystem {
	protected ComponentMapper<Input>      			inputMapper;
	protected ComponentMapper<PhysicsBodyComponent>	bodyMapper;
	protected ComponentMapper<Velocity>				velocityMapper;
	
	@SuppressWarnings("unchecked")
	public LD28InputSystem() {
		super( Aspect.getAspectForAll(Input.class, Velocity.class) );
	}
	
	@Override
	public void initialize() {
		inputMapper = ComponentMapper.getFor(Input.class, world);
		bodyMapper 	= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
	}

	@Override
	protected void process(Entity e) {
		int input = inputMapper.get(e).input;
		Vector2 force = new Vector2();
		if( input >= 8 ) {
			input -= 8;
			force.add(0, -CONST.MOVEMENT);
		}
		
		if( input >= 4 ) {
			input -= 4;
			force.add(0, CONST.MOVEMENT);
		}
		
		if( input >= 2 ) {
			input -= 2;
			force.add(CONST.MOVEMENT, 0);
		}
		
		if( input >= 1 ) {
			input -= 1;
			force.add(-CONST.MOVEMENT, 0);
		}
		//bodyMapper.get(e).physicsBody.body.applyForceToCenter(force.nor().mul(2000));
		velocityMapper.get(e).addSpeed(force);
		
	}
	

	
}
