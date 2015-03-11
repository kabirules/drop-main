package com.mimostudios.drop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
  final Drop game;

	Texture dropImage;
	Texture bucketImage;
	Texture backgroundImage;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Rectangle background;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;
	int dropsMissed;
	float speed=200; //the speed droplets are falling
	float hesitate=1000000000;// the pause between droplets spawning

	public GameScreen(final Drop gam) {
		this.game = gam;

		// load the images for the droplet and the bucket, 64x64 pixels each, plus the game background
		dropImage = new Texture(Gdx.files.internal("data/droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("data/bucket.png"));
		backgroundImage = new Texture(Gdx.files.internal("data/desert.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("data/drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("data/rain.mp3"));
		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.w, game.h);

		// create a Rectangle to logically represent the background
		background = new Rectangle();
		background.x = 0;
		background.y = 0;
		background.width = game.w;
		background.height = game.h;
		
		// create a Rectangle to logically represent the background
		bucket = new Rectangle();
		bucket.x = game.w / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 120; // bottom left corner of the bucket is 120 pixels above the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;		
		
		

		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, game.w - 64);
		raindrop.y = game.h;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
		
		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.batch.draw(backgroundImage, background.x, background.y);
		game.fontSmall.draw(game.batch, "Drops Collected: " + dropsGathered, 10, 700);
		game.fontSmall.draw(game.batch, "Drops Missed: " + dropsMissed + "/3", game.w-380, 700);
		//game.font16.draw(game.batch, "speed: " + speed, 0, 600);
		//game.font16.draw(game.batch, "hesitate: " + hesitate, 1280-150, 600);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > game.w - 64)
			bucket.x = game.w - 64;

		// check if we need to create a new raindrop
		//the time between droplets spawning will get shorter every time (until 400000000 ns)
		hesitate = hesitate>400000000? hesitate - 1000000 : hesitate; 
		if (TimeUtils.nanoTime() - lastDropTime > hesitate)
			spawnRaindrop();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			//the speed droplets fall will get faster every time (until 350)
			speed = speed<350?speed + (float)0.05:speed;
			Rectangle raindrop = iter.next();
			raindrop.y -= speed * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				dropsMissed++;
				iter.remove();
				Gdx.input.vibrate(200);
			}
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
			if (dropsMissed>3) {
				game.setScreen(new GameOverScreen(game));
				Gdx.input.vibrate(1000);
				game.score = dropsGathered;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
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
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
	
	public int getDropsGathered() {
		return dropsGathered;
	}	

}
