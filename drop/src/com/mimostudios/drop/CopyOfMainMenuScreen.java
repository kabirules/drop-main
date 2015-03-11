package com.mimostudios.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class CopyOfMainMenuScreen implements Screen {

  final Drop game;

	OrthographicCamera camera;
	Texture backgroundImage;
	Rectangle background;

	public CopyOfMainMenuScreen(final Drop gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.w, game.h);
		
		//create the texture of the background
		backgroundImage = new Texture(Gdx.files.internal("data/sand-background.png"));
		
		// create a Rectangle to logically represent the background
		background = new Rectangle();
		background.x = 0;
		background.y = 0;
		background.width =  game.w;
		background.height =  game.h;		

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(backgroundImage, background.x, background.y);
		game.fontBig.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
		game.fontBig.draw(game.batch, "Tap anywhere to begin!", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
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
	}
}