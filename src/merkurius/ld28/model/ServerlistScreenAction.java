package merkurius.ld28.model;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.model.actions.MouseAction;
import fr.kohen.alexandre.framework.systems.interfaces.*;

public class ServerlistScreenAction extends MouseAction {

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		Systems.get(ScreenSystem.class, world).setScreen("serverlistScreen");
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
