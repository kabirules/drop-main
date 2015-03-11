package com.mimostudios.drop;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mimostudios.drop.Drop;
import com.mimostudios.utilities.Share;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class MainActivity extends AndroidApplication {
	
    private StartAppAd startAppAd = new StartAppAd(this);
    private Share share = new Share(this);
    private boolean firstTime = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        StartAppSDK.init(this, "109125082", "205215263", false);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;        
        
        initialize(new Drop(new AndroidStartAppAds(startAppAd), new AndroidShare(share)), cfg);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }
    
    @Override
    public void onBackPressed() {
    	if (firstTime) {
    		startAppAd.onBackPressed();
    		firstTime=false;
    	} else {
    		super.onBackPressed();
    	}
    }
    
}