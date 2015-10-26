package com.touchcircle.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;


public class GameMode extends ApplicationAdapter implements InputProcessor {
	ShapeRenderer shapeRenderer;
	int height;
	int width;
	int touchCoordinateX, touchCoordinateY;
	int edge = 200;
	int minSize = 50;
	int maxSize = 150;
	int randomColor;
	int CircleNumber = 5;
	int touchBonus = 0, touch = 0;
	boolean bonus = false;
	boolean whiteBonus = false;
	Color[] colorList, RdmColorList;
	Circle[] circleBounds;
	int[] Xcoordinates, Ycoordinates, Radius;
	Sound pop;
	BitmapFont shadow, font;
	SpriteBatch batch;
	int score = 0;
	long startTime;
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		startTime = TimeUtils.millis();

		colorList = new Color[]{
				Color.CYAN,
				Color.GREEN,
				Color.RED,
				Color.MAGENTA,
				Color.YELLOW
		};
		// We load our sound effect
		pop = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		//shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));

		Xcoordinates = new int[CircleNumber];
		Ycoordinates = new int[CircleNumber];
		Radius = new int[CircleNumber];
		RdmColorList = new Color[CircleNumber];
		for (int j=0; j<RdmColorList.length; j++) RdmColorList[j]= new Color();
		circleBounds = new Circle[CircleNumber];					// New bounds for the circle
		for (int i=0; i<circleBounds.length; i++) circleBounds[i]= new Circle();

		// We choose the color of the first circle randomly
		randomColor = (int) (colorList.length * Math.random());

		// New renderer to display the elements on the screen
		shapeRenderer = new ShapeRenderer();			// New shapeRenderer
		RdmColorList[0] = colorList[randomColor];		// Color of the first circle

		// We fetch the size of the screen
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		System.out.println("height : " + height + "  width : " + width);

		// Size of the first circle
		Radius[0] = maxSize;
		// We assign a random value to the position of the circle inside the screen
		Xcoordinates[0] = (int) (minSize + edge + ( (width - maxSize - minSize - 2*edge + 100) * Math.random() ) );
		Ycoordinates[0] = (int) (minSize + edge + ( (height - maxSize - minSize - 2*edge + 100) * Math.random() ) );
		// We set the bounds of the circle and its center
		circleBounds[0].set(Xcoordinates[0], Ycoordinates[0], Radius[0]);
		System.out.println("x : " + Xcoordinates[0] + "  y : " + Ycoordinates[0]);

