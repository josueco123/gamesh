package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.suecogames.AssetsManager;
import com.suecogames.BaseScreen;
import com.suecogames.MainGame;

public class ChallengeScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private Label challengeLabel,textLabel,levelLabel,youLabel;

    private TextButton btnGo,btnBack;

    private AssetsManager manager;

    private Texture textBackground;
    private SpriteBatch batch;
    I18NBundle bundle;
    String level;


    public ChallengeScreen(final MainGame game) {
        super(game);

        stage = new Stage(game.screenPort);

        manager = new AssetsManager();

        skin = manager.getSkin();

        bundle = game.bundle;

        challengeLabel = new Label(bundle.get("mainmenu.challenge"),skin);
        textLabel = new Label(bundle.get("textChallenge2"),skin);
        levelLabel = new Label(bundle.get("level"),skin);
        level = yourLevel();
        youLabel = new Label(level,skin);

        btnGo = new TextButton(bundle.get("play"),skin);
        btnBack = new TextButton(bundle.get("back"),skin);

        batch = new SpriteBatch();
        textBackground = new Texture("corona_up.png");


        btnBack.setSize(150,60);
        btnGo.setSize(200,80);

        int middleWidth = Gdx.graphics.getWidth()/3;
        int divide = ((Gdx.graphics.getHeight()/2)/5)+15;

        //btnBronze.setPosition(middleWidth,350);
        btnGo.setPosition(middleWidth,divide*2);
        btnBack.setPosition(middleWidth/6,divide);

        challengeLabel.setPosition(Gdx.graphics.getWidth()/2.5f,divide*7);
        textLabel.setPosition(Gdx.graphics.getWidth()/3f,divide*5.5f);
        levelLabel.setPosition(Gdx.graphics.getWidth()/2.7f,divide*3.8f);
        youLabel.setPosition(Gdx.graphics.getWidth()/2.5f,divide*3.4f);

        stage.addActor(btnBack);
        stage.addActor(btnGo);
        stage.addActor(challengeLabel);
        stage.addActor(textLabel);
        stage.addActor(levelLabel);
        stage.addActor(youLabel);

        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.setScreen(game.menuScreen);
            }
        });


        btnGo.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.isCHALLENGE = true;
                nextLevel();
            }
        });
    }

    public void nextLevel(){
        if(game.challengeLEVEL < 5)
            game.setScreen(game.levelOne);

        if(game.challengeLEVEL >= 5 && game.challengeLEVEL < 9)
            game.setScreen(game.levelOneHalf);

        if(game.challengeLEVEL >= 9 && game.challengeLEVEL < 13)
            game.setScreen(game.levelTwo);

        if(game.challengeLEVEL >= 13 && game.challengeLEVEL < 17)
            game.setScreen(game.levelTwoHalf);

        if(game.challengeLEVEL >= 17 && game.challengeLEVEL < 22)
            game.setScreen(game.levelThree);

        if(game.challengeLEVEL >= 22 && game.challengeLEVEL < 26)
            game.setScreen(game.levelThree);

        if(game.challengeLEVEL >= 26 && game.challengeLEVEL < 31)
            game.setScreen(game.levelThreeHalf);

        if(game.challengeLEVEL >= 31 && game.challengeLEVEL < 35)
            game.setScreen(game.levelThreeQuarters);

        if(game.challengeLEVEL >= 35 && game.challengeLEVEL < 40)
            game.setScreen(game.levelFour);

        if(game.challengeLEVEL >= 40 && game.challengeLEVEL < 44)
            game.setScreen(game.levelFourHalf);

        if(game.challengeLEVEL >= 44 && game.challengeLEVEL < 48)
            game.setScreen(game.levelFourQuarters);

        if(game.challengeLEVEL >= 48 && game.challengeLEVEL < 51)
            game.setScreen(game.levelFive);
    }

    public String yourLevel(){
        String level = bundle.get("bronze");

        if(game.challengeLEVEL < 11)
            level =  bundle.get("bronze")+ " " + game.challengeLEVEL;

        if(game.challengeLEVEL >= 11 && game.challengeLEVEL < 21)
            level =  bundle.get("silver") + " " + game.challengeLEVEL;

        if(game.challengeLEVEL >= 21 && game.challengeLEVEL < 31)
            level =  bundle.get("gold") +  " " + game.challengeLEVEL;

        if(game.challengeLEVEL >= 31 && game.challengeLEVEL < 41)
            level =  bundle.get("diamond") + " " + game.challengeLEVEL;

        if(game.challengeLEVEL >= 41 && game.challengeLEVEL < 51)
            level =  bundle.get("master") + " " + game.challengeLEVEL;


        return level;
    }

    @Override
    public void show() {Gdx.input.setInputProcessor(stage);}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textBackground,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        youLabel.setText(yourLevel());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
