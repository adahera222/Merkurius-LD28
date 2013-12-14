package merkurius.ld28.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.base.C;
import fr.kohen.alexandre.framework.model.PhysicsBody;

public class BulletBody extends PhysicsBody {

    private float size;
   
    public BulletBody(float size) {
        this.size = size;
    }

    @Override
    public void initialize(World box2dworld) {
        // Create our body definition
        BodyDef bodyDef =new BodyDef();
        // Set its world position
        bodyDef.position.set(new Vector2(0, 10));
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.bullet = true;

        // Create a body from the defintion and add it to the world
        body = box2dworld.createBody(bodyDef);
        
        CircleShape circle = new CircleShape();
        circle.setRadius(size);
        
        Fixture fixture = body.createFixture(circle, 0.1f);
        fixture.setRestitution(1);
        fixture.setFriction(0.1f);
        
        Filter filter = new Filter();
        filter.categoryBits = C.CATEGORY_ITEM;
        filter.maskBits = (short) ~C.CATEGORY_ITEM;
		fixture.setFilterData(filter);
        
        // Clean up after ourselves
		circle.dispose();

    }

}
