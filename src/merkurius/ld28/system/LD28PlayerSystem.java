package merkurius.ld28.system;

import merkurius.ld28.component.Input;
import merkurius.ld28.component.Shooter;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.base.KeyBindings;
import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Mouse;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;


public class LD28PlayerSystem extends EntityProcessingSystem {

    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Player>      	playerMapper;
    protected ComponentMapper<Input>      	inputMapper;

    private Entity mouse;
    private Mouse mouseComponent;
    private Transform mouseTransform;
	private CameraSystem cameraSystem;

    @SuppressWarnings("unchecked")
    public LD28PlayerSystem() {
        super( Aspect.getAspectForAll(Player.class, Transform.class, Shooter.class) );
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        inputMapper   	= ComponentMapper.getFor(Input.class, world);
        
        cameraSystem	= Systems.get(CameraSystem.class, world);
        
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
        	mouse = cameraSystem.getCameras().get(0).getComponent(CameraComponent.class).mouse;
            mouseComponent = mouse.getComponent(Mouse.class);
            mouseTransform = mouse.getComponent(Transform.class);
        }
    }

    @Override
    protected void process(Entity e) {
		
		Vector2 direction = mouseTransform.getPosition2().cpy().sub(transformMapper.get(e).getPosition2());        
		inputMapper.get(e).rotation = direction.angle();
		
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
		if (mouseComponent.clicked){
			inputMapper.get(e).input += 16;
		} 
    }
}
