package merkurius.ld28;


public class CONST {
	public static final float SCALE = 1;
	
	
	
	public static enum WEAPON { 
		NAILGUN	(100, 100, 100), 
		RING	(100, 100, 100), 
		BAT		(100, 100, 100), 
		SYRINGE	(650, 900, 100), 
		MINE	(100, 100, 100), 
		ACID	(100, 100, 100);
		
		public int reload;
		public float speed;
		public float damage;
		
		WEAPON(int reload, float speed, float damage) {
			this.reload = reload;
			this.speed = speed;
			this.damage = damage;
		}
	};

	
	
	
  
}
