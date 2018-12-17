package com.suecogames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.suecogames.AssetsManager;
import com.suecogames.BaseScreen;
import com.suecogames.MainGame;

public class LoadScreen extends BaseScreen {

    private Stage stage;

    /**
     * Creamos un objeto tipo skin para obtener los objetos json.
     */
    private Skin skin;

    /**
     * Estas son las letras que aparecerán cuando el juego esté cargando.
     */
    private Label loading;
    //Creamos un int para la altura y anchura
    private int height;
    private int width;

    private AssetsManager manager;

    //Creamos una imagen para mostrar el logo
    private Image logo;

    public LoadScreen(MainGame game) {
        super(game);

        manager = new AssetsManager();

        // Establecemos el tamaño del Stage
        stage = new Stage(game.screenPort);
        skin = manager.getSkin();

        height = game.screenPort.getScreenHeight();
        width = game.screenPort.getScreenWidth();

        // Create some loading text using this skin file and position it on screen.
        loading = new Label("Cargando...", skin);
        loading.setPosition((height / 2) - loading.getWidth() + 5 / 2, (width / 2) - loading.getHeight() / 2);
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // This is important. To load an asset from the asset manager you call update() method.
        // this method will return true if it has finished loading. Else it will return false.
        // You usually want to do the code that changes to the main menu screen if it has finished
        // loading, else you update the screen to not make the user angry and keep loading.
        if (game.getManager().update()) {
            // I'll notify the game that all the assets are loaded so that it can load the
            // remaining set of screens and enter the main menu. This avoids Exceptions because
            // screens cannot be loaded until all the assets are loaded.
            game.finishLoading();

        } else {
            // getProgress() returns the progress of the load in a range of [0,1]. We multiply
            // this progress per * 100 so that we can display it as a percentage.
            int progress = (int) (game.getManager().getProgress() * 100);
            loading.setText("Cargando... " + progress + "%");
        }

        stage.act();
        stage.draw();
    }
}
