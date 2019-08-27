package com.factionsstorm.Building;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Sc;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Tool.Trajectory;

import java.util.ArrayList;

public abstract class Harvestable extends Building {

    protected enum State{waiting, producing, finished};
    protected State state;

    protected TextureRegion productionTexture;
    private float gapDelay;

    private FinishedIcon finishedIcon = new FinishedIcon();
    private ArrayList<Icon> icons = new ArrayList<Icon>();
    private Zzz zzz = new Zzz();

    public Harvestable(Vector2 position) {
        super(position);
        gapDelay = (float)Math.random();
    }

    public void updateParticles(OrthographicCamera mapCamera){
        if(!icons.isEmpty()) {
            for(int i=icons.size()-1;i>=0;i--){
                if (icons.get(i).isDead()) {
                    icons.remove(i);
                } else {
                    icons.get(i).update(mapCamera);
                }
            }
        }else{
            if(state == State.finished){
                finishedIcon.update(mapCamera);
            }else if(state == State.waiting) {
                zzz.update(mapCamera);
            }
        }
    }

    public void renderParticles(){
        super.render();
        if(!icons.isEmpty()) {
            for (Icon icon : icons) {
                icon.render();
            }
        }else{
            if(state == State.finished){
                finishedIcon.render();
            }else if(state == State.waiting) {
                zzz.render();
            }
        }
    }

    protected void harvest(int value){
        icons.add(new Icon(productionTexture, value, 0));
        icons.add(new Icon(Assets.instance.icon.energie, -1, 1));
    }

    private class FinishedIcon{
        final float height = Sc.screenH*.03f;
        Vector2 position = null;
        float dim, scale;

        private void update(OrthographicCamera mapCamera){
            Vector3 projection = mapCamera.project(new Vector3(renderPosition.x+1.5f, renderPosition.y+1.5f, 0));
            position = new Vector2(projection.x, projection.y + (float)(1+Math.sin(Sc.time+2*Math.PI*gapDelay))*.5f*height / mapCamera.zoom);
            dim = Sc.screenH * .05f / mapCamera.zoom;
            scale = .5f/mapCamera.zoom;
        }

        private void render(){
            if(position!=null){
                Drawer.texture(productionTexture,position.x-dim*.5f, position.y, dim, dim, 0);
            }
        }
    }

    private class Icon{
        TextureRegion textureRegion;
        String chainValue;
        double delay, startTime;
        final float duration=1, height=Sc.screenH*.075f;
        Trajectory trajectory;
        Vector2 position = null;
        float dim, alpha, scale;

        private Icon(TextureRegion textureRegion, int value, double delay) {
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
                dim = Sc.screenH * .065f / mapCamera.zoom;
                scale = .5f/mapCamera.zoom;
            }
        }

        private void render(){
            if(position!=null){
                Drawer.texture(textureRegion,position.x-dim, position.y, dim, dim, 0, alpha);
                Drawer.setFontScale(scale);
                Drawer.text(chainValue, position.x, position.y+dim*.85f, new Vector3(1,1,1), alpha);
            }
        }
    }

    private class Zzz{

        ArrayList<Z> list = new ArrayList<Z>();

        private void update(OrthographicCamera mapCamera){
            Vector3 projection = mapCamera.project(new Vector3(renderPosition.x+1, renderPosition.y+1, 0));
            for(int i=list.size()-1;i>=0;i--){
                if (list.get(i).isDead()) {
                    list.remove(i);
                } else {
                    list.get(i).update(projection, mapCamera.zoom);
                }
            }
            if(list.isEmpty()){
                list.add(new Z(-3*gapDelay));
                list.add(new Z(-3*gapDelay+.5f));
                list.add(new Z(-3*gapDelay+1));
            }else if(list.size()<3){
                list.add(new Z(1));
                list.add(new Z(1.5f));
                list.add(new Z(2));
            }
        }

        private void render(){
            if(!list.isEmpty()) {
                for (Z z : list) {
                    z.render();
                }
            }
        }

        private class Z{
            double startTime;
            final float duration=3, height=Sc.screenH*.1f, dim=Sc.screenH*.03f;
            Vector2 position = null;
            float alpha, scale;
            private Z(float delay){
                startTime = Sc.time+delay;
            }

            private boolean isDead(){
                return Sc.time>startTime+duration;
            }

            private void update(Vector3 projection, float scale){
                if(Sc.time>startTime){
                    float t=(float)(Sc.time-startTime)/duration;
                    float r=2*(float)Math.PI*t;
                    position = new Vector2(projection.x + ((float)(Math.sin(r))*height*.35f - dim*.5f)/scale, projection.y + t*height/scale);
                    alpha=1-t;
                    this.scale = scale;
                }
            }

            private void render(){
                if(position!=null){
                    Drawer.texture(Assets.instance.icon.z, position.x, position.y, dim/scale, dim/scale, 0, alpha);
                }
            }
        }
    }
}