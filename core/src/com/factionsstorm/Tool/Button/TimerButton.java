package com.factionsstorm.Tool.Button;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Sc;
import com.factionsstorm.Tool.Drawer;

public class TimerButton extends Button {

    private double endTime;
    private int timer=0;

    public TimerButton(Vector2 position, Vector2 dim, TextureRegion textureRegion, double endTime) {
        super(position, dim, textureRegion);
        this.endTime=endTime;
    }

    @Override
    public void update(){
        super.update();
        timer=(int)(endTime-Sc.time);
        locked=(timer>=0);
    }

    @Override
    public void render(){
        super.render();
        if(timer>=0) {
            Drawer.setFontScale(.4f * scale);
            Drawer.text(String.valueOf(timer), renderPosition.x, renderPosition.y+renderDim.y*.35f, renderDim.x);
        }
    }

    public void setEndTime(double endTime){
        this.endTime=endTime;
    }
}
