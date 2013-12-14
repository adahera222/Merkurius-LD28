package merkurius.ld28.system;


import merkurius.ld28.EntityFactoryLD28;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import fr.kohen.alexandre.framework.systems.DefaultMapSystem;

public class LD28MapSystem extends DefaultMapSystem {
	
	protected void checkTile(int x, int y, TiledMapTileLayer layer, int mapId) {
		if ( layer.getCell(x, y) != null ) {
			Entity map = maps.get(mapId);
			EntityFactoryLD28.newWall(world, mapId, 16+x*32+transformMapper.get(map).getPosition().x, 16+y*32+transformMapper.get(map).getPosition().y, 32, 32).addToWorld();
		}
	}
	
	protected void checkObject(MapObject object, TiledMapTileLayer layer, int mapId) {
	}
}
