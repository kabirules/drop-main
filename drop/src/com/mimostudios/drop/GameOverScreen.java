package com.mimostudios.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class GameOverScreen implements Screen {

	  final Drop game;

		OrthographicCamera camera;
		Texture backgroundImage;
		Rectangle background;
		Stage stage;
		SpriteBatch batch;
	
  
	public GameOverScreen(final Drop gam) {
		game = gam;
		
		batch = new SpriteBatch();
		stage = new Stage(new StretchViewport(game.w, game.h));
		Gdx.input.setInputProcessor(stage);

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		table.align(Align.top);
		table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/sand-background.png")))));
		stage.addActor(table);
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final Label intro1 = new Label("\n\nGame Over!!!\n", game.skin, "big");
		final Label intro2 = new Label("\nYour score: " + game.score + "\n\n\n", game.skin, "big");
		final Label lf = new Label("\n\n\n", game.skin);
		final TextButton buttonPlay = new TextButton("  Try again  ", game.skin);
		final TextButton buttonOtherApps = new TextButton("  Other apps  ", game.skin);
		final TextButton buttonShare = new TextButton("  Share  ", game.skin);
		table.add(intro1);
		table.row();
		table.add(intro2);
		table.row();
		table.add(buttonPlay);
		table.add(lf);
		table.row();
		table.add(buttonOtherApps);
		table.add(lf);
		table.row();
		table.add(buttonShare);
		
		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		buttonPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (game.numberOfGames%3==0 && game.startAppAds!=null) {
					game.startAppAds.showInterstitial();
				} else {
					game.setScreen(new GameScreen(game));	
				}
				game.numberOfGames++;
				//dispose();
			}
		});
		
		buttonOtherApps.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.shareInterface.openOtherApp();
			}
		});		
		
		buttonShare.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.shareInterface.shareScore("Drop - Raining in the desert", "I scored " + game.score +" on Drop. Try to beat me! " + 
						"https://play.google.com/store/apps/details?id=com.mimostudios.drop");
			}
		});		
	}
	

	@Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
    }

    @Override
	public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
	}
 
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
 
	@Override
	public void hide() {
	}
 
	@Override
	public void pause() {
	}
 
	@Override
	public void resume() {
	}
 
	@Override
	public void dispose() {
		stage.dispose();
	}
}
