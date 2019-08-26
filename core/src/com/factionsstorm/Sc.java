package com.factionsstorm;

import com.badlogic.gdx.Gdx;

public class Sc {
    public static final int screenW=Gdx.graphics.getWidth(), screenH=Gdx.graphics.getHeight();
    public static final float W=32,H=32*screenH/screenW;
    public static double time=System.currentTimeMillis()/1000;
    public static int fingers=0;

    public static String formatedInt(int value){
        String chain = String.valueOf(value);
        StringBuilder formatedInt = new StringBuilder();
        int length = chain.length();
        for(int i=0;i<length;i++){
            if(i%3==0)formatedInt.insert(0," ");
            formatedInt.insert(0, chain.charAt(length-i-1));
        }
        return formatedInt.toString();
    }

    public static String formatedTime(int value){
        StringBuilder formatedTime = new StringBuilder();
        if(value>=86400){
            formatedTime.append(value/86400+"d");
        }
        int rest = value%86400;
        if(rest>=3600){
            formatedTime.append(rest/3600+"h");
        }
        rest %= 3600;
        if(rest>=60){
            formatedTime.append(rest/60+"m");
        }
        rest %= 60;
        if(value==0 || rest!=0){
            formatedTime.append(rest+"s");
        }
        return formatedTime.toString();
    }
}
