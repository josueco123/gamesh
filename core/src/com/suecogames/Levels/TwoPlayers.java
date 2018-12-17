package com.suecogames.Levels;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.suecogames.AssetsManager;
import com.suecogames.Entities.*;
import com.suecogames.MainGame;
import com.suecogames.Screens.EndGameScreen;

public class TwoPlayers extends InputAdapter implements Screen {

    Sprite rink, player1, player2, puck,projectile;
    PlayerEntity playerEntity1, playerEntity2;
    ProjectileEntity projectilePlayer1,projectilePlayer2;
    PuckEntity puckEntity;
    private Stage stage;
    RinkEntity rinkEntity;
    boolean pause = false;
    MainGame game;

    Texture projectilTexture;
    Texture playerTex2,playerTex1, space, puckTexture;

    boolean lifePlayer1,lifePlayer2;

    private ParticleEffect particleGol1,particleGol2;

    SpriteBatch fontBatch;

    Matrix4 mx4Font;
    Vector2 maxVelocity;

    //Creamos los mouseJoints para
    private MouseJointDef mouseJointDefPlayer1;
    private MouseJointDef mouseJointDefPlayer2;
    private Vector3 touchPositionPlayer1 = new Vector3();
    private Vector3 touchPositionPlayer2 = new Vector3();
    private MouseJoint jointJugador1, jointJugador2;


    private Box2DDebugRenderer debugRenderer;
    private final float MAXVELOCITY = 50f;
    //Box2D

    public World world;

    SpriteBatch batch, batch2;

    WallEntity wallEntity;

    private double angle;
    AssetsManager myAssetManager;
    StretchViewport viewport;

    OrthographicCamera camera, camera2;
    ShapeRenderer shapeRenderer;
    private final int VELOCITYITERATIONS = 20, POSITIONITERATIONS = 3;
    int pointerPlayer1, pointerPlayer2, pointerActual;
    int puntuacionJugador1 = 0;
    int puntuacionJugador2 = 0;
    Matrix4 oldTransformMatrix;

    Texture blank;
    float healthPlayer1, healthPlayer2;//0 = dead, 1 = full healthPlayer1

    boolean firstContact;
    Dialog dialog;

    public EndGameScreen endGameScreen;


    public TwoPlayers(final MainGame game){

        myAssetManager = new AssetsManager();
        this.game = game;

        debugRenderer = new Box2DDebugRenderer();

        //Establecemos la máxima velocidad del disco
        maxVelocity = new Vector2(100, 100);

        //Creamos la variables de pantalla para reescalar todos los objetos
        float screenWidth = 400;
        float screenHeight = 600;
        float gameWidth = 203;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        //Camara uno se utilizará para Box2d por eso lo dividimos entre 10 ya que World utiliza otra medida diferente al pixel
        camera = new OrthographicCamera(gameWidth / 10, gameHeight / 10);

        //Creamos la camara2 que será la que tiene la pista
        camera2 = new OrthographicCamera(gameWidth, gameHeight);

        this.world = new World(new Vector2(0, 0), true);

        wallEntity = new WallEntity(world);

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        batch2 = new SpriteBatch();


        viewport = new StretchViewport(screenHeight, screenWidth, camera2);
        stage = new Stage(viewport, batch);

        blank = new Texture("blank.png");

        projectilTexture = new Texture("laser.png");


        playerTex2 = new Texture("shipblue.PNG");
        playerTex1 = new Texture("ship3.png");
        space = new Texture("backgraund.png");
        puckTexture = new Texture("energyball.png");

        projectile = new Sprite(projectilTexture,(int) screenHeight/7, (int)screenWidth/7);
        player2 = new Sprite(playerTex2,(int) screenHeight/4, (int)screenWidth/3);
        player1 = new Sprite(playerTex1,(int) screenHeight/5, (int)screenWidth/4);
        rink = new Sprite(space,(int) screenHeight, (int)screenWidth);
        puck = new Sprite(puckTexture,(int) screenHeight/4, (int)screenWidth/4);

        particleGol1 = myAssetManager.getParticleGol1();
        particleGol2 = myAssetManager.getParticleGol2();

        dialog = new Dialog("", myAssetManager.getSkin()){
            public void result(Object obj) {
                System.out.println("result "+obj);
                pause = false;

                if(obj.equals(false)) {
                    retornarPausa();
                }

                if(obj.equals(true)){
                    game.setScreen(game.menuScreen);
                }

            }
        };

        dialog.text("Are you want to end the match?");
        dialog.button("Yes", true);
        dialog.button("No", false);



    }

