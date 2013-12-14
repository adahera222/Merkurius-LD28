package merkurius.ld28;

import java.util.HashMap;
import java.util.Map;

import merkurius.ld28.model.PlayerBody;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.model.Action;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class EntityFactoryLD28 extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
        visuals.put( "wall", new BoxVisual(20,20, Color.BLUE));
        //actions.put( "bullet_action", new BulletAction() );
	}

	
	public static Entity newActor(World world, int mapId, float x, float y) {
	    Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, 0) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
	    e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
	    world.getManager(GroupManager.class).add(e,"actor");
	    return e;
	}
	
	public static Entity newPlayer(World world, int mapId, float x, float y) {
		Entity e = newActor(world, mapId, x, y);
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new VisualComponent("wall") );
		e.addComponent( new Player() );
		return e;
	}
	
	public static Entity newWall(World world, int mapId, float x, float y, int width, int height){
	    Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, -1) );
	    e.addComponent( new VisualComponent("wall") );
	    e.addComponent( new PhysicsBodyComponent(new BoxBody(width, height)) );
	    world.getManager(GroupManager.class).add(e,"solid");
	    return e;
	}
	
	
	
}
