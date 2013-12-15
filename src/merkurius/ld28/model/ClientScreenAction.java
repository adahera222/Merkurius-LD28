package merkurius.ld28.model;

import merkurius.ld28.LD28Controller;
import merkurius.ld28.component.Server;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.model.actions.MouseAction;
import fr.kohen.alexandre.framework.systems.interfaces.ScreenSystem;

public class ClientScreenAction extends MouseAction {

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		((LD28Controller) Systems.get(ScreenSystem.class, world).getController()).setServerAddress( e.getComponent(Server.class).address );
		Systems.get(ScreenSystem.class, world).setScreen("clientScreen");
	}

	@Override
	protected void mouseRelease(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOver(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOff(Entity e, Entity mouse) {
	}
	
}
