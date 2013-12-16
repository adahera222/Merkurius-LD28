package merkurius.ld28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.model.Visual;

public class MenuVisual extends Visual {
   
	public MenuVisual() {
		Texture texture = new Texture(Gdx.files.internal("data/Menu.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 800, 600);
		
		sprite = new Sprite(region);		
	}
	
}
