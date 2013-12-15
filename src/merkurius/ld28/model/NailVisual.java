package merkurius.ld28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.model.Visual;

public class NailVisual extends Visual {
   
	public NailVisual() {
		Texture.setEnforcePotImages(false);
		Texture sheet = new Texture(Gdx.files.internal("data/LD28.png"));
		
		TextureRegion[][] tmp = TextureRegion.split( sheet, 16, 16 );
		
		addAnimation("nail", 0.15f, Animation.NORMAL, tmp[1][10]);
	}
	
}
