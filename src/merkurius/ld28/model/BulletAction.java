package merkurius.ld28.model;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Bullet;
import merkurius.ld28.component.Health;
import merkurius.ld28.system.DamageSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.model.Action;

public class BulletAction implements Action {

	protected World world;
	private ComponentMapper<Parent> parentMapper;
	private ComponentMapper<Health> healthMapper;
	private ComponentMapper<PhysicsBodyComponent> bodyMapper;
	private ComponentMapper<Bullet> bulletMapper;
	private DamageSystem damageSystem;
	
	public void initialize(World world) {
		parentMapper 	= ComponentMapper.getFor(Parent.class, world);
		healthMapper 	= ComponentMapper.getFor(Health.class, world);
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
		bulletMapper 	= ComponentMapper.getFor(Bullet.class, world);
		damageSystem	= Systems.get(DamageSystem.class, world);
		
		this.world 	= world;
	}

	@Override
	public void beginContact(Entity e, Entity other, Contact contact) {
	}
	
	
	@Override
	public void process(Entity e) {	
	}
	
	@Override
	public void endContact(Entity e, Entity other, Contact contact) {
	}

	@Override
	public void preSolve(Entity e, Entity other, Contact contact) {
		if( healthMapper.has(other) ) {
			damageSystem.dealDamage( world.getEntity( parentMapper.get(e).getParentId() ), other, bulletMapper.get(e).weapon );
			e.deleteFromWorld();
		} else if( bulletMapper.get(e).weapon == WEAPON.SYRINGE ){
			bodyMapper.get(e).getBody().setLinearVelocity(0, 0);
		} else {
			e.deleteFromWorld();
		}
	}

	@Override
	public void postSolve(Entity e, Entity other, Contact contact) {
	}

}
