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

/**
 * Created by Alban on 26/10/2015.
 */
public class GameClassicMode extends ApplicationAdapter implements InputProcessor, Runnable {

    ShapeRenderer shapeRenderer;					// Give the possibility to draw elements on the screen
    static int height, width;						// Height and Width of the screen
    int touchCoordinateX, touchCoordinateY;			// Position of the user's finger on the screen
    static int edge = 200;							// Edge to give bounds for the zone where we display circles
    static int minSize = 50;						// Minimum size of the circle
    static int maxSize = 150;						// Maximum size of the circle
    static int randomColor;							// Number of the random color
    int CircleNumber = 5;							// Number of circles to display when we touch the bonus
    static int touchBonus = 0;						// Number of time we touch the screen before displaying a bonus
    int touch = 0;									// Number of circle touched after pressing the bonus circle
    static boolean bonus = false;					// Boolean which tells us if we have to press on circles displayed by the bonus circle in order to know when we have to change the color of the next circle
    static boolean whiteBonus = false;				// Boolean which tells us if it is a bonus circle or not
    static Color[] colorList, RdmColorList;			// List of the colors of the circles
    static Circle[] circleBounds;					// List of the bounds for the circle (to know if we click inside the circle)
    static int[] Xcoordinates, Ycoordinates, Radius;// Parameters of the circles
    Music pop;										// Sound effect when we click on a circle
    Music background_music;                         // Background music for the game
    BitmapFont font;								// Font of the text
    SpriteBatch batch;								// SpriteBatch to display the text
    int score = 0;									// Value of the score
    long startTime;									// Start time of the game
    int minutes = 0;                                // Number of minutes since the game started
    static boolean displayNewCircle = true;         // Boolean which display a circle or not during a while
    long apparitionTime = 2500;                     // Time where we can see the circle
    long invisibleTime = 1000;                      // Time where we can't see a circle
    static Thread myThread;                         // Thread for removing or displaying circle
    public static boolean running = true;           // Boolean indicating if the game is playing
    static int missedCircle = 0;                    // Number of circles missed
    int time = 0;                                   // Time since the game has started
    static int endTime = 3;                         // End time of the game

    static int Difficulty = 3;                      // Difficulty of the game (1=easy, 2=normal, 3=hard)

    @Override
    public void create () {

        batch = new SpriteBatch();
        startTime = TimeUtils.millis();

        System.out.println("Classic Mode");

        // List of the colors of the circles
        colorList = new Color[]{
                Color.CYAN,
                Color.GREEN,
                Color.RED,
                Color.MAGENTA,
                Color.YELLOW
        };
        // We load our sound effect and our background music
        pop = Gdx.audio.newMusic(Gdx.files.internal("data/bubble_pop.wav"));
        background_music = Gdx.audio.newMusic(Gdx.files.internal("data/background_sound.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));

        // We instantiate all the lists
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
        //System.out.println("height : " + height + "  width : " + width);

        // If the difficulty is easy or normal, we start with a big Circle
        if(Difficulty==1 || Difficulty==2)
            Radius[0] = maxSize;			// Maximal size for the first circle
        else if (Difficulty==3)
            Radius[0] = minSize;			// Minimal size for the first circle
        // We assign a random value to the position of the circle inside the screen
        Xcoordinates[0] = (int) (minSize + edge + ( (width - maxSize - minSize - 2*edge + 100) * Math.random() ) );
        Ycoordinates[0] = (int) (minSize + edge + ( (height - maxSize - minSize - 2*edge + 100) * Math.random() ) );
        // We set the bounds of the circle and its center
        circleBounds[0].set(Xcoordinates[0], Ycoordinates[0], Radius[0]);

        myThread = new Thread(new GameClassicMode());
        myThread.setDaemon(true);
        myThread.start();

        background_music.play();
        background_music.setLooping(true);

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

        // We fetch the time since the user start the game if the game is not over
        if (running && minutes<endTime) {
            time = (int) (TimeUtils.timeSinceMillis(startTime) / 1000);
        }

        batch.begin();
        font.getData().setScale(1.15f, 1.15f);
        // We choose a color for the text
        font.setColor(Color.ORANGE);
        // We display the score
        font.draw(batch, "Score: " + score, 50, (height - 25));
        // We display the time with this format 0:00 and we stop at 1:00 minute
        if (time < 60)
            if (time < 10)
                font.draw(batch, minutes + ":0" + String.valueOf(time), (width - 200), (height - 25));
            else font.draw(batch, minutes + ":" + time, (width - 200), (height - 25));
        else if (minutes==endTime){
            font.draw(batch, endTime + ":00", (width - 200), (height - 25));
            font.draw(batch, "Score: " + score, 50, (height - 25));
        }
        else {
            minutes++;
            startTime = TimeUtils.millis();
            font.draw(batch, minutes + ":00", (width - 200), (height - 25));
            font.draw(batch, "Score: " + score, 50, (height - 25));
        }
        batch.end();

        // If it is the apparitionTime and if the time is less than endTime minutes
        if (displayNewCircle && minutes<endTime) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            // We draw each circle with its color
            for (int i = 0; i < Xcoordinates.length; i++) {
                shapeRenderer.setColor(RdmColorList[i]);
                shapeRenderer.circle(Xcoordinates[i], (height - Ycoordinates[i]), Radius[i]);
                circleBounds[i].set(Xcoordinates[i], Ycoordinates[i], Radius[i]);
            }
            shapeRenderer.end();

            // If it is a white circle, we add a outline
            if (whiteBonus) {
                //Thickness of the bounds
                Gdx.gl.glLineWidth(25);
                // Bounds of the circle
                circleBounds[0].set(Xcoordinates[0], Ycoordinates[0], Radius[0]);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.YELLOW);
                shapeRenderer.circle(Xcoordinates[0], (height - Ycoordinates[0]), Radius[0] + 2);
                shapeRenderer.end();
            }
        }
        // If the apparition time is over or if the game is over
        else {
            // We disable the fact that we can click on the circles
            for (int i = 0; i < Xcoordinates.length; i++) {
                circleBounds[i]= new Circle();
            }
            // If the user missed a circle
            if (!running || minutes ==endTime) {
                // We remove all the circles
                for (int i = 0; i < Xcoordinates.length; i++) {
                    Xcoordinates[i]= 0;
                    Ycoordinates[i]= 0;
                    Radius[i] = 0;
                }
                // We display a game over with the final score (for the moment)
                batch.begin();
                font.getData().setScale(1.5f, 1.5f);
                font.setColor(Color.WHITE);
                font.draw(batch, "Game Over !\n Score: " + score, (width / 2 - 300), (height / 2 + 200));
                batch.end();
            }
        }
    }

