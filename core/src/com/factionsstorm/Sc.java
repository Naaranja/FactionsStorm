package com.factionsstorm;

import com.badlogic.gdx.Gdx;

public class Sc {
    public static final int screenW=Gdx.graphics.getWidth(), screenH=Gdx.graphics.getHeight();
    public static final float W=32,H=32*screenH/screenW;
    public static double time=System.currentTimeMillis()/1000;
    public static int fingers=0;
}
