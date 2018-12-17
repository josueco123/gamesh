package com.suecogames;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.*;
import com.suecogames.Levels.*;
import com.suecogames.Screens.*;

public class MainGame extends Game implements ApplicationListener {

	public static int WIDTH;
	public static int HEIGHT;

	public I18NBundle bundle;
	public boolean VIBRATE,MUSIC,SOUND,isCHALLENGE;
	public int challengeLEVEL;

	private AssetsManager manager;

	public MenuScreen menuScreen;

	public Viewport screenPort;
	OrthographicCamera camera;

	public LevelOne levelOne;
	public TwoPlayers twoPlayers;
	public LevelOneHalf levelOneHalf;
	public LevelTwoHalf levelTwoHalf;
	public LevelTwo levelTwo;
	public LevelThree levelThree;
	public LevelThreeQuarters levelThreeQuarters;
	public LevelThreeHalf levelThreeHalf;
	public LevelFour levelFour;
	public LevelFourHalf levelFourHalf;
	public LevelFourQuarters levelFourQuarters;
	public LevelFive levelFive;

	public SettingScreen settingScreen;
	public AboutScreen aboutScreen;
	public ChallengeScreen challengeScreen;

	public Music bgMusic;
	public Sound menuSound,contacSound,explodeSound,goalSound,shootSound;
	public Preferences preferences;
	public AdHandler adHandler;

	public MainGame(AdHandler handler){
		this.adHandler = handler;
	}


	@Override
	public void create() {

		bundle = I18NBundle.createBundle(Gdx.files.internal("locale/locale"));
		manager = new AssetsManager();
		manager.getManager().update();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		preferences = Gdx.app.getPreferences("My Preferences");

		float gameWidth = 203;
		float gameHeight = HEIGHT / (WIDTH / gameWidth);

		camera = new OrthographicCamera(gameWidth / 10, gameHeight / 10);
		//screenPort = new ScreenViewport();
		screenPort = new StretchViewport(WIDTH,HEIGHT);
		//screenPort = new FitViewport(HEIGHT,WIDTH,camera);
		screenPort.apply();


		VIBRATE = true;
		SOUND = true;
		MUSIC = true;

		isCHALLENGE = false;
		challengeLEVEL = preferences.getInteger("level");

		if(challengeLEVEL == 0)
			challengeLEVEL++;

		manager.load();
		setScreen(new LoadScreen(this));

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public AssetManager getManager() {
		return manager.getManager();
	}

	public void finishLoading() {
		twoPlayers = new TwoPlayers(this);
		menuScreen = new MenuScreen(this);
		levelOne = new LevelOne(this);
		levelOneHalf = new LevelOneHalf(this);
		levelTwo = new LevelTwo(this);
		levelTwoHalf = new LevelTwoHalf(this);
		levelThree = new LevelThree(this);
		levelThreeHalf = new LevelThreeHalf(this);
		levelThreeQuarters = new LevelThreeQuarters(this);
		levelFour = new LevelFour(this);
		levelFourHalf = new LevelFourHalf(this);
		levelFourQuarters = new LevelFourQuarters(this);
		levelFive = new LevelFive(this);
		settingScreen = new SettingScreen(this);
		aboutScreen = new AboutScreen(this);
		challengeScreen = new ChallengeScreen(this);
		setScreen(menuScreen);
	}


}
