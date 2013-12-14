package merkurius.ld28.system;

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
	private Raycast raycast;

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
    }

    @Override
    protected void process(Entity e) {
        shooterMapper.get(e).decrementTimer((int) (world.getDelta()*1000));
        
        if (shooterMapper.get(e).isShooting() && shooterMapper.get(e).canShoot()) {
        	
        	if( shooterMapper.get(e).weapon == WEAPON.NAILGUN ) {
        		Vector2 vector = new Vector2(0,1000);
        		vector.setAngle(shooterMapper.get(e).getAim());
        		raycast = new Raycast();
        		physicsSystem.raycast(1, this, transformMapper.get(e).getPosition2(), transformMapper.get(e).getPosition2().cpy().add(vector));
        		if( raycast.lastEntity != null ) {
        			Entity bullet = EntityFactoryLD28.newBullet( world, 1, raycast.lastHit.x, raycast.lastHit.y, 1000, 0);
        	        bullet.addToWorld();
        		}
        		shooterMapper.get(e).setTimer(WEAPON.NAILGUN.reload);
                shooterMapper.get(e).setShooting(false);
        	} else if( shooterMapper.get(e).weapon == WEAPON.SYRINGE )  {
        		Vector2 vector = new Vector2(0,20);
        		vector.setAngle(shooterMapper.get(e).getAim());
        		Vector2 bulletPosition = transformMapper.get(e).getPosition2().cpy().add(vector);
                Entity bullet = EntityFactoryLD28.newBullet( world, 1, bulletPosition.x, bulletPosition.y, 1000, e.getId() );
                bullet.addToWorld();
                vector.set(0, WEAPON.SYRINGE.speed).setAngle(shooterMapper.get(e).getAim());
                velocityMapper.get(bullet).setSpeed(vector);
                shooterMapper.get(e).setTimer(WEAPON.SYRINGE.reload);
                shooterMapper.get(e).setShooting(false);
        	}
            
        }
    }

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		if( fixture.isSensor() ) {
			return -1;
		} else if( raycast.lastFraction > fraction) {
        	raycast.lastEntity = (Entity) fixture.getBody().getUserData();
        	raycast.lastFraction = fraction;
        	raycast.lastHit = point.cpy();
        }
		return 1;
	}
	
	private class Raycast {
		public float lastFraction = 1;
		public Entity lastEntity = null;
		public Vector2 lastHit = null;
	}
}
