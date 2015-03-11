package com.mimostudios.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class CopyOfGameOverScreen extends ScreenAdapter {

	final Drop game;

	OrthographicCamera camera;
    Texture playagainImage;
    Rectangle gameover, playagain;
    Vector3 touchPoint;
    Texture backgroundImage;
    Rectangle background;
    //ShapeRenderer sr;
	
	class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }
    
	public CopyOfGameOverScreen(final Drop gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.w, game.h);
		
		backgroundImage = new Texture(Gdx.files.internal("data/sand-background.png"));
		// create a Rectangle to logically represent the background
		background = new Rectangle();
		background.x = 0;
		background.y = 0;
		background.width = game.w;
		background.height = game.h;		

		playagain = new Rectangle((game.w / 2) - (256 / 2), 240-50, 320, 64);
		//sr = new ShapeRenderer();
		
		touchPoint = new Vector3();
	}
	

	public void update() {
		if (Gdx.input.justTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			//System.out.println("x: " + touchPoint.x + " y: " + touchPoint.y);
			if (playagain.contains(touchPoint.x, touchPoint.y)) {
				//System.out.println("OK!");
				if (game.numberOfGames%5==0 && game.startAppAds!=null) {
					game.startAppAds.showInterstitial();
				}
				game.numberOfGames++;
				game.setScreen(new GameScreen(game));
			}
		}		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
		game.batch.draw(backgroundImage, background.x, background.y);
		game.fontBig.draw(game.batch, "Game Over!!!", game.w / 2 - 325 / 2,600);
		game.fontBig.draw(game.batch, "Your score: " + game.score, game.w / 2 - 325 / 2,420);
		game.fontBig.draw(game.batch, "Play Again", (game.w / 2) - (256 / 2), 240);		
		//game.fontBig.draw(game.batch, "Games played: " + game.numberOfGames, 1280 / 2 - 325 / 2,140);
		
		game.batch.end();
		/*
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		sr.rect((1280 / 2) - (256 / 2), 240-50, 320, 64);
		sr.end();
		*/
		update();
		dispose();

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