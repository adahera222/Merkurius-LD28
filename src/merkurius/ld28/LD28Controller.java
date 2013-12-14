package merkurius.ld28;

import merkurius.ld28.screen.MainScreen;
import fr.kohen.alexandre.framework.base.GameController;


public class LD28Controller extends GameController {

	@Override
	public void create() {
		this.addScreen(new MainScreen(true), "serverScreen");
		setScreen("serverScreen");
	}

}
