package merkurius.ld28;

import merkurius.ld28.screen.MainScreen;
import merkurius.ld28.screen.MenuScreen;
import merkurius.ld28.screen.ServerlistScreen;
import fr.kohen.alexandre.framework.base.GameController;


public class LD28Controller extends GameController {

	@Override
	public void create() {
		this.addScreen(new MainScreen(true), "serverScreen");
		this.addScreen(new MainScreen(false), "clientScreen");
		this.addScreen(new MenuScreen(), "menuScreen");
		this.addScreen(new ServerlistScreen(), "serverlistScreen");
		setScreen("menuScreen");
	}
	
	public void setServerAddress(String address) {
		((MainScreen) screens.get("clientScreen")).setAddress(address);
	}

}
