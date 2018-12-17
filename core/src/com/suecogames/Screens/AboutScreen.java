package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.suecogames.AssetsManager;
import com.suecogames.BaseScreen;
import com.suecogames.MainGame;

public class AboutScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private Label settingsLabel;
    private AssetsManager manager;

    private Texture textBackground;
    private SpriteBatch batch;
    private BitmapFont font;

    private TextButton btnBack;

    float divide,middleWidth;



    public AboutScreen(final MainGame game) {
        super(game);

        stage = new Stage(game.screenPort);

        manager = new AssetsManager();

        skin = manager.getSkin();
        batch = new SpriteBatch();
        textBackground = new Texture("corona_up.png");

        font = new BitmapFont();

        I18NBundle bundle = game.bundle;

        settingsLabel = new Label(bundle.get("mainmenu.about"),skin);
        middleWidth = Gdx.graphics.getWidth()/3;
        divide = ((Gdx.graphics.getHeight()/2)/5)+15;

        btnBack = new TextButton(bundle.get("back"),skin);

        btnBack.setSize(150,60);
        btnBack.setPosition(middleWidth/6,divide);

        settingsLabel.setPosition(Gdx.graphics.getWidth()/2.5f,divide*7);

        stage.addActor(settingsLabel);
        stage.addActor(btnBack);

        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play();

                game.setScreen(game.menuScreen);
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
        font.draw(batch,"Create by: suecoapp - Josue Hurtado",middleWidth/2,divide*6);
        font.draw(batch,"skin Created by Raymond-Raeleus-Buckley CC 4.0",middleWidth/2,divide*5);
        font.draw(batch,"Textures from opengameart.org created by: ",middleWidth/2,divide*4.5f);
        font.draw(batch,"skybox made by Ulukai CC 3.0",middleWidth/2,divide*4);
        font.draw(batch,"Skorpio, thomaswp,Master484 Carlos Alface CC-BY-SA 3.0",middleWidth/2,divide*3.5f);
        font.draw(batch,"Contact: https://spacehockey.blogspot.com",middleWidth/2,divide*2.5f);
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
