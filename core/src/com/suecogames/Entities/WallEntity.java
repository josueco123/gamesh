package com.suecogames.Entities;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class WallEntity implements ContactFilter, ContactListener {

    private World world;

    private Body body;

    private Fixture fixture;

    public WallEntity(World world){
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;

        float groundPos = -19f;
        float topGroundPos = 4.5f;
        float topPos =10f;
        float leftWall = -4.65f;
        float rightWall =14.10f;

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, topGroundPos);

        // ground shape
        ChainShape groundShareBottomLeft= new ChainShape();
        ChainShape groundShareBottomRight = new ChainShape();

        ChainShape groundShareTopRight = new ChainShape();
        ChainShape groundShareTopLeft = new ChainShape();

        ChainShape groundShareRight = new ChainShape();
        ChainShape groundShareLeft = new ChainShape();

        groundShareBottomLeft.createChain(new Vector2[] {new Vector2(-20, groundPos), new Vector2(-3.5f,groundPos)});
        groundShareTopLeft.createChain(new Vector2[] {new Vector2(-20,topPos), new Vector2(-3.5f,topPos)});

        groundShareBottomRight.createChain(new Vector2[] {new Vector2(3.5f, groundPos), new Vector2(10f,groundPos)});
        groundShareTopRight.createChain(new Vector2[] {new Vector2(3.5f,topPos), new Vector2(10f,topPos)});

        groundShareRight.createChain(new Vector2[] {new Vector2(rightWall, -20), new Vector2(rightWall,20)});
        groundShareLeft.createChain(new Vector2[] {new Vector2(leftWall, -20), new Vector2(leftWall,20)});

        fixtureDef.shape = groundShareBottomLeft;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        fixtureDef.shape = groundShareTopLeft;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        fixtureDef.shape = groundShareBottomRight;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        // fixture definition
        fixtureDef.shape = groundShareTopRight;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);



        bodyDef.position.set(leftWall,0 );
        // fixture definition

        fixtureDef.shape = groundShareLeft;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        // fixture definition
        fixtureDef.shape = groundShareRight;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);


        groundShareTopLeft.dispose();
        groundShareBottomLeft.dispose();

    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public Body getBody() {
        return body;
    }

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        return false;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
