package com.suecogames.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.suecogames.Constans;

public class ProjectileEntity  {

    private static float SPEED = 30;

    private static float DAMAGE = 50;

    private World world;

    private Body body;

    private BodyDef bodyDef;
    private Fixture fixture;
    private FixtureDef fixtureDef;
    private Texture texture;
    private Sprite sprite;

    public final float RADIUS;

    public ProjectileEntity(World world, Sprite sprite){
        this.world = world;
        this.sprite = sprite;

        PolygonShape ballShape = new PolygonShape();
        //CircleShape ballShape = new CircleShape();
        RADIUS = 0.5f;
        //ballShape.setRadius(RADIUS);
        ballShape.setAsBox(0.2f,1f);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        bodyDef.fixedRotation=true;
        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;

        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.density = 3f;

        fixture = body.createFixture(fixtureDef);

        ballShape.dispose();

    }

    public Body getBody() {
        return body;
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
