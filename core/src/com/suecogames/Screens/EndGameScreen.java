package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.suecogames.*;

public class EndGameScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private SpriteBatch batch;

    private BitmapFont font;

    private AssetsManager manager;

    private TextButton btnBack,btnAd, btnContinue;

    private Texture textBackground;

    private Label labelResult,labelScore,labelLevel,labelAd;

    private String score,result;

    boolean toggle;

    I18NBundle bundle;


    public EndGameScreen(final MainGame game,boolean isWinner, String score) {
        super(game);

        this.score = score;

        manager = new AssetsManager();

        stage = new Stage(game.screenPort);

        skin = manager.getSkin();

        batch = new SpriteBatch();
        font = new BitmapFont();

        bundle = game.bundle;

        btnBack = new TextButton(bundle.get("back"),skin);
        btnContinue = new TextButton(bundle.get("continue"),skin);
        btnAd = new TextButton(bundle.get("ad"),skin);

        textBackground = new Texture("redeclipse_bk.png");

        if(isWinner){
            this.result = bundle.get("win");
        }else{
            this.result = bundle.get("lose");
        }

        labelResult = new Label(result,skin);
        labelScore = new Label(score,skin);
        labelAd = new Label(bundle.get("help"),skin);

        String level = game.challengeScreen.yourLevel();
        labelLevel = new Label(level,skin);

        float middleWidth = Gdx.graphics.getWidth()/3;
        float divide = ((Gdx.graphics.getHeight()/1.5f)/2)+5;

        btnBack.setSize(200,80);
        btnBack.setVisible(false);
        btnContinue.setSize(200,80);
        btnContinue.setVisible(false);
        btnAd.setSize(200,80);

        btnBack.setPosition(middleWidth/4.2f,divide/4);
        btnAd.setPosition(middleWidth*1.8f,divide/4);
        btnContinue.setPosition(middleWidth,divide/4);
        labelResult.setPosition(middleWidth*1.3f,divide *2);
        labelScore.setPosition(middleWidth*1.4f,divide*1.7f);
        labelLevel.setPosition(middleWidth*1.2f,divide*1.2f);
        labelAd.setPosition(middleWidth*0.5f,divide*0.9f);

        stage.addActor(btnBack);
        stage.addActor(btnAd);
        stage.addActor(btnContinue);
        stage.addActor(labelResult);
        stage.addActor(labelScore);
        stage.addActor(labelAd);

        game.preferences.putInteger("level",game.challengeLEVEL);
        game.preferences.flush();

        if(game.isCHALLENGE)
        stage.addActor(labelLevel);

        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.SOUND)
                    game.menuSound.play();

                game.setScreen(game.menuScreen);
            }
        });

        btnAd.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btnBack.setVisible(true);
                if(game.isCHALLENGE)
                    btnContinue.setVisible(true);

                game.adHandler.showAds(toggle);
                toggle = !toggle;
                //System.out.println("excution: " + toggle);
            }
        });

        btnContinue.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("level: "+game.challengeLEVEL);
                game.challengeScreen.nextLevel();
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
        //batch.draw(textBackground,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(textBackground,0,0,game.screenPort.getScreenWidth(),game.screenPort.getScreenHeight());
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
