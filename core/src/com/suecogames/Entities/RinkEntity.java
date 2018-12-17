package com.suecogames.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RinkEntity extends Actor {

    private Sprite sprite;
    private String name;
    private float posX, posY;
    private Body body;
    private Fixture fixture;
    private World world;

    public RinkEntity(Sprite sprite, String name, World world) {
        this.world = world;
        this.sprite = sprite;
        this.name = name;
        this.posX = sprite.getX();
        this.posY = sprite.getY();
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(sprite, 0, 0, sprite.getWidth(), sprite.getHeight());
    }

    public Body getBody() {
        return body;
    }

}
