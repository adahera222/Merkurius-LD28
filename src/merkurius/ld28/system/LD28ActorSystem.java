package merkurius.ld28.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Actor;
import merkurius.ld28.component.Health;
import merkurius.ld28.component.Shooter;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;


public class LD28ActorSystem extends EntityProcessingSystem implements ActorSystem {

    protected ComponentMapper<Actor> 		actorMapper;
    protected ComponentMapper<Synchronize> 	syncMapper;
    protected ComponentMapper<Health>		healthMapper;
    protected ComponentMapper<Transform>	transformMapper;
    protected ComponentMapper<Shooter>		shootMapper;

    private int playerId = -1;
    private boolean playerFound = false;
    private List<Vector2> spawnPoints;
	private Random rand;

    @SuppressWarnings("unchecked")
    public LD28ActorSystem() {
        super( Aspect.getAspectForAll(Actor.class, Health.class) );
    }
    
    public LD28ActorSystem(int playerId) {
    	this();
    	this.playerId = playerId;
    }

    @Override
    public void initialize(){
    	rand = new Random();
    	spawnPoints 	= new ArrayList<Vector2>();
    	spawnPoints.add(new Vector2(-100,-100));
    	spawnPoints.add(new Vector2(300,350));
    	spawnPoints.add(new Vector2(-200,250));
    	spawnPoints.add(new Vector2(400,-150));
    	spawnPoints.add(new Vector2(700,-150));
    	spawnPoints.add(new Vector2(700,250));
    	syncMapper   	= ComponentMapper.getFor(Synchronize.class, world);
        actorMapper   	= ComponentMapper.getFor(Actor.class, world);
        healthMapper	= ComponentMapper.getFor(Health.class, world);
        transformMapper	= ComponentMapper.getFor(Transform.class, world);
        shootMapper		= ComponentMapper.getFor(Shooter.class, world);
    }

    
    @Override
    protected void process(Entity e) { 
    	if( !playerFound && actorMapper.get(e).playerId == this.playerId ) {
    		
    		world.getManager(TagManager.class).register("player", e);
    		e.addComponent( new Player(playerId) );
    		e.changedInWorld();
    		syncMapper.get(e).setActive(true);
    		
    		playerFound = true;
    	}
    	
    	if( healthMapper.get(e).health == 0 && playerId == 0) {
    		respawn(e);
    	}
    }
    
    
    private void respawn(Entity e) {
    	healthMapper.get(e).setHealth(100);
    	transformMapper.get(e).setPosition( spawnPoints.get(rand.nextInt(spawnPoints.size())) );
    	switch(rand.nextInt(2)) {
    	case 0:
    		shootMapper.get(e).setWeapon(WEAPON.SYRINGE);
    		break;
    	case 1:
    		shootMapper.get(e).setWeapon(WEAPON.NAILGUN);
    		break;
    	}
	}

	public void setPlayerId(int playerId) {
    	this.playerId = playerId;
    }
    
    
    @Override
	public void dealDamage(Entity shooter, Entity hit, WEAPON weapon) {
		if( healthMapper.has(hit) ) {
			healthMapper.get(hit).damage(weapon.damage);
			
			if( healthMapper.get(hit).health == 0 && shootMapper.get(hit).weapon == WEAPON.BOLT ) {
				shootMapper.get(shooter).weapon = WEAPON.BOLT;
			}
		}
	}
}