    public PuckEntity getPuckEntity() {
        return puckEntity;
    }
    public World getWorld() {
        return world;
    }

    private MouseJointDef createMouseJointDefinition(Body body) {
        MouseJointDef jointDef;
        jointDef = new MouseJointDef();
        jointDef.bodyA = body;
        jointDef.collideConnected = true;
        jointDef.frequencyHz = 400;
        jointDef.dampingRatio = 1;

        return jointDef;
    }

    /**
     * Resetea las posiciones de los jugadores después de un gol y posiciona el disco.
     *
     */
    private void resetearPosiciones(boolean jugador) {
        //Destruimos los joints
        destroyJoints();

        firstContact = false;

        //Asignamos la posicion de la pelota a 0
        puckEntity.getBody().setLinearVelocity(0, 0);
        //Asignamos la posición a cada jugador
        playerEntity1.getBody().setTransform(0, 10, 0);
        playerEntity2.getBody().setTransform(0, -10, 0);

        projectilePlayer2.getBody().setTransform(4,17,0);
        projectilePlayer1.getBody().setTransform(4,-17,0);

        projectilePlayer1.getBody().setLinearVelocity(0,0);
        projectilePlayer2.getBody().setLinearVelocity(0,0);


        //Comprobamos de quien ha sido el gol y posicionamos la pelota en su campo
        if (jugador) {
            puckEntity.getBody().setTransform(0, 3, 0);
        } else {
            puckEntity.getBody().setTransform(0, -3, 0);
        }
    }

    /**
     * Metodo destruye el "MouseJoint" objeto para hacer drag sobre los jugadores
     */
    private synchronized void destroyJoints() {
        if (jointJugador1 != null) {
            world.destroyJoint(jointJugador1);
            playerEntity1.getBody().setLinearVelocity(0, 0);
            jointJugador1 = null;
        }

        if (jointJugador2 != null) {
            world.destroyJoint(jointJugador2);
            playerEntity2.getBody().setLinearVelocity(0, 0);
            jointJugador2 = null;
        }

    }


    @Override
    public void show() {

        //creacion de Entidades
        playerEntity1 = new PlayerEntity(world);
        playerEntity2 = new PlayerEntity(world);
        puckEntity = new PuckEntity(0,0,puck,rink.getHeight()/2,rink.getWidth()/2,world);
        rinkEntity = new RinkEntity(rink,"rink",world);
        rinkEntity.setPosition(0,0);
        projectilePlayer1 = new ProjectileEntity(world, projectile);
        projectilePlayer2 = new ProjectileEntity(world, projectile);

        puckEntity.getBody().setUserData("puck");
        playerEntity1.getBody().setUserData("player1");
        playerEntity2.getBody().setUserData("player2");
        wallEntity.getBody().setUserData("wall");
        projectilePlayer1.getBody().setUserData("bulletp1");
        projectilePlayer2.getBody().setUserData("bulletp2");


        //Creamos un objeto Matrix4 para poder crear un texto invertido
        mx4Font = new Matrix4();
        oldTransformMatrix = new Matrix4();
        stage.addActor(rinkEntity);
        angle = 0;

        game.isCHALLENGE = false;

        if(game.MUSIC)
            game.bgMusic.stop();

        iniciarJuego();


        mouseJointDefPlayer1 = createMouseJointDefinition(wallEntity.getBody());
        mouseJointDefPlayer2 = createMouseJointDefinition(wallEntity.getBody());
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //PAUSA
        if (!pause) {
            world.step(1 / 60f, 8, 1);
        } else {
            world.step(0, VELOCITYITERATIONS, POSITIONITERATIONS);
        }

        //Actualizamos camaras y batch
        camera.update();
        camera2.update();
        batch.setProjectionMatrix(camera2.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch2.setProjectionMatrix(camera.combined);

        //Imprimimos el stage que contiene pista
        stage.act(delta);
        stage.draw();

        //actualizamos la vida
        batch.begin();
        particleGol1.draw(batch);
        particleGol2.draw(batch);
        batch.setColor(Color.GREEN);
        if(healthPlayer2 < 0.2)
            batch.setColor(Color.RED);
        batch.draw(blank, 0, 0, 200 * healthPlayer2, 6);
        batch.end();

        batch.begin();
        batch.setColor(Color.GREEN);
        if(healthPlayer1 < 0.2)
            batch.setColor(Color.RED);
        if(healthPlayer1 > 0)
        batch.draw(blank, 385, 395, 200 * healthPlayer1, 6);
        batch.end();

        if(healthPlayer2 <= 0)
            lifePlayer2 = false;

        if(healthPlayer1 <= 0)
            lifePlayer1 = false;

        particleGol1.update(delta);
        particleGol2.update(delta);

        //Imprimimos el marcador y lo actualizamos
        imprimirMarcador();
        limitarPosicionJugador();


        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            pause = true;
            pauseMenu();
        }

        regularVelocidad();
        imprimirImagenes();

        comprobarGol(puckEntity.getBody());
        createCollisionListener();
    }

