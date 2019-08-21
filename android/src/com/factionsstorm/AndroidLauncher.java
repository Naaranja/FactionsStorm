package com.factionsstorm;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.factionsstorm.FactionsStorm;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
		config.useCompass=false;
		config.useGyroscope=false;
		config.useRotationVectorSensor=false;
		config.useImmersiveMode=true;
		initialize(new FactionsStorm(), config);
	}
}