		// We allow the screen to listen the finger of the user
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		// Color of the background Dark blue
		//Gdx.gl.glClearColor(0.040f, 0.098f, 0.350f, 1);
		// Color of the background dark
		Gdx.gl.glClearColor(0.07f, 0.07f, 0.07f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// We fetch the time since the user start the game
		int time = (int) (TimeUtils.timeSinceMillis(startTime) / 1000);

		batch.begin();
		font.setColor(Color.ORANGE);
		font.getData().setScale(1.15f, 1.15f);
		// We choose a color for the text
		font.setColor(Color.ORANGE);
		// We display the score
		font.draw(batch, "Score: " + score, 50, (height - 25));
		// We display the time with this format 0:00 and we stop at 1:00 minute
		if (time < 60 )
			if (time < 10)
				font.draw(batch, "0:0"+String.valueOf(time) ,(width - 200), (height - 25));
			else font.draw(batch, "0:"+String.valueOf(time) ,(width - 200), (height - 25));
		else {
			font.draw(batch, "1:00", (width - 200), (height - 25));
			font.draw(batch, "Score: " + score, 50, (height - 25));
		}
		batch.end();

		if (time < 60) {

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			// We draw each circle with its color
			for (int i = 0; i < Xcoordinates.length; i++) {
				shapeRenderer.setColor(RdmColorList[i]);
				shapeRenderer.circle(Xcoordinates[i], (height - Ycoordinates[i]), Radius[i]);
			}
			shapeRenderer.end();

			// If it is a white circle, we add a outline
			if (whiteBonus) {
				//Thickness of the bounds
				Gdx.gl.glLineWidth(25);
				shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				shapeRenderer.setColor(Color.YELLOW);
				shapeRenderer.circle(Xcoordinates[0], (height - Ycoordinates[0]), Radius[0] + 2);
				shapeRenderer.end();
			}
		}
		else {
			// We disable the fact that we can click on the circles
			for (int i = 0; i < Xcoordinates.length; i++) {
				circleBounds[i]= new Circle();
			}
			// We display a game over with the final score (for the moment)
			batch.begin();
			font.getData().setScale(1.5f, 1.5f);
			font.setColor(Color.WHITE);
			font.draw(batch, "Game Over !\n Score: " + score, (width / 2 - 300), (height / 2 + 200));
			batch.end();
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
		System.out.println("X coordinate : " + touchCoordinateX + " Y coordinate : " + touchCoordinateY);

		// We check if the player touched one of the circles displayed
		for (int k=0; k<circleBounds.length; k++) {
			// If the finger touch a circle we create a new circle
			if (circleBounds[k].contains(touchCoordinateX, touchCoordinateY)) {
				touchBonus++;
				// we play a sound effect
				pop.play(1.0f);

				// If the circle was a bonus (White circle)
				if (whiteBonus) {
					// We increase the score by 5 because it's a bonus
					score += 5;
					System.out.println("BONUS");
					// We display 5 others circles at the same time
					for (int i = 0; i < Xcoordinates.length; i++) {
						// New size for each circle
						Radius[i] = (int) (minSize + (maxSize - minSize) * Math.random());
						// We assign a random value to the position of each new circle inside the screen
						Xcoordinates[i] = (int) (minSize + edge + ((width - maxSize - minSize - 2*edge + 100) * Math.random()));
						Ycoordinates[i] = (int) (minSize + edge + ((height - maxSize - minSize - 2*edge + 100) * Math.random()));
						System.out.println("x : " + Xcoordinates[i] + "  y : " + Ycoordinates[i]);
						// New bounds for each new circle and its center
						circleBounds[i].set(Xcoordinates[i], Ycoordinates[i], Radius[i]);
						// New color for each circle
						randomColor = (int) (colorList.length * Math.random());
						RdmColorList[i] = colorList[randomColor];
					}
					// We reset the number click to display another bonus in 10 clicks
					touchBonus = 0;
				}
				// If the circle was not a bonus
				else {
					// We increase the score
					score += 1;
					// We cannot change the color of the first circle until all the circles were destroyed
					bonus = false;
					// We remove the circle
					Xcoordinates[k]= 0;
					Ycoordinates[k]= 0;
					Radius[k] = 0;
					circleBounds[k]= new Circle();

					// we check if all the circles were removed
					for (int coord: Xcoordinates ) {
						if (coord == 0){
							touch++;
							//System.out.println(touch);
						}
					}
					if (touch == CircleNumber){
						// We say that we can change the color of the first circle now
						bonus = true;
						// New size of the circle
						Radius[0] = (int) (minSize + (maxSize - minSize) * Math.random());
						// We assign a random value to the position of the new circle inside the screen
						Xcoordinates[0] = (int) (minSize + edge + ((width - maxSize - minSize - 2 * edge + 100) * Math.random()));
						Ycoordinates[0] = (int) (minSize + edge + ((height - maxSize - minSize - 2 * edge + 100) * Math.random()));
						// New bounds of the circle and its center
						circleBounds[0].set(Xcoordinates[0], Ycoordinates[0], Radius[0]);
					}
					touch=0;

					System.out.println("RANDOM");
				}

				// We change the color of the circle
				// if the player touched 10 times a circle, we display a Bonus (white circle)
				if (touchBonus == 10) {
					RdmColorList[0] = Color.WHITE;
					whiteBonus = true;
				}
				// else we display a circle with one color of the list
				else {
					// if there is no circles displayed by the bonus, we can change the color of the first circle
					if (bonus) {
						randomColor = (int) (colorList.length * Math.random());
						RdmColorList[0] = colorList[randomColor];
					}
					whiteBonus = false;
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

	@Override
	public void dispose(){
		pop.dispose();
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
	}
}
