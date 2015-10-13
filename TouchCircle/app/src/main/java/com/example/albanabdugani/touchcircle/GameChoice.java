package com.example.albanabdugani.touchcircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Alban on 10/10/2015.
 */
public class GameChoice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choice_layout);

        // Back button
        ImageButton IBback = (ImageButton)findViewById(R.id.IB_back_game);
        IBback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We finish the activity and return to the HomePage
                finish();
            }
        });
    }
}
