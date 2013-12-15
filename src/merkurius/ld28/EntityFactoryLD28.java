package merkurius.ld28;

import java.util.HashMap;
import java.util.Map;

import merkurius.ld28.CONST.WEAPON;
import merkurius.ld28.component.Actor;
import merkurius.ld28.component.Bullet;
import merkurius.ld28.component.Health;
import merkurius.ld28.component.Input;
import merkurius.ld28.component.Server;
import merkurius.ld28.component.Shooter;
import merkurius.ld28.model.BulletAction;
import merkurius.ld28.model.BulletBody;
import merkurius.ld28.model.BulletClientAction;
import merkurius.ld28.model.ClientScreenAction;
import merkurius.ld28.model.PlayerBody;
import merkurius.ld28.model.PlayerVisual;
import merkurius.ld28.model.ServerScreenAction;
import merkurius.ld28.model.ServerlistScreenAction;
import merkurius.ld28.model.SyringeVisual;
import merkurius.ld28.model.WallBody;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.ActionsComponent;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Expires;
import fr.kohen.alexandre.framework.components.MapComponent;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.TextComponent;
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
        visuals.put( "player0", new PlayerVisual(0));
        visuals.put( "player1", new PlayerVisual(1));
        visuals.put( "syringe", new SyringeVisual());

        actions.put( "bullet_action", new BulletAction() );
        actions.put( "bullet_client_action", new BulletClientAction() );
        actions.put( "server_button", new ServerScreenAction() );
        actions.put( "serverlist_button", new ServerlistScreenAction() );
        actions.put( "client_button", new ClientScreenAction() );
	}

	
	public static Entity newActor(World world, int mapId, float x, float y, int playerId, int syncId, boolean isServer) {
	    Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, 0) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
		e.addComponent( new Input() );
		e.addComponent( new Shooter(WEAPON.BOLT) );
		e.addComponent( new Health() );
		e.addComponent( new Actor(playerId) );
		e.addComponent( new Synchronize("player", isServer, syncId) );
	    e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
	    e.addComponent( new VisualComponent("player" + playerId) );
	    world.getManager(GroupManager.class).add(e,"actor");
	    return e;
	}
	
	/*public static Entity newPlayer(World world, int mapId, float x, float y, int playerId) {
		Entity e = newActor(world, mapId, x, y, playerId);
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new Player() );
		return e;
	}*/
	
	public static Entity newWall(World world, int mapId, float x, float y, float width, float height){
	    Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, -1) );
	    e.addComponent( new PhysicsBodyComponent(new WallBody(width/CONST.SCALE, height/CONST.SCALE)) );
	    world.getManager(GroupManager.class).add(e,"solid");
	    return e;
	}
	
	public static Entity newMap(World world, int mapId, String mapName, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new MapComponent(mapId,mapName) );
		e.addComponent( new Transform(mapId, x, y, -1) );
		return e;
	}

	
	public static Entity newServerSyringe(World world, int mapId, float x, float y, int ttl, int parentid, WEAPON weapon) {
		Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, 0) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
		e.addComponent( new Expires(ttl) );
		e.addComponent( new Parent(parentid) );
		e.addComponent( new Bullet(weapon) );
	    e.addComponent( new PhysicsBodyComponent(new BulletBody(2/CONST.SCALE)) );
	    e.addComponent( new VisualComponent("syringe") );
	    e.addComponent( new ActionsComponent("bullet_action") );
	    world.getManager(GroupManager.class).add(e,"bullet");
		e.addComponent( new Synchronize("syringe", true) );
		return e;
	}

	public static Entity newClientSyringe(World world, int id) {
		Entity e = world.createEntity();
	    e.addComponent( new Transform(1, -1000, 0, 1) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
		e.addComponent( new Expires(500) );
		e.addComponent( new Parent(-1) );
		e.addComponent( new Bullet(null) );
	    e.addComponent( new PhysicsBodyComponent(new BulletBody(2/CONST.SCALE)) );
	    e.addComponent( new VisualComponent("syringe") );
	    e.addComponent( new ActionsComponent("bullet_client_action") );
	    world.getManager(GroupManager.class).add(e,"bullet");
		e.addComponent( new Synchronize("syringe", false, id) );
		return e;
	}
	
	
	
	
	public static Entity newServerBolt(World world, int mapId, float x, float y, int ttl, int parentid, WEAPON weapon) {
		Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, 0) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
		e.addComponent( new Expires(ttl) );
		e.addComponent( new Parent(parentid) );
		e.addComponent( new Bullet(weapon) );
	    e.addComponent( new PhysicsBodyComponent(new BulletBody(2/CONST.SCALE)) );
	    e.addComponent( new VisualComponent("syringe") );
	    e.addComponent( new ActionsComponent("bullet_action") );
	    world.getManager(GroupManager.class).add(e,"bullet");
		e.addComponent( new Synchronize("syringe", true) );
		return e;
	}

	public static Entity newClientBolt(World world, int id) {
		Entity e = world.createEntity();
	    e.addComponent( new Transform(1, -1000, 0, 1) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
		e.addComponent( new Expires(500) );
		e.addComponent( new Parent(-1) );
		e.addComponent( new Bullet(null) );
	    e.addComponent( new PhysicsBodyComponent(new BulletBody(2/CONST.SCALE)) );
	    e.addComponent( new VisualComponent("syringe") );
	    e.addComponent( new ActionsComponent("bullet_client_action") );
	    world.getManager(GroupManager.class).add(e,"bullet");
		e.addComponent( new Synchronize("syringe", false, id) );
		return e;
	}
	
	
	
	public static Entity newNailgunImpact(World world, int mapId, float x, float y, int ttl, int parentid) {
		Entity e = world.createEntity();
	    e.addComponent( new Transform(mapId, x, y, 0) );
		e.addComponent( new Velocity() );
		e.addComponent( new EntityState() );
		e.addComponent( new Expires(ttl) );
		e.addComponent( new Parent(parentid) );
	    e.addComponent( new PhysicsBodyComponent(new BulletBody(2/CONST.SCALE)) );
	    e.addComponent( new VisualComponent("wall") );
	    world.getManager(GroupManager.class).add(e,"bullet");
	    return e;
	}
	
	
	
	public static Entity newText(World world, int mapId, float x, float y, String text) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y, -1) );
		e.addComponent( new TextComponent(text, Color.BLACK) );
		e.addComponent( new EntityState() );
		return e;
	}

	public static Entity newServerButton(World world, int mapId, int x, int y) {
		return newText(world, mapId, x, y, "Start Server")
				.addComponent( new PhysicsBodyComponent(new BoxBody(100,50)) )
				.addComponent( new ActionsComponent("server_button") );
	}

	public static Entity newServerlistButton(World world, int mapId, int x, int y) {
		return newText(world, mapId, x, y, "Play online")
				.addComponent( new PhysicsBodyComponent(new BoxBody(100,50)) )
				.addComponent( new ActionsComponent("serverlist_button") );
	}
	
	public static Entity newClientButton(World world, int mapId, int x, int y, String address) {
		return newText(world, mapId, x, y, "Connect to " + address)
				.addComponent( new PhysicsBodyComponent(new BoxBody(100,50)) )
				.addComponent( new ActionsComponent("client_button") )
				.addComponent( new Server(address) );
	}

	
	
	
	
	
}
