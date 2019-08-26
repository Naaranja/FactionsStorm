package com.factionsstorm.State.Menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Producer.Producer;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.State.State;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.Tool.Amount;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Drawer;

import java.util.ArrayList;

public class ProducerProduction extends Menu implements State {

    Producer building;
    Amount[] productionCost = new Amount[8], productionIncome = new Amount[8];
    ArrayList<Item> items = new ArrayList<Item>();

    public ProducerProduction(Producer building, Player.Commodities commoditie, int[] productionCost, int[] productionIncome) {
        this.building = building;
        for(int i=0;i<8;i++){
            this.productionCost[i] = new Amount();
            this.productionCost[i].add(Player.Commodities.fcoin, productionCost[i]);
            this.productionIncome[i] = new Amount();
            this.productionIncome[i].add(commoditie, productionIncome[i]);
        }
    }

    @Override
    public void create() {
        super.create();

        for(int i=0;i<8;i++){
            items.add(new Item(i,Sc.screenW*(i*.21f +.01f), productionCost[i], productionIncome[i]));
        }

        limitX = items.get(items.size() - 1).positionX + Sc.screenW * .21f;
    }

    @Override
    public void update() {
        super.update();

        for(Item item : items){
            item.update();
        }
    }

    @Override
    public void render() {
        Drawer.batch.setProjectionMatrix(fixedCamera.combined);
        Drawer.batch.setTransformMatrix(matrix);
        Drawer.batch.begin();

        super.render();

        Drawer.setFontScale(1f);
        Drawer.text("Buildings", Sc.screenW*.01f, Sc.screenH*.99f);

        Drawer.batch.setProjectionMatrix(camera.combined);

        for(Item item : items){
            item.render();
        }

        Drawer.batch.end();
    }

    @Override
    public void dispose() {

    }

    public boolean touchDown(int x, int y, int pointer) {
        boolean touched=super.touchDown(x,y,pointer);

        Vector3 input=camera.unproject(new Vector3(x,y,0));
        for(Item item : items){
            if(item.touchDown(input))touched=true;

        }
        setDragable(!touched);
        return touched;
    }

    public boolean touchUp(int x, int y, int pointer) {
        Vector3 input=camera.unproject(new Vector3(x,y,0));
        for(Item item : items){
            item.touchUp(input, dragger.isTrueDrag());
        }
        super.touchUp(x,y,pointer);
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        super.touchDragged(x,y,pointer);
        return false;
    }

    private class Item {

        private Amount productionCost, productionIncome;
        TextureRegion textureRegion;
        private float positionX;
        private final float width=Sc.screenW*.2f;
        private boolean touched=false, selected=false;
        private Button buyButton;
        private String name, time;

        String[] names = {"Little harvest", "Little production", "Sweet harvest", "Sweet production",
                "Big harvest", "Big production", "Wtf harvest", "Wtf production"};
        String[] timesString = {"5m", "30m", "1h", "2h", "4h", "12h", "24h", "48h"};
        //TODO buyButton -> TextButton

        public Item(final int index, float positionX, final Amount productionCost, Amount productionIncome){
            this.textureRegion = Assets.instance.icon.iProd[index];
            this.positionX = positionX;
            this.productionCost = productionCost;
            this.productionIncome = productionIncome;

            name=names[index];
            time=timesString[index];

            buyButton = new Button(new Vector2(positionX+width*.1f,Sc.screenH*.04f), new Vector2(width*.8f, Sc.screenH*.2f), Assets.instance.menu.buttonValider){
                @Override
                public void action(){
                    if(productionCost.isPayable()) {
                        productionCost.pay();
                        building.produce(index);
                        StateManager.instance.exit();
                    }
                }
            };
            if(!productionCost.isPayable()) buyButton.setLocked(true);
        }

        public void update(){
            buyButton.update();
        }

        public void render(){
            if (!selected) {
                renderTexture(false, Sc.screenH * .52f);
                renderName(Sc.screenH * .44f);
                renderProduction(0);
            } else {
                renderName(Sc.screenH * .72f);
                renderProduction(Sc.screenH * .28f);
                Drawer.texture(Assets.instance.menu.square, positionX, 0, width, Sc.screenH * .28f, 0, new Vector3(.7f, .7f, .7f), 1);
                buyButton.render();
            }
        }

        private void renderTexture(boolean locked, float y){
            Drawer.texture(Assets.instance.menu.topTriangle, positionX, y+Sc.screenH*.28f, width, Sc.screenH*.015f, 0, new Vector3(.7f, .8f, .2f), 1);
            Drawer.texture(Assets.instance.menu.square, positionX, y, width, Sc.screenH * .28f, 0, new Vector3(.7f, .8f, .2f), 1);
            Drawer.texture(textureRegion, positionX + (Sc.screenW * .2f - Sc.screenH * .3f) * .5f, y, Sc.screenH * .3f, Sc.screenH * .3f, 0);
        }

        private void renderName(float y){
            if(selected){
                Drawer.texture(Assets.instance.menu.topTriangle, positionX, y+Sc.screenH*.08f, width, Sc.screenH*.015f, 0, new Vector3(.3f, .3f, .3f), 1);
            }
            Drawer.texture(Assets.instance.menu.square,positionX, y, width, Sc.screenH*.08f,0, new Vector3(.3f,.3f,.3f),1);
            Drawer.setFontScale(.48f);
            Drawer.text(name ,positionX,y+Sc.screenH*.07f, width);
        }

        private void renderProduction(float y){
            Drawer.texture(Assets.instance.menu.square,positionX, y+Sc.screenH*.22f, width, Sc.screenH*.22f,0, new Vector3(.8f,.8f,.8f),1);
            productionCost.render(new Vector2(positionX, y+Sc.screenH*.22f),Sc.screenH*.22f, true);
            Drawer.texture(Assets.instance.menu.square,positionX, y, width, Sc.screenH*.22f,0, new Vector3(.7f,.7f,.7f),1);
            productionIncome.render(new Vector2(positionX, y),Sc.screenH*.22f, false);
            Drawer.texture(Assets.instance.menu.flecheBleu, positionX+width*.15f, y+Sc.screenH*.15f, Sc.screenH*.1f, Sc.screenH*.14f, 0);
            Drawer.setFontScale(.6f);
            Drawer.text(time, positionX+width*.5f, y+Sc.screenH*.25f);
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
                if(touched && !trueDrag && !buyButton.touchUp(input)){
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
}