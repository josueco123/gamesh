package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.suecogames.AssetsManager;
import com.suecogames.BaseScreen;
import com.suecogames.MainGame;

public class SettingScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private Label settingsLabel;
    private AssetsManager manager;

    private Texture textBackground;
    private SpriteBatch batch;

    private TextButton btnBack;



    public SettingScreen(final MainGame game) {
        super(game);

        stage = new Stage(game.screenPort);

        manager = new AssetsManager();

        skin = manager.getSkin();
        batch = new SpriteBatch();
        textBackground = new Texture("corona_up.png");

        I18NBundle bundle = game.bundle;

        settingsLabel = new Label(bundle.get("mainmenu.settings"),skin);
        int middleWidth = Gdx.graphics.getWidth()/2;
        int divide = ((Gdx.graphics.getHeight()/2)/5)+15;

        btnBack = new TextButton(bundle.get("back"),skin);


        btnBack.setSize(150,60);
        btnBack.setPosition(middleWidth/6,divide);

        settingsLabel.setPosition(Gdx.graphics.getWidth()/2.5f,divide*7);

        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);
                game.setScreen(game.menuScreen);

                game.preferences.flush();
            }
        });

        stage.addActor(settingsLabel);
        stage.addActor(btnBack);

        //Music Group
        ButtonGroup musicGroup = new ButtonGroup();
        Label labelMusic = new Label("Music",skin);
        labelMusic.setPosition(middleWidth/3f,divide*3);
        final CheckBox musicOnBox = new CheckBox("On",skin);
        musicOnBox.setChecked(true);
        musicOnBox.setPosition(middleWidth*1.5f,divide*3);
        final CheckBox musicOffBox = new CheckBox("Off",skin);
        musicOffBox.setChecked(false);

        musicOffBox.setPosition(middleWidth,divide*3);

        musicGroup.add(musicOnBox,musicOffBox);

        musicOnBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                if(musicOnBox.isChecked()){
                    game.bgMusic.play();
                    game.MUSIC = true;
                }
            }
        });

        musicOffBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                if(musicOffBox.isChecked()){
                    game.bgMusic.stop();
                    game.MUSIC = false;
                    game.preferences.putBoolean("Music",game.MUSIC);
                }
            }
        });

        stage.addActor(musicOnBox);
        stage.addActor(musicOffBox);
        stage.addActor(labelMusic);

        //Sounds Group
        ButtonGroup soundGroup = new ButtonGroup();
        Label labelSound = new Label("Sound",skin);
        labelSound.setPosition(middleWidth/3f,divide*4);
        final CheckBox soundOnBox = new CheckBox("On",skin);
        soundOnBox.setChecked(true);
        soundOnBox.setPosition(middleWidth*1.5f,divide*4);
        final CheckBox soundOffBox = new CheckBox("Off",skin);
        soundOffBox.setChecked(false);
        soundOffBox.setPosition(middleWidth,divide*4);

        soundGroup.add(soundOnBox,soundOffBox);

        soundOnBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play();

                if(soundOnBox.isChecked()){
                    game.menuSound.play();
                    game.SOUND = true;
                }
            }
        });

        soundOffBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                if(soundOffBox.isChecked()){
                    game.menuSound.stop();
                    game.SOUND = false;

                }
            }
        });

        stage.addActor(soundOnBox);
        stage.addActor(soundOffBox);
        stage.addActor(labelSound);

        //vibrate Group
        ButtonGroup vibrateGroup = new ButtonGroup();
        Label labelVibrate = new Label("Vibrate",skin);
        labelVibrate.setPosition(middleWidth/3f,divide*5);
        final CheckBox vibrateOnBox = new CheckBox("On",skin);
        vibrateOnBox.setChecked(true);
        vibrateOnBox.setPosition(middleWidth*1.5f,divide*5);
        final CheckBox vibrateOffBox = new CheckBox("Off",skin);
        vibrateOffBox.setChecked(false);
        vibrateOffBox.setPosition(middleWidth,divide*5);

        vibrateGroup.add(vibrateOnBox,vibrateOffBox);

        vibrateOnBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                if(vibrateOnBox.isChecked()){
                    game.VIBRATE = true;
                    Gdx.input.vibrate(200);
                }
            }
        });

        vibrateOffBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play(0.5f);

                if(vibrateOffBox.isChecked()){
                    game.VIBRATE=false;

                }
            }
        });

        stage.addActor(vibrateOnBox);
        stage.addActor(vibrateOffBox);
        stage.addActor(labelVibrate);

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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
