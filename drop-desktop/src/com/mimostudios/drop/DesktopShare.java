package com.mimostudios.drop;

import com.badlogic.gdx.Gdx;
import com.mimostudios.drop.ShareInterface;

public class DesktopShare implements ShareInterface {
	
   public void shareScore(String title, String message) {
	      Gdx.app.log("DesktopShare", "title: " + title + "  message: " + message);
	   }


   public void openOtherApp() {
	      Gdx.app.log("DesktopShare","openOtherApp");
   }
}
