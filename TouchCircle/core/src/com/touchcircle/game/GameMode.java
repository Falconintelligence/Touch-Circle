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
import com.badlogic.gdx.math.Circle;


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
	Circle circleBounds;
	
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

		// We choose the color of the first circle randomly
		randomColor = (int) (colorList.length * Math.random());
		//System.out.println("randomColor = " + randomColor);

		// We create the first circle
		circleBounds = new Circle();					// New bounds for the circle
		shapeRenderer = new ShapeRenderer();			// New shapeRenderer
		shapeRenderer.setColor(colorList[randomColor]);	// Color of the first circle

		// We fetch the size of the screen
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		//System.out.println("height : " + height + "  width : " + width);

		// Size of the circle
		radius = maxSize;
		// We assign a random value to the position of the circle inside the screen
		x = (int) (radius + ( (width - radius) * Math.random() ) );
		y = (int) (radius + ( (height - radius) * Math.random() ) );
		// We set the bounds of the circle and its center
		circleBounds.set(x, y, radius);
		//System.out.println("x : " + x + "  y : " + y);

		// We allow the screen to listen the finger of the user
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		// Color of the background Dark blue
		Gdx.gl.glClearColor(0.040f, 0.098f, 0.350f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// We display the circle
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(x, (height-y), radius);
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

		// We fetch the position of the finger
		touchCoordinateX = screenX;
		touchCoordinateY = screenY;
		//System.out.println("X coordinate : " + touchCoordinateX + " Y coordinate : " + touchCoordinateY);

		// If the finger touch the circle we create a new circle
		if (circleBounds.contains(touchCoordinateX, touchCoordinateY)) {
			// New size of the circle
			radius = (int) (minSize + (maxSize - minSize) * Math.random());
			// We assign a random value to the position of the new circle inside the screen
			x = (int) (radius + ((width - radius - edge) * Math.random()) );
			y = (int) (radius + ((height - radius - edge) * Math.random() ) );
			// New bounds of the circle and its center
			circleBounds.set(x, y, radius);
			//System.out.println("x : " + x + "  y : " + y);

			// We change the color of the circle
			randomColor = (int) (colorList.length * Math.random());
			shapeRenderer.setColor(colorList[randomColor]);
		}
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
