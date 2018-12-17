package com.suecogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsManager {

    private AssetManager manager;
    private TextureAtlas textureAtlas;
    private ParticleEffect particleGol1,particleGol2;
    private Skin skin;
    public static BitmapFont font, fuenteMarcador, fuenteMarcadorInvert;
    public static Texture items;
    public static TextureRegion pauseMenu;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public AssetsManager(){

        manager = new AssetManager();
        textureAtlas = new TextureAtlas("textures.txt");
        font = new BitmapFont(Gdx.files.internal("calibri.fnt"),Gdx.files.internal("calibri.png"),false);
        fuenteMarcador = new BitmapFont(Gdx.files.internal("lcd.fnt"),Gdx.files.internal("lcd.png"),false);
        fuenteMarcadorInvert = new BitmapFont(Gdx.files.internal("lcd.fnt"),Gdx.files.internal("lcd.png"),false);
        particleGol1 = new ParticleEffect();
        particleGol2 = new ParticleEffect();
        particleGol1.load(Gdx.files.internal("particles/particlegol1.par"),Gdx.files.internal("particles"));
        particleGol2.load(Gdx.files.internal("particles/particlegol2.par"),Gdx.files.internal("particles"));
        skin = new Skin(Gdx.files.internal("soldier/skin/star-soldier-ui.json"));


    }

    public void load(){

        items = loadTexture("data/items.png");

        manager.load("energyball.png",Texture.class);
        manager.load("laser.png",Texture.class);
        manager.load("propulsion_fire.png",Texture.class);
        manager.load("blank.png",Texture.class);
        manager.load("backgraund.png",Texture.class);
        manager.load("corona_rt.png",Texture.class);
        manager.load("corona_up.png",Texture.class);
        manager.load("redeclipse_bk.png",Texture.class);
        manager.load("orangebar.png",Texture.class);
        manager.load("shipblue.PNG",Texture.class);
        manager.load("ship3.png",Texture.class);
        manager.load("Songs/Explo1.ogg", Sound.class);
        manager.load("Songs/menusound.ogg", Sound.class);
        manager.load("Songs/shoot.ogg", Sound.class);
        manager.load("Songs/goal.ogg", Sound.class);
        manager.load("Songs/contact.ogg", Sound.class);
        manager.load("Songs/hockeySong.ogg", Music.class);

    }

    public Sprite cargarTextura(String textura){
        return textureAtlas.createSprite(textura);
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public AssetManager getManager() {
        return manager;
    }

    public ParticleEffect getParticleGol1() {
        return particleGol1;
    }

    public ParticleEffect getParticleGol2() {
        return particleGol2;
    }


    public Skin getSkin() {
        return skin;
    }
}
