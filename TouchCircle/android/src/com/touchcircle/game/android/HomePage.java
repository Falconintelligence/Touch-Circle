package com.touchcircle.game.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

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
