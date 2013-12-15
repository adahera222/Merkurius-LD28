package merkurius.ld28.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import merkurius.ld28.EntityFactoryLD28;
import merkurius.ld28.component.Input;
import merkurius.ld28.component.Server;
import merkurius.ld28.component.Shooter;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;

public class LD28ServerListSystem extends IntervalEntityProcessingSystem {
    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Player>      	playerMapper;
    protected ComponentMapper<Input>      	inputMapper;
    protected String[] serversIP;
    protected List<Entity> servers = new ArrayList<Entity>();

	private static String sendGet(String url) throws Exception {
	
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
	
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	
		return response.toString();
	}
    @SuppressWarnings("unchecked")
    public LD28ServerListSystem() {
        super( Aspect.getAspectForAll(Server.class), 5);
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        inputMapper   	= ComponentMapper.getFor(Input.class, world);
        refreshList();
    }

    @Override
    protected void begin(){
    	refreshList();
    	
    	
    }

    private void refreshList() {
    	
    	for(Entity server : servers) {
    		server.deleteFromWorld();
    	}
    	servers.clear();
    	
    	try {
    		serversIP = sendGet("http://serverlist.alexandre.kohen.fr").split(";");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	int y = 0;
    	for(String address : serversIP) {
    		if( !address.equalsIgnoreCase(" ") ) {
    			Entity server = EntityFactoryLD28.newClientButton(world, 1, 0, y, address);
    			server.addToWorld();
    			servers.add(server);
    			Gdx.app.log("Server", address);
    		}
    		y += 50;
    	}
	}
	@Override
    protected void process(Entity e) {      
    }
}
