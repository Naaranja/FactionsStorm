package com.factionsstorm.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Dwelling.HutLv1;
import com.factionsstorm.Sc;
import com.factionsstorm.State.Village.VillageManager;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Dragger;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Tool.Item;
import com.factionsstorm.Tool.Selector;

import java.util.ArrayList;

public class Shop implements State {

    OrthographicCamera fixedCamera, camera;
    Matrix4 matrix;
    Dragger dragger;

    VillageManager vm;
    Selector selector;
    ArrayList<Item> items = new ArrayList<Item>();
    Button exitButton;

    private boolean dragable=true;

    public Shop(VillageManager vm){
        this.vm=vm;
    }

    @Override
    public void create() {
        fixedCamera = new OrthographicCamera(Sc.screenW, Sc.screenH);
        fixedCamera.position.set(Sc.screenW/2, Sc.screenH/2, 18);
        fixedCamera.direction.set(0, 0, -1);

        camera = new OrthographicCamera(Sc.screenW, Sc.screenH);
        camera.position.set(Sc.screenW/2, Sc.screenH/2, 18);
        camera.direction.set(0, 0, -1);

        matrix = new Matrix4();
        matrix.setToRotation(new Vector3(1, 0, 0), 0);

        dragger = new Dragger();

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


        exitButton = new Button(new Vector2(Sc.screenW-Sc.screenH*.1f, Sc.screenH*.9f), new Vector2(Sc.screenH*.08f,Sc.screenH*.08f), Assets.instance.menu.buttonQuitterSeul){
            @Override
            public void action(){
                StateManager.instance.exit();
            }
        };

        initDwelings();
    }

    @Override
    public void update() {
        if(dragable) {
            Vector2 d = dragger.getMotion();
            camera.position.add(-d.x, 0, 0);
            if (camera.position.x > items.get(items.size() - 1).positionX + Sc.screenW * .21f - Sc.screenW / 2)
                camera.position.x = items.get(items.size() - 1).positionX + Sc.screenW * .21f - Sc.screenW / 2;
            if (camera.position.x < Sc.screenW / 2) camera.position.x = Sc.screenW / 2;
        }

        fixedCamera.update();
        camera.update();

        selector.update();
        exitButton.update();
        for(Item item : items){
            item.update();
        }
    }

    @Override
    public void render() {
        Drawer.batch.setProjectionMatrix(fixedCamera.combined);
        Drawer.batch.setTransformMatrix(matrix);
        Drawer.batch.begin();

        Drawer.texture(Assets.instance.menu.square,0,0,Sc.screenW,Sc.screenH,0, new Vector3(0,.3f,.5f),1);

        Drawer.setFontScale(1f);
        Drawer.text("Buildings", Sc.screenW*.01f, Sc.screenH*.99f);

        selector.render();
        exitButton.render();

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
        dragger.add(new Vector2(x,y),pointer);

        boolean touched=false;
        Vector3 input=fixedCamera.unproject(new Vector3(x,y,0));
        if(selector.touchDown(input))touched=true;
        if(exitButton.touchDown(input))touched=true;

        input=camera.unproject(new Vector3(x,y,0));
        for(Item item : items){
            if(item.touchDown(input))touched=true;

        }
        dragable=!touched;
        return touched;
    }

    public boolean touchUp(int x, int y, int pointer) {
        Vector3 input=fixedCamera.unproject(new Vector3(x,y,0));
        selector.touchUp(input);
        exitButton.touchUp(input);

        input=camera.unproject(new Vector3(x,y,0));
        for(Item item : items){
            item.touchUp(input, dragger.isTrueDrag());
        }

        dragable=true;
        dragger.remove(pointer);
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        dragger.update(new Vector2(x,y), pointer);
        return false;
    }

    private void initDwelings(){
        items.clear();
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*.01f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*0.22f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*0.43f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*0.64f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*0.85f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*1.06f));
        setVM();
    }

    private void initProducers(){
        items.clear();
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*.01f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*0.22f));
        items.add(new Item(vm, new HutLv1(new Vector2(),0), Sc.screenW*0.43f));
        setVM();
    }

    private void setVM(){
        for(Item item : items){
            item.building.setVM(vm);
        }
    }
}