    @Override
    public void run() {
        try {
            while (running && minutes<endTime) {
                // We wait during apparitionTime before removing the circles
                Thread.sleep(apparitionTime);
                displayNewCircle = false;

                // If the difficulty is easy each circle must have the maximum size
                if (Difficulty ==1)
                    Radius[0] = maxSize;
                    // If the difficulty is normal each circle have a random size
                else if (Difficulty ==2)
                    Radius[0] = (int) (minSize + (maxSize - minSize) * Math.random());
                    // If the difficulty is hard each circle have the minimum size
                else
                    Radius[0] = minSize;

                // We assign a random value to the position of the new circle inside the screen
                Xcoordinates[0] = (int) (minSize + edge + ((width - maxSize - minSize - 2 * edge + 100) * Math.random()));
                Ycoordinates[0] = (int) (minSize + edge + ((height - maxSize - minSize - 2 * edge + 100) * Math.random()));
                // New bounds of the circle and its center
                circleBounds[0].set(Xcoordinates[0], Ycoordinates[0], Radius[0]);
                touchBonus++;
                // If the user touched 10 times a circle or if the circles were removing 10 times
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
                // We remove the circle during the invisibleTime and then we display the new one
                Thread.sleep(invisibleTime);
                displayNewCircle = true;

                // If the value of running changed during the sleep method
                if (!running || minutes==endTime) {
                    displayNewCircle = false;
                    running = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    // When the user touch the screen
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
                // we play a sound effect with the volume at the maximum
                pop.play();

                // If the circle was a bonus (White circle)
                if (whiteBonus) {
                    // We increase the score by 5 because it's a bonus
                    score += 5;
                    System.out.println("BONUS");
                    // We display 5 others circles at the same time
                    for (int i = 0; i < Xcoordinates.length; i++) {

                        // If the difficulty is easy each circle must have the maximum size
                        if (Difficulty ==1)
                            Radius[i] = maxSize;
                            // If the difficulty is normal each circle have a random size
                        else if (Difficulty ==2)
                            Radius[i] = (int) (minSize + (maxSize - minSize) * Math.random());
                            // If the difficulty is hard each circle have the minimum size
                        else
                            Radius[i] = minSize;

                        // We assign a random value to the position of each new circle inside the screen
                        Xcoordinates[i] = (int) (minSize + edge + ((width - maxSize - minSize - 2*edge + 100) * Math.random()));
                        Ycoordinates[i] = (int) (minSize + edge + ((height - maxSize - minSize - 2*edge + 100) * Math.random()));
                        System.out.println("x : " + Xcoordinates[i] + "  y : " + Ycoordinates[i]);
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

                        // If the difficulty is easy each circle must have the maximum size
                        if (Difficulty ==1)
                            Radius[0] = maxSize;
                            // If the difficulty is normal each circle have a random size
                        else if (Difficulty ==2)
                            Radius[0] = (int) (minSize + (maxSize - minSize) * Math.random());
                            // If the difficulty is hard each circle have the minimum size
                        else
                            Radius[0] = minSize;

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
            else {
                missedCircle++;
                if (missedCircle == 5) {
                    System.out.println("LOUPE");
                    running = false;
                    displayNewCircle = false;
                }
            }
        }
        missedCircle=0;
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
        background_music.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        // We have to reset these value for the next time because they are static
        running = true;
        displayNewCircle = true;
        touchBonus=0;
        whiteBonus = false;
        bonus = false;
        minutes = 0;
    }
}
