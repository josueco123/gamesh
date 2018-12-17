package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.suecogames.AssetsManager;
import com.suecogames.BaseScreen;
import com.suecogames.MainGame;

public class MenuScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private OnePlayerScreen onePlayerScreen;

    private TextButton oneplayerBtn,twoplayersBtn, settingBtn,aboutBtn, challengeBtn;

    private AssetsManager manager;

    private Texture textBackground;
    private SpriteBatch batch;

    Dialog dialog;


    public MenuScreen(final MainGame game) {
        super(game);

        manager = new AssetsManager();
        onePlayerScreen = new OnePlayerScreen(game);

        stage = new Stage(game.screenPort);

        skin = manager.getSkin();

        I18NBundle bundle = game.bundle;

        batch = new SpriteBatch();
        textBackground = new Texture("corona_rt.png");

        oneplayerBtn = new TextButton(bundle.get("mainmenu.onePlayer"),skin);

        twoplayersBtn = new TextButton(bundle.get("mainmenu.twoPlayers"),skin);

        settingBtn = new TextButton(bundle.get("mainmenu.settings"),skin);

        aboutBtn = new TextButton(bundle.get("mainmenu.about"), skin);

        challengeBtn = new TextButton(bundle.get("mainmenu.challenge"),skin);

        dialog = new Dialog("", manager.getSkin()){
            public void result(Object obj) {
                System.out.println("objeto: "+obj);
                if(obj.equals(true))
                    Gdx.app.exit();
            }
        };

        dialog.text("Do you really want to leave?");
        dialog.button("Yes", true);
        dialog.button("No", false);

        challengeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                game.setScreen(game.challengeScreen);
            }
        });

        oneplayerBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                game.setScreen(onePlayerScreen);
            }
        });

        twoplayersBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.setScreen(game.twoPlayers);
            }
        });

        settingBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.setScreen(game.settingScreen);
            }
        });

        aboutBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.setScreen(game.aboutScreen);
            }
        });

        int middleWidth = Gdx.graphics.getWidth()/3;

        int divide = ((Gdx.graphics.getHeight()/3)/4)+15;


        challengeBtn.setSize(300,100);
        oneplayerBtn.setSize(300,100);
        twoplayersBtn.setSize(300,100);
        aboutBtn.setSize(300,100);
        settingBtn.setSize(300,100);

        challengeBtn.setPosition(middleWidth,divide *5);
        oneplayerBtn.setPosition(middleWidth,divide *4);
        twoplayersBtn.setPosition(middleWidth,divide*3);
        settingBtn.setPosition(middleWidth,divide*2);
        aboutBtn.setPosition(middleWidth,divide);

        stage.addActor(oneplayerBtn);
        stage.addActor(twoplayersBtn);
        stage.addActor(settingBtn);
        stage.addActor(aboutBtn);
        stage.addActor(challengeBtn);

        game.bgMusic = game.getManager().get("Songs/hockeySong.ogg");
        game.menuSound = game.getManager().get("Songs/menusound.ogg");
        game.contacSound = game.getManager().get("Songs/contact.ogg");
        game.explodeSound = game.getManager().get("Songs/Explo1.ogg");
        game.goalSound = game.getManager().get("Songs/goal.ogg");
        game.shootSound = game.getManager().get("Songs/shoot.ogg");


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        if(game.MUSIC){
            game.bgMusic.setVolume(0.60f);
            game.bgMusic.play();
        }
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
        batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK))
            dialog.show(stage);


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
