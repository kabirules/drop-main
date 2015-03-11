package com.mimostudios.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mimostudios.drop.ShareInterface;

public class Drop extends Game {

  SpriteBatch batch;
  BitmapFont fontSmall;
  BitmapFont fontBig;
  int score;
  int numberOfGames=1; //games played
  final StartAppAds startAppAds; //interface for startapp integration
  final ShareInterface shareInterface; //interface for startapp integration
  int w = 1280; //screen width
  int h = 720; //screen height
  Skin skin;

	public Drop() {
		this.startAppAds = null;
		this.shareInterface = null;
	}

	public Drop(StartAppAds startAppAds, ShareInterface shareInterface) {
		this.startAppAds = startAppAds;
		this.shareInterface = shareInterface;
	}	

	/**
	 * Creates the font we'll use in the whole game, 
	 * and call the Main Menu Screen
	 */
	public void create() {
		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/MISTV___.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 32;
		fontSmall = generator.generateFont(parameter);
		fontSmall.setColor(Color.BLACK);
		parameter.size = 48;
		fontBig = generator.generateFont(parameter);
		fontBig.setColor(Color.BLACK);
		generator.dispose();
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		// Store the default libgdx font under the name "default".
		skin.add("default", this.fontSmall);
		skin.add("big", this.fontBig);		
		
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("big");
		skin.add("default", textButtonStyle);
		
		//Configure a LabelStyle and name it "default".
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		labelStyle.fontColor = Color.BLACK;
		skin.add("default", labelStyle);
		//Configure a LabelStyle and name it "big".
		LabelStyle labelStyleBig = new LabelStyle();
		labelStyleBig.font = skin.getFont("big");
		labelStyleBig.fontColor = Color.BLACK;
		skin.add("big", labelStyleBig);		
		
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		fontSmall.dispose();
		fontBig.dispose();
	}
}
