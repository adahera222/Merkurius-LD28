package merkurius.ld28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.model.Visual;

public class BoltVisual extends Visual {
   
	public BoltVisual() {
		Texture.setEnforcePotImages(false);
		Texture sheet = new Texture(Gdx.files.internal("data/LD28.png"));
		
		TextureRegion[][] tmp = TextureRegion.split( sheet, 32, 32 );
		
		addAnimation("syringe", 0.15f, Animation.NORMAL, tmp[0][3]);
	}
	
}
