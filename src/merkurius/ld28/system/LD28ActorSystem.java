package merkurius.ld28.system;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Actor;
import merkurius.ld28.component.Health;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Synchronize;


public class LD28ActorSystem extends EntityProcessingSystem implements ActorSystem {

    protected ComponentMapper<Actor> 		actorMapper;
    protected ComponentMapper<Synchronize> 	syncMapper;
    protected ComponentMapper<Health>		healthMapper;

    private int playerId = -1;
    private boolean playerFound = false;

    @SuppressWarnings("unchecked")
    public LD28ActorSystem() {
        super( Aspect.getAspectForAll(Actor.class) );
    }
    
    public LD28ActorSystem(int playerId) {
    	this();
    	this.playerId = playerId;
    }

    @Override
    public void initialize(){
    	syncMapper   	= ComponentMapper.getFor(Synchronize.class, world);
        actorMapper   	= ComponentMapper.getFor(Actor.class, world);
        healthMapper	= ComponentMapper.getFor(Health.class, world);
    }

    @Override
    protected void process(Entity e) { 
    	if( !playerFound && actorMapper.get(e).playerId == this.playerId ) {
    		
    		world.getManager(TagManager.class).register("player", e);
    		e.addComponent( new Player(playerId) );
    		e.changedInWorld();
    		syncMapper.get(e).setActive(true);
    		
    		playerFound= true;
    	}
    }
    
    public void setPlayerId(int playerId) {
    	this.playerId = playerId;
    }
    
    
    @Override
	public void dealDamage(Entity shooter, Entity hit, WEAPON weapon) {
		if( healthMapper.has(hit) ) {
			Gdx.app.log("Damage dealt", "" +weapon.damage);
			healthMapper.get(hit).damage(weapon.damage);
		}
	}
}
