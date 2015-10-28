package com.touchcircle.game.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.badlogic.gdx.Gdx;

/**
 * Created by Alban on 10/10/2015.
 */
public class GameChoice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choice_layout);

        // I lock the screen orientation in portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Back button
        ImageButton IBback = (ImageButton)findViewById(R.id.IB_back_game);
        IBback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We finish the activity and return to the HomePage
                finish();
            }
        });

        // Time mode
        Button TimeBtn = (Button)findViewById(R.id.Btn_time_mode);
        TimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We go to the Android Launcher
                Intent intent = new Intent(GameChoice.this, AndroidLauncher.class);
                intent.putExtra("choice", "time");
                startActivity(intent);
            }
        });

        // Classic mode
        Button ClassicBtn = (Button)findViewById(R.id.Btn_classic_mode);
        ClassicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameChoice.this, AndroidLauncher.class);
                intent.putExtra("choice", "classic");
                startActivity(intent);
            }
        });
    }
}
