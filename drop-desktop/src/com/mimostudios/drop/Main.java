package com.mimostudios.drop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mimostudios.drop.Drop;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Drop";
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new Drop(), cfg);
	}
}
