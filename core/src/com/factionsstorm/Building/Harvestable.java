package com.factionsstorm.Building;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Tool.Trajectory;

public abstract class Harvestable extends Building {

    protected enum State{waiting, producing, finished};

    private Icon[] icons = new Icon[2];

    public Harvestable(Vector2 position) {
        super(position);
    }


    public void updateParticles(OrthographicCamera mapCamera){
        for(int i=0;i<2;i++){
            if(icons[i]!=null){
                if(icons[i].isDead()){
                    icons[i]=null;
                }else{
                    icons[i].update(mapCamera);
                }
            }
        }
    }

    public void renderParticles(){
        super.render();
        for(int i=0;i<2;i++){
            if(icons[i]!=null)icons[i].render();
        }
    }

    public void harvest(Player.Commodities commoditie, int value){
        icons[0] = new Icon(Assets.instance.icon.getCommoditieTexture(commoditie), value, 0);
        icons[1] = new Icon(Assets.instance.icon.energie, -1, 1);
    }

    private class Icon{

        TextureRegion textureRegion;
        String chainValue;
        double delay, startTime;
        final float duration=1;
        final float height=Sc.screenH*.1f;
        Trajectory trajectory;
        Vector2 position;
        float dim, alpha, scale;

        public Icon(TextureRegion textureRegion, int value, double delay) {
            this.textureRegion = textureRegion;
            chainValue = value >= 0 ? "+ " : "- ";
            chainValue+= Sc.formatedInt(Math.abs(value));
            this.delay=delay;
            startTime = Sc.time + delay;

            trajectory = new Trajectory(new Vector2(), new Vector2(0, height), duration, (float)delay);
        }

        private boolean isDead(){
            return Sc.time>startTime+duration;
        }

        private void update(OrthographicCamera mapCamera){
            if(Sc.time>startTime) {
                float t = (float) Math.max(0, (Sc.time - startTime) / duration);
                alpha = t < .3f ? t / .4f : t > .7f ? (1 - t) / .4f : 1;
                Vector3 projection = mapCamera.project(new Vector3(renderPosition.x+1, renderPosition.y+1, 0));
                Vector2 positionToAdd = trajectory.getPosition();
                position = new Vector2(projection.x, projection.y + positionToAdd.y / mapCamera.zoom);
                dim = Sc.screenH * .08f / mapCamera.zoom;
                scale = .5f/mapCamera.zoom;
            }
        }

        private void render(){
            if(Sc.time>startTime){
                Drawer.texture(textureRegion,position.x-dim, position.y, dim, dim, 0, alpha);
                Drawer.setFontScale(scale);
                Drawer.text(chainValue, position.x, position.y+dim*.85f, new Vector3(1,1,1), alpha);
            }
        }
    }
}
