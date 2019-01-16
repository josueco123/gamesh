package com.suecogames;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.suecogames.MainGame;

import java.util.Locale;

public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, AdHandler {

	RewardedVideoAd mAd;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			System.out.println("what: " + msg.what);
				if(mAd.isLoaded()){
					mAd.show();
				}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGame(this), config);

		MobileAds.initialize(this,"ca-app-pub-5740875488960578/2350582979");
		mAd = MobileAds.getRewardedVideoAdInstance(this);
		mAd.setRewardedVideoAdListener(this);

		mAd.loadAd("ca-app-pub-5740875488960578/2350582979", new AdRequest.Builder().build());

	}

	@Override
	public void onRewardedVideoAdLoaded() {
		System.out.println("an ads as loaded\n");
	}

	@Override
	public void onRewardedVideoAdOpened() {
		System.out.println("an ads as opened\n");
	}

	@Override
	public void onRewardedVideoStarted() {
		System.out.println("an ads as started\n");
	}

	@Override
	public void onRewardedVideoAdClosed() {
		System.out.println("an ads as closed\n");
		//mAd.loadAd("ca-app-pub-5740875488960578/3307290700", new AdRequest.Builder().build());
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		System.out.println("you recived "+ rewardItem.getAmount() +rewardItem.getType());
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
		System.out.println("an ads as cause focus to leave\n");
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		System.out.println("an ads as falid to load \n");
	}

	@Override
	public void onRewardedVideoCompleted() {

	}

	@Override
	public void showAds (boolean show){
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
