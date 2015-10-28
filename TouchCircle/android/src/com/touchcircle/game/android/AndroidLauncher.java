package com.touchcircle.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.touchcircle.game.GameClassicMode;
import com.touchcircle.game.GameTimeMode;

public class AndroidLauncher extends AndroidApplication {

	String Mode;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			Mode = bundle.getString("choice");
		}
		else Mode = (String) savedInstanceState.getSerializable("choice");

		switch (Mode){
			case "time":
				initialize(new GameTimeMode(), config);
				break;
			case "classic":
				initialize(new GameClassicMode(), config);
				break;
			default:
				System.out.println("ERREUR");
		}
	}
}