package com.suecogames.AIClases;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class SteeringAgent implements Steerable<Vector2> {

    Body body;
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAceleration;
    float masxAngularSpeed, maxAngularAceleration;


    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringAcceleration;

    public SteeringAgent (Body body, float boundingRadius, float plus){
        this.body = body;
        this.boundingRadius = boundingRadius;

        this.maxLinearSpeed = 2000000 + plus ;
        this.maxLinearAceleration = 100000050 + plus;
        this.masxAngularSpeed = 2000000;
        this.maxAngularAceleration = 100000050;

        this.tagged = false;
        this.steeringAcceleration = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    public void update(float delta){

        if (behavior != null) {
            // Calculate steering acceleration
            behavior.calculateSteering(steeringAcceleration);

            /*
             * Here you might want to add a motor control layer filtering steering accelerations.
             *
             * For instance, a car in a driving game has physical constraints on its movement:
             * - it cannot turn while stationary
             * - the faster it moves, the slower it can turn (without going into a skid)
             * - it can brake much more quickly than it can accelerate
             * - it only moves in the direction it is facing (ignoring power slides)
             */

            // Apply steering acceleration to move this agent
            applySteering(delta);
        }

    }

    private void applySteering(float time){
        boolean anyAcceleration = false;

        if(!steeringAcceleration.linear.isZero()){
            Vector2 force = steeringAcceleration.linear.scl(time);
            body.applyForceToCenter(force,true);
            anyAcceleration = true;
        }

        if(anyAcceleration){
            Vector2 velocity = body.getLinearVelocity();
            float currentsquere = velocity.len2();
            if(currentsquere > maxLinearSpeed * maxLinearSpeed){
                body.setLinearVelocity(velocity.scl(maxLinearSpeed/ (float)Math.sqrt(currentsquere)));
                //body.setLinearVelocity(velocity.x*2,velocity.y*2);
            }
        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return masxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.masxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxLinearAceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public Body getBody() {
        return body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior(){
        return behavior;
    }
}
