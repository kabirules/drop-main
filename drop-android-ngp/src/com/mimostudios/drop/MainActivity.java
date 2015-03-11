package com.mimostudios.drop;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.foffptyqz.cdponwtvc102565.MA;
import com.mimostudios.drop.Drop;
import com.nqiacyviyqlpivoc.AdController;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class MainActivity extends AndroidApplication {
	
	private static final String MY_LEADBOLT_SECTION_ID_APP_AD = "839502096";
	private static final String MY_LEADBOLT_SECTION_ID_BANNER_AD = "409060385";
	private static final String MY_LEADBOLT_SECTION_AUDIO_AD = "970344224";
	private static final String MY_LEADBOLT_REENGAGEMENT = "540495798";
	
	private AdController ad;
	private AdController adBanner;
	
	private MA ma; //Declare here
	
    //private StartAppAd startAppAd = new StartAppAd(this);	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //StartAppSDK.init(this, "109125082", "205215263", false);        
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        
        //Leadbolt
        adBanner = new AdController(this, MY_LEADBOLT_SECTION_ID_BANNER_AD);
        adBanner.loadAd();        
        ad = new AdController(this, MY_LEADBOLT_SECTION_ID_APP_AD);
        ad.loadStartAd(MY_LEADBOLT_SECTION_AUDIO_AD, MY_LEADBOLT_REENGAGEMENT);
        
        //Airpush
        ma=new MA(this, null, false);        
        
        initialize(new Drop(), cfg);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        //startAppAd.onResume();
    }
    
    @Override
    public void onBackPressed() {
        //startAppAd.onBackPressed();
        super.onBackPressed();
    }    
}