package com.factionsstorm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.factionsstorm.Data.Data;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.State.Village.*;
import com.factionsstorm.Tool.Drawer;

public class FactionsStorm extends ApplicationAdapter {

	@Override
	public void create () {
		Assets.instance.onCreate(new AssetManager());
		Drawer.createCustomFont();

		VillageParameter villageParameter = new VillageParameter();
		villageParameter.setBuildings(Data.Own.getBuildings());
		StateManager.instance.setState(new Village(villageParameter, new OwnUI()));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Sc.time += Gdx.graphics.getDeltaTime();

		StateManager.instance.update();
		StateManager.instance.render();
	}
	
	@Override
	public void dispose () {

	}
}
