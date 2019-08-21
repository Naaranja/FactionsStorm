package com.factionsstorm.Tool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.factionsstorm.Assets;
import com.factionsstorm.Player;

import java.util.ArrayList;

public class Amount {

    ArrayList<Single> list = new ArrayList<Single>();

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
