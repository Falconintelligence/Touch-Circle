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
	int touchCoordinateX, touchCoordinateY;
	int x,y;
	int radius;
	int edge = 200;
	int minSize = 50;
	int maxSize = 150;
	int randomColor;
	int touchBonus;
	boolean bonus = false;
	Color[] colorList, RdmColorList;
	Circle[] circleBounds;
	int[] Xcoordinates, Ycoordinates, Radius;
	
	@Override
	public void create () {

		colorList = new Color[]{
				Color.CYAN,
				Color.GREEN,
				Color.RED,
				Color.MAGENTA,
				Color.YELLOW
		};

		Xcoordinates = new int[5];
		Ycoordinates = new int[5];
		Radius = new int[5];
		RdmColorList = new Color[5];
		for (int j=0; j<RdmColorList.length; j++) RdmColorList[j]= new Color();
		circleBounds = new Circle[5];					// New bounds for the circle
		for (int i=0; i<circleBounds.length; i++) circleBounds[i]= new Circle();

		// We choose the color of the first circle randomly
		randomColor = (int) (colorList.length * Math.random());
		//System.out.println("randomColor = " + randomColor);

		// New renderer to display the elements on the screen
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
		circleBounds[0].set(x, y, radius);
		//System.out.println("x : " + x + "  y : " + y);

		// We allow the screen to listen the finger of the user
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		// Color of the background Dark blue
		Gdx.gl.glClearColor(0.040f, 0.098f, 0.350f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (bonus){
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			// We draw each circle with its color
			for (int i=0; i<Xcoordinates.length; i++) {
				shapeRenderer.setColor(RdmColorList[i]);
				shapeRenderer.circle(Xcoordinates[i], (height - Ycoordinates[i]), Radius[i]);
			}
			shapeRenderer.end();
		}
		else {
			// We display the circle
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.circle(x, (height - y), radius);
			shapeRenderer.end();

		}
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

		// We check if the player touched one of the circles displayed
		for (int k=0; k<circleBounds.length; k++) {
			// If the finger touch a circle we create a new circle
			if (circleBounds[k].contains(touchCoordinateX, touchCoordinateY)) {
				touchBonus++;
				// If the circle was a bonus (White circle)
				//if (shapeRenderer.getColor() == Color.WHITE){
				if (touchBonus == 11) {
					System.out.println("BONUS");
					bonus = true;
					// We display 5 others circles at the same time
					for (int i = 0; i < Xcoordinates.length; i++) {
						// We assign a random value to the position of each new circle inside the screen
						Xcoordinates[i] = (int) (radius + ((width - radius - edge) * Math.random()));
						Ycoordinates[i] = (int) (radius + ((height - radius - edge) * Math.random()));
						// New size for each circle
						Radius[i] = (int) (minSize + (maxSize - minSize) * Math.random());
						// New bounds for each new circle and its center
						circleBounds[i].set(Xcoordinates[i], Ycoordinates[i], Radius[i]);
						// New color for each circle
						randomColor = (int) (colorList.length * Math.random());
						RdmColorList[i] = colorList[randomColor];
					}
					touchBonus = 0;
				}
				// If the circle was not a bonus
				else {
					bonus = false;
					// New size of the circle
					radius = (int) (minSize + (maxSize - minSize) * Math.random());
					// We assign a random value to the position of the new circle inside the screen
					x = (int) (radius + ((width - radius - edge) * Math.random()));
					y = (int) (radius + ((height - radius - edge) * Math.random()));
					// New bounds of the circle and its center
					circleBounds[0].set(x, y, radius);
					//System.out.println("x : " + x + "  y : " + y);

					// We change the color of the circle
					// If the player touched 12 times a circle, we display a Bonus (white circle)
					if (touchBonus == 10) {
						shapeRenderer.setColor(Color.WHITE);
					}
					// else we display a circle with one color of the list
					else {
						randomColor = (int) (colorList.length * Math.random());
						shapeRenderer.setColor(colorList[randomColor]);
					}
					System.out.println("RANDOM");
				}
			}
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
