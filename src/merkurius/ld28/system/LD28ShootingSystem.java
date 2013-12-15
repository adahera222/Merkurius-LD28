package merkurius.ld28.system;

import merkurius.ld28.CONST;
import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.EntityFactoryLD28;
import merkurius.ld28.component.Input;
import merkurius.ld28.component.Shooter;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.interfaces.PhysicsSystem;

public class LD28ShootingSystem extends EntityProcessingSystem implements RayCastCallback, ShootingSystem {

    protected ComponentMapper<Shooter>   shooterMapper;
    protected ComponentMapper<Transform> transformMapper;
    protected ComponentMapper<Velocity>  velocityMapper;
    protected ComponentMapper<Input>     inputMapper;
	protected PhysicsSystem physicsSystem;
	private Entity lastEntity = null;
	private Vector2 lastHit = null;
	private ActorSystem actorSystem;

    @SuppressWarnings("unchecked")
	public LD28ShootingSystem() {
        super(Aspect.getAspectForAll(Shooter.class, Transform.class, Velocity.class));
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        inputMapper  	= ComponentMapper.getFor(Input.class, world);
        physicsSystem	= Systems.get(PhysicsSystem.class, world);
        actorSystem	= Systems.get(ActorSystem.class, world);
    }

    @Override
    protected void process(Entity e) {
        shooterMapper.get(e).decrementTimer((int) (world.getDelta()*1000));
        
        if (inputMapper.get(e).input > 15 && shooterMapper.get(e).canShoot()) {
        	shoot(e, shooterMapper.get(e).weapon);
        	cooldown(e, shooterMapper.get(e).weapon);
        }
    }

	

	public void shoot(Entity e, WEAPON weapon) {
		switch(weapon) {
		
		case NAILGUN:
			Vector2 endPoint = new Vector2(0, WEAPON.NAILGUN.range);
			endPoint.setAngle( inputMapper.get(e).rotation );
			endPoint.add( transformMapper.get(e).getPosition2() );
			lastEntity = null;
			lastHit = null;
    		physicsSystem.raycast( 1, this, transformMapper.get(e).getPosition2().cpy().div(CONST.SCALE), endPoint.div(CONST.SCALE) );
    		lastHit.mul(CONST.SCALE);
    		if( lastEntity != null ) {
    			if( inputMapper.has(lastEntity) ) {
    				actorSystem.dealDamage(e, lastEntity, weapon);
    			} else {
    				Entity bullet = EntityFactoryLD28.newNailgunImpact( world, 1, lastHit.x, lastHit.y, 2000, 0 );
    				bullet.addToWorld();
        	        transformMapper.get(bullet).rotation = inputMapper.get(e).rotation - 45;
    			}    	        
    		}
			break;
			
			
		case SYRINGE:
			Vector2 syringePosition = new Vector2(0,20);
			syringePosition.setAngle( inputMapper.get(e).rotation  );
			syringePosition.add( transformMapper.get(e).getPosition2() );
    		
            Entity syringe = EntityFactoryLD28.newServerSyringe( world, 1, syringePosition.x, syringePosition.y, weapon.range, e.getId(), weapon );
            syringe.addToWorld();
            transformMapper.get(syringe).rotation = inputMapper.get(e).rotation - 45;
            
            Vector2 syringeSpeed = new Vector2( 0, weapon.speed );
            syringeSpeed.setAngle( inputMapper.get(e).rotation );
            velocityMapper.get(syringe).setSpeed(syringeSpeed);
            break;
            
            
		case BOLT:
			Vector2 bulletPosition = new Vector2(0,20);
			bulletPosition.setAngle( inputMapper.get(e).rotation  );
    		bulletPosition.add( transformMapper.get(e).getPosition2() );
    		
            Entity bullet = EntityFactoryLD28.newServerBolt( world, 1, bulletPosition.x, bulletPosition.y, weapon.range, e.getId(), weapon );
            bullet.addToWorld();
            
            transformMapper.get(bullet).rotation = inputMapper.get(e).rotation;
            
            Vector2 bulletSpeed = new Vector2( 0, weapon.speed );
            bulletSpeed.setAngle( inputMapper.get(e).rotation );
            velocityMapper.get(bullet).setSpeed(bulletSpeed);
			break;
		case BAT:
		case MINE:
		case ACID:
		default:
			break;
		}
	}
	
	
	private void cooldown(Entity e, WEAPON weapon) {
		shooterMapper.get(e).setTimer(weapon.reload);
		shooterMapper.get(e).setShooting(false);
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		if( fixture.isSensor() ) {
			return -1;
		} else {
        	lastEntity = (Entity) fixture.getBody().getUserData();
        	lastHit = point.cpy();
        }
		return fraction;
	}
}
