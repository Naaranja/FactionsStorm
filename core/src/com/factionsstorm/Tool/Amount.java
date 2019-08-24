package com.factionsstorm.Tool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;

import java.beans.VetoableChangeListener;
import java.util.ArrayList;

public class Amount {

    private final float COMMODITIE_SIZE=Sc.screenH*.05f;

    private ArrayList<Single> list = new ArrayList<Single>();

    public void add(Player.Commodities commoditie, int value){
        list.add(new Single(commoditie,value));
    }

    public void pay(){
        for(Single single : list){
            single.pay();
        }
    }

    public void win(){
        for(Single single : list){
            single.win();
        }
    }

    public boolean isPayable(){
        for(Single single : list){
            if(!single.isPayable()){
                return false;
            }
        }
        return true;
    }

    public void render(Vector2 position, float heigth){
        int size=list.size();
        Drawer.setFontScale(.4f);
        for(int z=0;z<size;z++){
            Drawer.texture(list.get(z).getTexture(),position.x+ Sc.screenW*.04f,position.y+heigth*(1-(z+.5f)/size)-COMMODITIE_SIZE*.5f, COMMODITIE_SIZE, COMMODITIE_SIZE,0, new Vector3(.8f,.8f,.8f),1);
            if(list.get(z).isPayable()){
                Drawer.text(String.valueOf(list.get(z).getValue()), position.x + Sc.screenW * .1f, position.y+heigth*(1-(z+.5f)/size)-COMMODITIE_SIZE*.5f + Sc.screenH * .045f);
            }else {
                Drawer.text(String.valueOf(list.get(z).getValue()), position.x + Sc.screenW * .1f, position.y+heigth*(1-(z+.5f)/size)-COMMODITIE_SIZE*.5f + Sc.screenH * .045f, new Vector3(1, 0, 0), 1);
            }
        }
    }

    public class Single{
        private Player.Commodities commoditie;
        private int value;

        public Single(Player.Commodities commoditie, int value) {
            this.commoditie = commoditie;
            this.value = value;
        }

        public void pay(){
            Player.instance.add(commoditie,-value);
        }

        public void win(){
            Player.instance.add(commoditie,value);
        }

        public boolean isPayable(){
            return (Player.instance.get(commoditie)>=value);
        }

        public TextureRegion getTexture(){
            switch(commoditie){
                case fcoin: return Assets.instance.icon.ressources[0];
                case wood: return Assets.instance.icon.ressources[1];
                case oil: return Assets.instance.icon.ressources[2];
                case iron: return Assets.instance.icon.ressources[3];
                case bronze: return Assets.instance.icon.ressources[4];
                case aluminium: return Assets.instance.icon.ressources[5];
                case gold: return Assets.instance.icon.ressources[6];
                case uraniumn: return Assets.instance.icon.ressources[7];
                case plasma: return Assets.instance.icon.ressources[8];
                case ruby: return Assets.instance.icon.ressources[9];
            }
            return null;
        }

        public int getValue(){
            return value;
        }
    }
}
