package com.suecogames.Entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;


public class PlayerEntity {

    private World world;

    private Body body;

    private BodyDef bodyDef;
    private Fixture fixture;
    private FixtureDef fixtureDef;
    String userData;

    //float width = Gdx.graphics.getWidth();
    public final float RADIUS;

    public PlayerEntity(World world){
        this.world = world;
        //this.userData = userData;

        CircleShape ballShape = new CircleShape();
        RADIUS = 1.7f;
        ballShape.setRadius(RADIUS);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation=true;
        body = world.createBody(bodyDef);


        fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;

        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 2000f;

        fixture = body.createFixture(fixtureDef);
        //fixture.setUserData(userData);
        //body.createFixture(fixtureDef);

        ballShape.dispose();
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public void setFixtureDef(FixtureDef fixtureDef) {
        this.fixtureDef = fixtureDef;
    }

    public Body getBody() {
        return body;
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
