package com.example.albanabdugani.touchcircle;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Alban on 10/10/2015.
 */
public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);

        // I lock the screen orientation in portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // We associate the ImageButtons
        ImageButton IBplay = (ImageButton)findViewById(R.id.IB_Play);
        ImageButton IBhelp = (ImageButton)findViewById(R.id.IB_help);
        ImageButton IBscores = (ImageButton)findViewById(R.id.IB_scores);
        ImageButton IBsettings = (ImageButton)findViewById(R.id.IB_settings);

        // We go to the game mode when we press the play button
        IBplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, GameChoice.class);
                startActivity(intent);
            }
        });

        // We go to the Help page when we press the help button
        IBhelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Help.class);
                startActivity(intent);
            }
        });

        // We go to the Score page when we press the score button
        IBscores.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Scores.class);
                startActivity(intent);
            }
        });

        // We go to the Settings page when we press the settings button
        IBsettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
