package com.touchcircle.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class GameMode extends ApplicationAdapter implements InputProcessor {
	ShapeRenderer shapeRenderer;
	int height;
	int width;
	int touchCoordinateX;
	int touchCoordinateY;
	int x,y;
	int radius;
	int edge = 200;
	int minSize = 50;
	int maxSize = 150;
	int randomColor;
	Color[] colorList;
	
	@Override
	public void create () {

		colorList = new Color[]{
				Color.CYAN,
				Color.GREEN,
				Color.RED,
				Color.WHITE,
				Color.YELLOW,
				Color.MAGENTA
		};

		randomColor = (int) (colorList.length * Math.random());
		System.out.println("randomColor = " + randomColor);

		// We create the first circle
		//Circle FirstCircle = new Circle();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(colorList[randomColor]);

		// We fetch the size of the screen
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		//System.out.println("height : " + height + "  width : " + width);

		// Size of the circle
		radius = maxSize;
		// We assign a random value to the position of the circle inside the screen
		x = (int) (radius + ( (width - radius) * Math.random() ) );
		y = (int) (radius + ( (height - radius) * Math.random() ) );
		System.out.println("x : " + x + "  y : " + y);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		// Color of the background Dark blue
		Gdx.gl.glClearColor(0.040f, 0.098f, 0.350f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// We display the circle
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
	}

	// When the user touch the screen
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	// When the user remove his finger of the screen
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchCoordinateX = screenX;
		touchCoordinateY = screenY;
		System.out.println("X coordinate : " + touchCoordinateX + " Y coordinate : " + touchCoordinateY);

		radius = (int) (minSize + (maxSize - minSize) * Math.random());
		// We assign a random value to the position of the circle inside the screen
		x = (int) (radius + ( (width - radius - edge) * Math.random() ) );
		y = (int) (radius + ( (height - radius - edge) * Math.random() ) );

		// We change the color of the circle
		randomColor = (int) (colorList.length * Math.random());
		shapeRenderer.setColor(colorList[randomColor]);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
