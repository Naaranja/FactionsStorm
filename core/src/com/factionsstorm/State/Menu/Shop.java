package com.factionsstorm.State.Menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Building.Dwelling.Hut1;
import com.factionsstorm.Building.Producer.Sawmill1;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.State.State;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.State.Village.VillageManager;
import com.factionsstorm.Tool.Amount;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Dragger;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Tool.Selector;

import java.util.ArrayList;

public class Shop extends Menu implements State {

    private VillageManager vm;
    private Selector selector;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Shop(VillageManager vm){
        this.vm=vm;
    }

    @Override
    public void create() {
        super.create();

        selector = new Selector(Sc.screenW*.3f);
        selector.add(new Selector.Tab(Assets.instance.icon.ressources[0]){
            @Override
            public void action(){ initDwelings(); }
        });
        selector.add(new Selector.Tab(Assets.instance.icon.ressources[1]){
            @Override
            public void action(){ initProducers(); }
        });
        selector.add(new Selector.Tab(Assets.instance.icon.ressources[2]));
        selector.add(new Selector.Tab(Assets.instance.icon.ressources[3]));
        selector.add(new Selector.Tab(Assets.instance.icon.ressources[4]));

        initDwelings();
    }

    @Override
    public void update() {
        super.update();
        selector.update();
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

        selector.render();

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
        Vector3 input=fixedCamera.unproject(new Vector3(x,y,0));
        if(selector.touchDown(input))touched=true;

        input=camera.unproject(new Vector3(x,y,0));
        for(Item item : items){
            if(item.touchDown(input))touched=true;

        }
        setDragable(!touched);
        return touched;
    }

    public boolean touchUp(int x, int y, int pointer) {
        Vector3 input=fixedCamera.unproject(new Vector3(x,y,0));
        selector.touchUp(input);

        input=camera.unproject(new Vector3(x,y,0));
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

    private void initDwelings(){
        items.clear();
        items.add(new Item(vm, new Hut1(new Vector2(),0), Sc.screenW*.01f));
        items.add(new Item(vm, new Hut1(new Vector2(),0), Sc.screenW*0.22f));
        items.add(new Item(vm, new Hut1(new Vector2(),0), Sc.screenW*0.43f));
        items.add(new Item(vm, new Hut1(new Vector2(),0), Sc.screenW*0.64f));
        items.add(new Item(vm, new Hut1(new Vector2(),0), Sc.screenW*0.85f));
        items.add(new Item(vm, new Hut1(new Vector2(),0), Sc.screenW*1.06f));
        limitX = items.get(items.size() - 1).positionX + Sc.screenW * .21f;
        setVM();
    }

    private void initProducers(){
        items.clear();
        items.add(new Item(vm, new Sawmill1(new Vector2(),0), Sc.screenW*.01f));
        items.add(new Item(vm, new Sawmill1(new Vector2(),0), Sc.screenW*0.22f));
        items.add(new Item(vm, new Sawmill1(new Vector2(),0), Sc.screenW*0.43f));
        limitX = items.get(items.size() - 1).positionX + Sc.screenW * .21f;
        setVM();
    }

    private void setVM(){
        for(Item item : items){
            item.building.setVM(vm);
        }
    }

    public class Item {

        private VillageManager vm;
        public Building building;
        public Amount amount;
        public float positionX;
        private final float width=Sc.screenW*.2f;
        private boolean locked, touched=false, selected=false;
        Button buyButton;

        //TODO buyButton -> TextButton

        private Item(final VillageManager vm, final Building building, float positionX){
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

        private void update(){
            buyButton.update();
        }

        private void render(){
            if(locked){
                Drawer.texture(Assets.instance.menu.square, positionX, 0, width, Sc.screenH * .5f, 0, new Vector3(.8f, .8f, .8f), 1);
                renderName("???", Sc.screenH*.5f);
                renderTexture(true, Sc.screenH*.58f);
            }else {
                if (!selected) {
                    renderAmount(0);
                    renderDescription(Sc.screenH * .25f);
                    renderName(building.getName(), Sc.screenH * .44f);
                    renderTexture(false, Sc.screenH * .52f);
                } else {
                    Drawer.texture(Assets.instance.menu.square, positionX, 0, width, Sc.screenH * .28f, 0, new Vector3(.7f, .7f, .7f), 1);
                    buyButton.render();
                    renderAmount(Sc.screenH * .28f);
                    renderDescription(Sc.screenH * .53f);
                    renderName(building.getName(), Sc.screenH * .72f);
                }
            }
        }

        private void renderTexture(boolean locked, float y){
            if(locked) {
                Drawer.texture(Assets.instance.menu.bottomRightTriangle, positionX, y+Sc.screenH*.28f, width, Sc.screenH*.015f, 0, new Vector3(.7f, .7f, .7f), 1);
                Drawer.texture(Assets.instance.menu.square, positionX, y, width, Sc.screenH *.28f, 0, new Vector3(.7f, .7f, .7f), 1);
                Drawer.texture(building.textureFixe, positionX + (Sc.screenW * .2f - Sc.screenH * .3f) * .5f, y + Sc.screenH * .02f, Sc.screenH * .3f, Sc.screenH * .3f, 0, new Vector3(0,0,0), 1);
            }else{
                Drawer.texture(Assets.instance.menu.bottomRightTriangle, positionX, y+Sc.screenH*.28f, width, Sc.screenH*.015f, 0, new Vector3(.7f, .8f, .2f), 1);
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
            Drawer.texture(Assets.instance.menu.square,positionX,y, width, Sc.screenH*.19f,0, new Vector3(.7f,.7f,.7f),1);
        }

        private void renderAmount(float y){
            Drawer.texture(Assets.instance.menu.square,positionX,y, width, Sc.screenH*.25f,0, new Vector3(.8f,.8f,.8f),1);
            amount.render(new Vector2(positionX, y), Sc.screenH*.25f);
        }

        private boolean touchDown(Vector3 input){
            if(touch(input)){
                touched=true;
                if(selected && buyButton.touchDown(input)){
                    return true;
                }
            }
            return false;
        }

        private boolean touchUp(Vector3 input, boolean trueDrag){
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
}