package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.suecogames.AssetsManager;
import com.suecogames.BaseScreen;
import com.suecogames.Constans;
import com.suecogames.MainGame;

public class OnePlayerScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private Label levels;

    private TextButton btnBronze,btnSilver,btnGold,btnDiamond,btnMaster,btnBack;

    private AssetsManager manager;

    private Texture textBackground;
    private SpriteBatch batch;


    public OnePlayerScreen(final MainGame game) {
        super(game);

        stage = new Stage(game.screenPort);

        manager = new AssetsManager();

        skin = manager.getSkin();

        I18NBundle bundle = game.bundle;

        levels = new Label(bundle.get("levels"),skin);
        //levels.setFontScale(7);
        //levels.setSize(100,100);

        btnBronze = new TextButton(bundle.get("bronze"),skin);
        btnDiamond = new TextButton(bundle.get("diamond"),skin);
        btnGold = new TextButton(bundle.get("gold"),skin);
        btnMaster = new TextButton(bundle.get("master"),skin);
        btnSilver = new TextButton(bundle.get("silver"),skin);
        btnBack = new TextButton(bundle.get("back"),skin);

        batch = new SpriteBatch();
        textBackground = new Texture("corona_up.png");


        btnBack.setSize(150,60);
        btnBronze.setSize(200,80);
        btnSilver.setSize(200,80);
        btnGold.setSize(200,80);
        btnDiamond.setSize(200,80);
        btnMaster.setSize(200,80);

        int middleWidth = Gdx.graphics.getWidth()/3;
        int divide = ((Gdx.graphics.getHeight()/2)/5)+15;

        //btnBronze.setPosition(middleWidth,350);
        btnBronze.setPosition(middleWidth,divide*5);
        btnSilver.setPosition(middleWidth,divide*4);
        btnGold.setPosition(middleWidth,divide*3);
        btnDiamond.setPosition(middleWidth,divide*2);
        btnMaster.setPosition(middleWidth,divide);
        btnBack.setPosition(middleWidth/6,divide);

        levels.setPosition(Gdx.graphics.getWidth()/2.5f,divide*7);

        stage.addActor(btnBack);
        stage.addActor(btnBronze);
        stage.addActor(btnSilver);
        stage.addActor(btnGold);
        stage.addActor(btnDiamond);
        stage.addActor(btnMaster);
        stage.addActor(levels);

        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.setScreen(game.menuScreen);
            }
        });

        btnBronze.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                game.isCHALLENGE= false;
                game.setScreen(game.levelOne);
            }
        });

        btnSilver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                game.isCHALLENGE= false;
                game.setScreen(game.levelTwo);
            }
        });

        btnGold.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                game.isCHALLENGE= false;
                game.setScreen(game.levelThree);
            }
        });

        btnDiamond.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.isCHALLENGE= false;
                game.setScreen(game.levelFour);
            }
        });

        btnMaster.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.isCHALLENGE= false;
                game.setScreen(game.levelFive);
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

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
        //batch.draw(textBackground,0,0);
        //font.draw(batch,"Levels",Gdx.graphics.getWidth()/2.5f,Gdx.graphics.getWidth()/1.5f);
        batch.end();


        stage.act();
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
