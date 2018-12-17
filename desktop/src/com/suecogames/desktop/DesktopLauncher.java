package com.suecogames.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.suecogames.AdHandler;
import com.suecogames.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		AdHandler ad = new AdHandler() {
			@Override
			public void showAds(boolean show) {

			}
		};
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(ad), config);
	}
}
