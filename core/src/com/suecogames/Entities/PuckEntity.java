package com.suecogames.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;


public class PuckEntity implements ContactFilter, ContactListener {

    private World world;
    private Body body;
    private Fixture fixture;
    public final float RADIUS;
    public BodyDef bodyDef;

    private float velocity;
    private float maxVelocity;
    private Sprite sprite;
    private float height;
    private float width;
    private Circle circle;

    public PuckEntity(float velocity, float maxVelocity, Sprite sprite, float height, float width,World world){
        this.world = world;
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
        this.sprite = sprite;
        this.height = height;
        this.width = width;
        circle = new Circle();

        bodyDef = new BodyDef();
        bodyDef.bullet=true;
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        CircleShape ballShape = new CircleShape();
        RADIUS = 1f;
        ballShape.setRadius(RADIUS);

        fixtureDef.shape = ballShape;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;
        fixtureDef.density = 50f;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("puck");

        ballShape.dispose();
        body.setTransform(0, 0, 0);

    }
    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public Circle getCircle() {
        return circle;
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public Sprite getSprite() {
        return sprite;
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
