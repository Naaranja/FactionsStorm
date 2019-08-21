package com.factionsstorm.Tool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.State.Village.VillageManager;
import com.factionsstorm.Tool.Button.Button;

public class Item {

    private VillageManager vm;
    public Building building;
    public Amount amount;
    public float positionX;
    private final float width=Sc.screenW*.2f;
    private boolean locked, touched=false, selected=false;
    Button buyButton;

    //TODO Button -> TextButton

    public Item(final VillageManager vm, final Building building, float positionX){
        this.vm=vm;
        this.building=building;
        amount = building.getAmount();
        this.positionX=positionX;

        locked = (Player.instance.getLevel()<building.getLevel());

        buyButton = new Button(new Vector2(positionX+width*.1f,Sc.screenH*.04f), new Vector2(width*.8f, Sc.screenH*.2f), Assets.instance.menu.buttonValider){
            @Override
            public void action(){
                if(amount.isPayable()) {
                    vm.setConstructionBuilding(building);
                    StateManager.instance.exit();
                }
            }
        };
    }

    public void update(){
        buyButton.update();
    }

    public void render(){
        if(locked){
            Drawer.texture(Assets.instance.menu.square, positionX, 0, width, Sc.screenH * .5f, 0, new Vector3(.8f, .8f, .8f), 1);
            renderName("???", Sc.screenH*.5f);
            renderTexture(true, Sc.screenH*.58f);
        }else {
            if (!selected) {
                renderAmount(0);
                renderDescription(Sc.screenH * .25f);
                renderName(building.getName(), Sc.screenH * .5f);
                renderTexture(false, Sc.screenH * .58f);
            } else {
                renderAmount(Sc.screenH * .28f);
                renderDescription(Sc.screenH * .53f);
                renderName(building.getName(), Sc.screenH * .78f);
                Drawer.texture(Assets.instance.menu.square, positionX, 0, width, Sc.screenH * .28f, 0, new Vector3(.7f, .7f, .7f), 1);
                buyButton.render();
            }
        }
    }

    private void renderTexture(boolean locked, float y){
        if(locked) {
            Drawer.texture(Assets.instance.menu.square, positionX, y, width, Sc.screenH * .28f, 0, new Vector3(.7f, .7f, .7f), 1);
            Drawer.texture(building.textureFixe, positionX + (Sc.screenW * .2f - Sc.screenH * .3f) * .5f, y + Sc.screenH * .02f, Sc.screenH * .3f, Sc.screenH * .3f, 0, new Vector3(0,0,0), 1);
        }else{
            Drawer.texture(Assets.instance.menu.square, positionX, y, width, Sc.screenH * .28f, 0, new Vector3(.7f, .8f, .2f), 1);
            Drawer.texture(building.textureFixe, positionX + (Sc.screenW * .2f - Sc.screenH * .3f) * .5f, y + Sc.screenH * .02f, Sc.screenH * .3f, Sc.screenH * .3f, 0);
        }
    }

    private void renderName(String name, float y){
        Drawer.texture(Assets.instance.menu.square,positionX,y, width, Sc.screenH*.08f,0, new Vector3(.3f,.3f,.3f),1);
        Drawer.setFontScale(.48f);
        Drawer.text(name ,positionX,y+Sc.screenH*.07f, width);
    }

    private void renderDescription(float y){
        Drawer.texture(Assets.instance.menu.square,positionX,y, width, Sc.screenH*.25f,0, new Vector3(.7f,.7f,.7f),1);
    }

    private void renderAmount(float y){
        Drawer.texture(Assets.instance.menu.square,positionX,y, width, Sc.screenH*.25f,0, new Vector3(.8f,.8f,.8f),1);
        int size=amount.list.size();
        Drawer.setFontScale(.4f);
        for(int z=0;z<size;z++){
            Drawer.texture(amount.list.get(z).getTexture(),positionX+Sc.screenW*.04f,y+Sc.screenH*(float)(.25f-.05*size)/size+Sc.screenH*.06f*(size-z-1), Sc.screenH*.05f, Sc.screenH*.05f,0, new Vector3(.8f,.8f,.8f),1);
            if(amount.list.get(z).isPayable()){
                Drawer.text(String.valueOf(amount.list.get(z).getValue()), positionX + Sc.screenW * .1f, y + Sc.screenH * (float) (.25f - .05 * size) / size + Sc.screenH * .06f * (size - z - 1) + Sc.screenH * .045f);
            }else {
                Drawer.text(String.valueOf(amount.list.get(z).getValue()), positionX + Sc.screenW * .1f, y + Sc.screenH * (float) (.25f - .05 * size) / size + Sc.screenH * .06f * (size - z - 1) + Sc.screenH * .045f, new Vector3(1, 0, 0), 1);
            }
        }
    }

    public boolean touchDown(Vector3 input){
        if(touch(input)){
            touched=true;
            if(selected && buyButton.touchDown(input)){
                return true;
            }
        }
        return false;
    }

    public boolean touchUp(Vector3 input, boolean trueDrag){
        boolean touchedUp=false;
        if(touch(input)){
            if(touched && !trueDrag && !buyButton.touchUp(input) && !locked){
                selected=!selected;
            }
           touchedUp=true;
        }
        buyButton.setTouched(false);
        touched=false;
        return touchedUp;
    }

    private boolean touch(Vector3 input){
        return (input.x>positionX && input.x<positionX+width && input.y<Sc.screenH*.86f);
    }
}
