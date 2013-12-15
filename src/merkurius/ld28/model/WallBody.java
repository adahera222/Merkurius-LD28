package merkurius.ld28.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.base.C;
import fr.kohen.alexandre.framework.model.PhysicsBody;

public class WallBody extends PhysicsBody {

    private float width;
    private float length;

    public WallBody(float width, float length) {
        this.width = width;
        this.length = length;
    }

    @Override
    public void initialize(World box2dworld) {
        // Create our body definition
        BodyDef bodyDef = new BodyDef();
        // Set its world position
        bodyDef.position.set(new Vector2(0, 10));
        bodyDef.type = BodyType.StaticBody;

        // Create a body from the defintion and add it to the world
        body = box2dworld.createBody(bodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(width/2, length/2);
        // Create a fixture from our polygon shape and add it to our ground body
        Fixture fixture = body.createFixture(groundBox, 0.0f);
        fixture.setRestitution(0f);
        fixture.setFriction(1f);

        Filter filter = new Filter();
        filter.categoryBits = C.CATEGORY_SCENERY;
        filter.maskBits = (short) -1;
		fixture.setFilterData(filter);
		
        // Clean up after ourselves
        groundBox.dispose();

    }

}