    private void pauseMenu(){

        Gdx.input.setInputProcessor(stage);
        dialog.show(stage);
        //stage.addActor(dialog);

    }

    public void retornarPausa(){
        Gdx.input.setInputProcessor(this);
    }

    private void shootPlayer1 (){
        if(lifePlayer1)
        if(firstContact){
                if(projectilePlayer1.getBody().getPosition().y < -15 || projectilePlayer1.getBody().getPosition().y > 15){
                    projectilePlayer1.getBody().setTransform(playerEntity1.getBody().getPosition().x+0.4f, playerEntity1.getBody().getPosition().y-1.5f, 0);
                    projectilePlayer1.getBody().setLinearVelocity(0,-30);
                    if(game.SOUND)
                        game.shootSound.play(0.7f);
                }
        }

    }

    private void shootPlayer2(){
        if(lifePlayer2)
        if(firstContact){
            if(projectilePlayer2.getBody().getPosition().y < -15 || projectilePlayer2.getBody().getPosition().y > 15){
                projectilePlayer2.getBody().setTransform(playerEntity2.getBody().getPosition().x+0.4f, playerEntity2.getBody().getPosition().y+1.2f, 0);
                projectilePlayer2.getBody().setLinearVelocity(0,30);
                if(game.SOUND)
                    game.shootSound.play(0.7f);
            }
        }
    }


    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureA.getBody().getUserData() != null && fixtureB.getBody().getUserData() != null){
                    if(fixtureA.getBody().getUserData().equals("puck") && fixtureB.getBody().getUserData().equals("player1")
                            || fixtureA.getBody().getUserData().equals("player1") && fixtureB.getBody().getUserData().equals("puck")){
                        firstContact=true;
                        if(game.SOUND)
                            game.contacSound.play(0.6f);
                    }

                    if(fixtureA.getBody().getUserData().equals("puck") && fixtureB.getBody().getUserData().equals("player2")
                            || fixtureA.getBody().getUserData().equals("player2") && fixtureB.getBody().getUserData().equals("puck")){
                        firstContact=true;
                        if(game.SOUND)
                            game.contacSound.play(0.6f);
                    }

                    if(fixtureA.getBody().getUserData().equals("bulletp2") && fixtureB.getBody().getUserData().equals("player1")
                            || fixtureA.getBody().getUserData().equals("player1") && fixtureB.getBody().getUserData().equals("bulletp2")){
                        healthPlayer1 -= 0.05;
                        if(game.SOUND)
                            game.explodeSound.play(0.6f);

                        if(game.VIBRATE)
                            Gdx.input.vibrate(200);

                    }

                    if(fixtureA.getBody().getUserData().equals("bulletp1") && fixtureB.getBody().getUserData().equals("player2")
                            || fixtureA.getBody().getUserData().equals("player2") && fixtureB.getBody().getUserData().equals("bulletp1")){
                        healthPlayer2 -= 0.05;
                        if(game.SOUND)
                            game.explodeSound.play(0.6f);

                        if(game.VIBRATE)
                            Gdx.input.vibrate(200);

                    }

                }
            }

            @Override
            public void endContact(Contact contact) {
                // TODO Auto-generated method stub
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // TODO Auto-generated method stub
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // TODO Auto-generated method stub
            }

        });
    }

    /**
     * Controlamos la posicion de los jugadores para que no puedan entrar en la porteria ni pasar al campo contrario
     */
    private void limitarPosicionJugador() {

        if (playerEntity1.getBody().getPosition().y < 2.2f) {
            playerEntity1.getBody().setTransform(playerEntity1.getBody().getPosition().x, 2.2f, 0);
        }

        if (playerEntity1.getBody().getPosition().x - playerEntity1.RADIUS < -9) {
            playerEntity1.getBody().setTransform(-9f + playerEntity1.RADIUS, playerEntity1.getBody().getPosition().y, 0);
        }

        if (playerEntity1.getBody().getPosition().x + playerEntity1.RADIUS > 9) {
            playerEntity1.getBody().setTransform(9f - playerEntity1.RADIUS, playerEntity1.getBody().getPosition().y, 0);
        }

        if (playerEntity1.getBody().getPosition().y + playerEntity1.RADIUS > 14) {
            playerEntity1.getBody().setTransform(playerEntity1.getBody().getPosition().x, 14f - playerEntity1.RADIUS, 0);
        }


        if (playerEntity2.getBody().getPosition().y > -2.2f) {
            playerEntity2.getBody().setTransform(playerEntity2.getBody().getPosition().x, -2.2f, 0);
        }

        if (playerEntity2.getBody().getPosition().x - playerEntity2.RADIUS < -9) {
            playerEntity2.getBody().setTransform(-9f + playerEntity2.RADIUS, playerEntity2.getBody().getPosition().y, 0);
        }

        if (playerEntity2.getBody().getPosition().x + playerEntity2.RADIUS > 9) {
            playerEntity2.getBody().setTransform(9f - playerEntity2.RADIUS, playerEntity2.getBody().getPosition().y, 0);
        }

        if (playerEntity2.getBody().getPosition().y - playerEntity2.RADIUS < -14) {
            playerEntity2.getBody().setTransform(playerEntity2.getBody().getPosition().x, -14f + playerEntity2.RADIUS, 0);
        }
    }

    /**
     * Metodo que regula la velocidad del disco si no supera la velocidad maxima la disminuye progresivamente
     */
    private void regularVelocidad() {

        Vector2 velocity = puckEntity.getBody().getLinearVelocity();
        float speed = velocity.len();
        if (speed > MAXVELOCITY) {
            puckEntity.getBody().setLinearVelocity(velocity.scl(MAXVELOCITY / speed));
        }
    }

    private void comprobarGol(Body disco) {
        if (disco.getPosition().y > 20) {
            resetearPosiciones(true);
            if(game.VIBRATE)
                Gdx.input.vibrate(200);
            if(game.SOUND)
                game.goalSound.play(0.6f);
            particleGol2.start();
            puntuacionJugador1++;
        }

        if (disco.getPosition().y < -20) {
            resetearPosiciones(false);
            if(game.VIBRATE)
                Gdx.input.vibrate(200);
            if(game.SOUND)
                game.goalSound.play(0.6f);
            particleGol1.start();
            puntuacionJugador2++;
        }
    }

    private void imprimirMarcador() {
        Matrix4 auxiliar = batch.getTransformMatrix();

        String puntuacion1, puntuacion2, total1,total2;
        puntuacion1 = String.valueOf(puntuacionJugador1);
        puntuacion2 = String.valueOf(puntuacionJugador2);


        //total1 = puntuacion1;
        total2 = puntuacion2 + " - " + puntuacion1;
        // batch.begin();
        //myAssetManager.fuenteMarcador.setColor(Color.RED);
        //myAssetManager.fuenteMarcador.draw(batch, total1, 450, 190);
        //myAssetManager.fuenteMarcadorInvert.draw(batch, total1, 450, 190);
        //batch.end();
        fontBatch.setTransformMatrix(mx4Font);

        fontBatch.begin();
        myAssetManager.fuenteMarcador.draw(fontBatch, total2, 0, 0);
        fontBatch.end();
        fontBatch.setTransformMatrix(oldTransformMatrix);

        if(puntuacionJugador1 == 7){
            endGameScreen = new EndGameScreen(game,true,total2);
            game.setScreen(endGameScreen);
        }else if(puntuacionJugador2 == 7){
            game.challengeLEVEL++;
            endGameScreen = new EndGameScreen(game,false,total2);
            game.setScreen(endGameScreen);
        }
    }

    /**
     * iniciamos todas las posiciones de los jugadores, disco y actualizamos las puntuaciones de los jugadores
     */
    private void iniciarJuego() {

        puntuacionJugador1 = 0;
        puntuacionJugador2 = 0;

        healthPlayer1 = 1;
        healthPlayer2 = 1;

        firstContact = false;
        lifePlayer1 = true;
        lifePlayer2 = true;

        puckEntity.getBody().setTransform(0, 0, 0);
        playerEntity1.getBody().setTransform(0, 5, 0);
        playerEntity2.getBody().setTransform(0, -5, 0);
        projectilePlayer1.getBody().setTransform(0,-17,0);
        projectilePlayer2.getBody().setTransform(0,17,0);


        fontBatch = new SpriteBatch();
        fontBatch.setProjectionMatrix(camera2.combined);


        oldTransformMatrix = fontBatch.getTransformMatrix().cpy();
        mx4Font.rotate(new Vector3(0, 0, 1), 270);
        mx4Font.trn(590, 225, 0);

        particleGol1.getEmitters().first().setPosition(300,Gdx.graphics.getHeight()/ (Gdx.graphics.getHeight()) / Gdx.graphics.getHeight() );
        particleGol2.getEmitters().first().setPosition(300 ,420 );
    }

    /**
     * Metodo que imprime el disco y ambos jugaodres con su Sprite
     */
    public void imprimirImagenes(){
        batch2.begin();
        //batch.draw(player, player.getX(), player.getY());
        puck.setPosition(puckEntity.getBody().getPosition().x, puckEntity.getBody().getPosition().y);
        batch2.draw(puck, puckEntity.getBody().getPosition().x-puckEntity.RADIUS, puckEntity.getBody().getPosition().y-puckEntity.RADIUS, puckEntity.RADIUS*2,puckEntity.RADIUS*2);
        batch2.draw(projectile, projectilePlayer1.getBody().getPosition().x, projectilePlayer1.getBody().getPosition().y, projectilePlayer1.RADIUS/2,projectilePlayer1.RADIUS*4);
        batch2.draw(projectile, projectilePlayer2.getBody().getPosition().x, projectilePlayer2.getBody().getPosition().y, projectilePlayer2.RADIUS/2,projectilePlayer2.RADIUS*4);
        batch2.draw(player1, playerEntity1.getBody().getPosition().x-playerEntity1.RADIUS, playerEntity1.getBody().getPosition().
                y-playerEntity1.RADIUS, playerEntity1.RADIUS*2, playerEntity1.RADIUS*2);

        batch2.draw(player2, playerEntity2.getBody().getPosition().x-playerEntity2.RADIUS, playerEntity2.getBody().getPosition().
                y-playerEntity2.RADIUS, playerEntity2.RADIUS*2, playerEntity2.RADIUS*2);
        batch2.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
        playerEntity1.detach();
        playerEntity2.detach();
        projectilePlayer1.detach();
        projectilePlayer2.detach();
        puckEntity.detach();
        destroyJoints();

    }

    @Override
    public void dispose() {
        //myAssetManager.getTextureAtlas().dispose();
        stage.dispose();
        world.dispose();
        particleGol1.dispose();
        particleGol2.dispose();
        shapeRenderer.dispose();
        debugRenderer.dispose();

    }

    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.unproject(tmp.set(screenX, screenY, 0));
        pointerActual = pointer;
        //Arrastramos los jugadores
        QueryCallback queryCallback = new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {
                if (fixture.testPoint(tmp.x,
                        tmp.y) && (fixture.getBody() == playerEntity1.getBody())) {
                    touchPositionPlayer1 = tmp;
                    pointerPlayer1 = pointerActual;
                    mouseJointDefPlayer1.bodyB = fixture.getBody();
                    mouseJointDefPlayer1.target.set(touchPositionPlayer1.x, touchPositionPlayer1.y);
                    mouseJointDefPlayer1.maxForce = 20000000.0f * fixture.getBody().getMass();
                    jointJugador1 = (MouseJoint) world.createJoint(mouseJointDefPlayer1);
                    return true;
                }

                if (fixture.testPoint(tmp.x,
                        tmp.y) && (fixture.getBody() == playerEntity2.getBody())) {
                    touchPositionPlayer2 = tmp;
                    pointerPlayer2 = pointerActual;
                    mouseJointDefPlayer2.bodyB = fixture.getBody();
                    mouseJointDefPlayer2.target.set(touchPositionPlayer2.x, touchPositionPlayer2.y);
                    mouseJointDefPlayer2.maxForce = 200000000.0f * fixture.getBody().getMass();
                    jointJugador2 = (MouseJoint) world.createJoint(mouseJointDefPlayer2);
                    return true;
                }

                return false;
            }
        };


        world.QueryAABB(queryCallback, tmp.x - 0.1f, tmp.y - 0.1f, tmp.x - 0.1f, tmp.y - 0.1f);

        /*if (this.isPaused()) {
            pause = false;
        }*/

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        /* Whether the input was processed */
        boolean processed = false;

        if (jointJugador1 != null && pointer == pointerPlayer1) {

            /* Translate camera point to world point */
            camera.unproject(touchPositionPlayer1.set(screenX, screenY, 0));
            //System.out.println("jugador1 x" + playerEntity1.getBody().getPosition().x);
            //System.out.println("jugador1 y" + playerEntity1.getBody().getPosition().y);

            if (touchPositionPlayer1.y > playerEntity1.getBody().getPosition().y + playerEntity1.RADIUS) {
                touchPositionPlayer1.y = playerEntity1.getBody().getPosition().y + playerEntity1.RADIUS;
            } else if (touchPositionPlayer1.y < playerEntity1.getBody().getPosition().y - playerEntity1.RADIUS) {
                touchPositionPlayer1.y = playerEntity1.getBody().getPosition().y - playerEntity1.RADIUS;

            }
            jointJugador1.setTarget(new Vector2(touchPositionPlayer1.x, touchPositionPlayer1.y));

            processed = true;
        }
        if (jointJugador2 != null && pointer == pointerPlayer2) {

            /* Translate camera point to world point */
            camera.unproject(touchPositionPlayer2.set(screenX, screenY, 0));
            //System.out.println("jugador2 x" + playerEntity2.getBody().getPosition().x);
            //System.out.println("jugador2 y" + playerEntity2.getBody().getPosition().y);
            if (touchPositionPlayer2.y > 1.5f || touchPositionPlayer2.y < -14.5
                    || touchPositionPlayer2.x < -9 || touchPositionPlayer2.x > 9) {

                world.destroyJoint(jointJugador2);
                playerEntity2.getBody().setLinearVelocity(0, 0);
                jointJugador2 = null;
            } else {
                jointJugador2.setTarget(new Vector2(touchPositionPlayer2.x, touchPositionPlayer2.y));
            }
            processed = true;
        }
        return processed;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (jointJugador1 != null && pointerPlayer1 == pointer) {
            world.destroyJoint(jointJugador1);
            playerEntity1.getBody().setLinearVelocity(0, 0);
            jointJugador1 = null;
            shootPlayer1();
        }
        if (jointJugador2 != null && pointerPlayer2 == pointer) {
            world.destroyJoint(jointJugador2);
            playerEntity2.getBody().setLinearVelocity(0, 0);
            jointJugador2 = null;
            shootPlayer2();
        }


        return true;
    }


}
