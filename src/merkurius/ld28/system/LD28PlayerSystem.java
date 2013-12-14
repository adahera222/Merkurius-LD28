package merkurius.ld28.system;

import java.text.NumberFormat;

import merkurius.ld28.component.Input;
import merkurius.ld28.component.Shooter;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.base.KeyBindings;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Mouse;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;


public class LD28PlayerSystem extends EntityProcessingSystem implements PlayerSystem {
    private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance();

    static {
        NUMBER_FORMATTER.setMinimumFractionDigits(1);
        NUMBER_FORMATTER.setMaximumFractionDigits(1);
    }

    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Player>      	playerMapper;
    protected ComponentMapper<Input>      	inputMapper;

    private Entity mouse;
    private Mouse mouseComponent;
    private Transform mouseTransform;
    private int playerId = -1;

    @SuppressWarnings("unchecked")
    public LD28PlayerSystem() {
        super( Aspect.getAspectForAll(Player.class, Transform.class, Shooter.class) );
    }
    
    public LD28PlayerSystem(int playerId) {
    	this();
    	this.playerId = playerId;
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        inputMapper   	= ComponentMapper.getFor(Input.class, world);
        
        KeyBindings.addKey(Keys.LEFT, "move_left");
		KeyBindings.addKey(Keys.RIGHT, "move_right");
		KeyBindings.addKey(Keys.UP, "move_up");
		KeyBindings.addKey(Keys.DOWN, "move_down");
		
		KeyBindings.addKey(Keys.Q, "move_left");
		KeyBindings.addKey(Keys.D, "move_right");
		KeyBindings.addKey(Keys.Z, "move_up");
		KeyBindings.addKey(Keys.S, "move_down");
    }

    @Override
    protected void begin(){
        if (mouse == null){
            mouse = world.getManager(TagManager.class).getEntity("cameraFollowPlayer").getComponent(CameraComponent.class).mouse;
            mouseComponent = mouse.getComponent(Mouse.class);
            mouseTransform = mouse.getComponent(Transform.class);
        }
    }

    @Override
    protected void process(Entity e) {
    	if( playerMapper.get(e).playerId == playerId ) {
			if (mouseComponent.clicked){
				shooterMapper.get(e).setShooting(true);
			} else {
				shooterMapper.get(e).setShooting(false);
			}
			Vector2 direction = mouseTransform.getPosition2().cpy().sub(transformMapper.get(e).getPosition2());
			shooterMapper.get(e).setAim(direction.angle());
	        
	        inputMapper.get(e).input = 0;
	        if ( KeyBindings.isKeyPressed("move_left") ) {
	        	inputMapper.get(e).input += 1;
			}
			
			if ( KeyBindings.isKeyPressed("move_right") ) {
				inputMapper.get(e).input += 2;
			} 
			
			if ( KeyBindings.isKeyPressed("move_up") ) {
				inputMapper.get(e).input += 4;
			}
			
			if ( KeyBindings.isKeyPressed("move_down") ) {
				inputMapper.get(e).input += 8;
			}
    	}        
    }
    
    public void setPlayerId(int playerId) {
    	this.playerId = playerId;
    }
}
