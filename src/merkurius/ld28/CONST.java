package merkurius.ld28;


public class CONST {
	public static final float SCALE = 10;
	
	
	
	public static enum WEAPON { 
		NAILGUN	(400, 	0, 		50, 	1000), 
		RING	(100, 	100, 	100, 	0), 
		BAT		(100, 	100, 	100, 	0), 
		SYRINGE	(650, 	900, 	30, 	1000), 
		MINE	(100, 	100, 	100, 	0), 
		ACID	(100, 	100, 	100, 	0);
		//		reload	speed	damage	range/ttl
		
		public int reload;
		public int speed;
		public int damage;
		public int range;
		
		WEAPON(int reload, int speed, int damage, int range) {
			this.reload = reload;
			this.speed = speed;
			this.damage = damage;
			this.range = range;
		}
	};

	
	
	
  
}
