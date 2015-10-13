package com.example.albanabdugani.touchcircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Alban on 10/10/2015.
 */
public class Settings extends Activity {

    private boolean soundON = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        final ImageButton IBsound = (ImageButton)findViewById(R.id.IB_sound);
        final TextView TVsound = (TextView)findViewById(R.id.TVsound);
        IBsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundON) {
                    IBsound.setImageResource(R.drawable.sound_black);
                    TVsound.setText("OFF");
                    soundON=false;
                }
                else {
                    IBsound.setImageResource(R.drawable.sound);
                    TVsound.setText("ON");
                    soundON=true;
                }
            }
        });

        ImageButton IBback = (ImageButton)findViewById(R.id.IB_back_settings);
        IBback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
