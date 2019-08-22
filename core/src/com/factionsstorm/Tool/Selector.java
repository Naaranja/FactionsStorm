package com.factionsstorm.Tool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Sc;

import java.util.ArrayList;

public class Selector {

    final static float TAB_WIDTH= Sc.screenW*.08f, TAB_HEIGTH=Sc.screenH*.08f, SELECTED_TAB_HEIGTH=Sc.screenH*.12f;

    ArrayList<Tab> tabs = new ArrayList<Tab>();
    float positionX;

    Tab selectedTab=null;

    public Selector(float positionX){
        this.positionX=positionX;
    }

    public void add(Tab tab){
        if(tabs.isEmpty()){
            tab.position = new Vector2(positionX, Sc.screenH-SELECTED_TAB_HEIGTH);
            tab.selected=true;
            selectedTab=tab;
        }else{
            tab.position = new Vector2(tabs.get(tabs.size()-1).position.x+TAB_WIDTH+Sc.screenW*.01f, Sc.screenH-TAB_HEIGTH);
        }
        tabs.add(tab);
    }

    public void update(){
        for(Tab tab : tabs){
            tab.update();
        }
    }

    public void render(){
        for(Tab tab : tabs){
            tab.render();
        }
    }

    public boolean touchDown(Vector3 input){
        for(Tab tab : tabs){
            if(tab.touch(input)){
                tab.touched=true;
                return true;
            }
        }
        return false;
    }

    public void touchUp(Vector3 input){
        for(Tab tab : tabs){
            if(tab.touched && tab.touch(input)){
                if(selectedTab!=null){
                    selectedTab.setSelected(false);
                }
                tab.setSelected(true);
                selectedTab=tab;
            }
            tab.touched=false;
        }
    }

    public static class Tab{

        TextureRegion textureRegion;
        Vector2 position;
        Trajectory trajectory=null;
        boolean touched=false, selected=false;

        public Tab(TextureRegion textureRegion){
            this.textureRegion=textureRegion;
        }

        private void update(){
            if(trajectory!=null){
                position=trajectory.getPosition();
                if(trajectory.isFinished())trajectory=null;
            }
        }

        private void render(){
            Drawer.texture(Assets.instance.menu.square, position.x, position.y, TAB_WIDTH, SELECTED_TAB_HEIGTH, 0, selected ? new Vector3(.2f, .8f, .9f) : new Vector3(.15f, .6f, 8f), 1);
            Drawer.texture(textureRegion, position.x+TAB_WIDTH*.1f, position.y, TAB_WIDTH*.8f, TAB_WIDTH*.8f, 0);
        }

        private boolean touch(Vector3 input){
            if(selected) {
                if (input.x > position.x && input.x < position.x + TAB_WIDTH && input.y > Sc.screenH - TAB_HEIGTH) {
                    return true;
                }
            }else{
                if (input.x > position.x && input.x < position.x + TAB_WIDTH && input.y > Sc.screenH - SELECTED_TAB_HEIGTH) {
                    return true;
                }
            }
            return false;
        }

        private void setSelected(boolean selected){
            if(!this.selected && selected){
                trajectory = new Trajectory(position, new Vector2(position.x, Sc.screenH-SELECTED_TAB_HEIGTH), .3f);
                action();
            }else if(this.selected && !selected){
                trajectory = new Trajectory(position, new Vector2(position.x, Sc.screenH-TAB_HEIGTH), .3f);
            }
            this.selected=selected;
        }

        public void action(){

        }

    }

}
