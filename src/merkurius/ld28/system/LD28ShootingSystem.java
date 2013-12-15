package merkurius.ld28.system;

import merkurius.ld28.CONST;
import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.EntityFactoryLD28;
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

public class LD28ShootingSystem extends EntityProcessingSystem implements RayCastCallback {

    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Velocity>     velocityMapper;
	protected PhysicsSystem physicsSystem;
	private Entity lastEntity = null;
	private Vector2 lastHit = null;
	private DamageSystem damageSystem;

    @SuppressWarnings("unchecked")
	public LD28ShootingSystem() {
        super(Aspect.getAspectForAll(Shooter.class, Transform.class, Velocity.class));
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        physicsSystem	= Systems.get(PhysicsSystem.class, world);
        damageSystem	= Systems.get(DamageSystem.class, world);
    }

    @Override
    protected void process(Entity e) {
        shooterMapper.get(e).decrementTimer((int) (world.getDelta()*1000));
        
        if (shooterMapper.get(e).isShooting() && shooterMapper.get(e).canShoot()) {
        	shoot(e, shooterMapper.get(e).weapon);
        	cooldown(e, shooterMapper.get(e).weapon);
        }
    }

	

	private void shoot(Entity e, WEAPON weapon) {
		switch(weapon) {
		
		case NAILGUN:
			Vector2 endPoint = new Vector2(0, WEAPON.NAILGUN.range);
			endPoint.setAngle( shooterMapper.get(e).getAim() );
			endPoint.add( transformMapper.get(e).getPosition2() );
			lastEntity = null;
			lastHit = null;
    		physicsSystem.raycast( 1, this, transformMapper.get(e).getPosition2().cpy().div(CONST.SCALE), endPoint.div(CONST.SCALE) );
    		lastHit.mul(CONST.SCALE);
    		if( lastEntity != null ) {
    			Entity bullet = EntityFactoryLD28.newBullet( world, 1, lastHit.x, lastHit.y, 1000, 0 );
    	        bullet.addToWorld();
    	        damageSystem.dealDamage(e, lastEntity, weapon);
    		}
			break;
			
		case SYRINGE:
			Vector2 bulletPosition = new Vector2(0,20);
			bulletPosition.setAngle( shooterMapper.get(e).getAim() );
    		bulletPosition.add( transformMapper.get(e).getPosition2() );
            Entity bullet = EntityFactoryLD28.newBullet( world, 1, bulletPosition.x, bulletPosition.y, WEAPON.SYRINGE.range, e.getId() );
            bullet.addToWorld();
            Vector2 bulletSpeed = new Vector2( 0, WEAPON.SYRINGE.speed );
            bulletSpeed.setAngle( shooterMapper.get(e).getAim() );
            velocityMapper.get(bullet).setSpeed(bulletSpeed);
			break;
			
		case RING:
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
