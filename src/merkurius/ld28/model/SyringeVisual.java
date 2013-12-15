package merkurius.ld28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.model.Visual;

public class SyringeVisual extends Visual {
   
	public SyringeVisual() {
		Texture.setEnforcePotImages(false);
		Texture sheet = new Texture(Gdx.files.internal("data/LD28.png"));
		
		TextureRegion[][] tmp = TextureRegion.split( sheet, 16, 16 );
		
		addAnimation("syringe", 0.15f, Animation.NORMAL, tmp[0][10]);
	}
	
}
