package merkurius.ld28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.model.Visual;

public class PlayerVisual extends Visual {
   
	public PlayerVisual(int playerId) {
		Texture.setEnforcePotImages(false);
		Texture sheet = new Texture(Gdx.files.internal("data/LD28.png"));
		
		TextureRegion[][] tmp = TextureRegion.split( sheet, 32, 32 );
		
		addAnimation("nailgun", 0.15f, Animation.NORMAL, 	tmp[playerId][0]);
		addAnimation("bolt", 0.15f, Animation.NORMAL, 		tmp[playerId][1]);
		addAnimation("bolt_shoot", 0.15f, Animation.NORMAL, tmp[playerId][2]);		
	}
	
}
