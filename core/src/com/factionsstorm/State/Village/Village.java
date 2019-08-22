package com.factionsstorm.State.Village;

import com.factionsstorm.State.State;
import com.factionsstorm.Tool.Drawer;

public class Village implements State {

    VillageManager vm;
    UI ui;
    Map map;

    boolean uiTouched=false;

    public Village(VillageParameter villageParameter, UI ui){
        vm = new VillageManager(villageParameter);
        map = new Map(vm);
        this.ui=ui;
        this.ui.setVM(vm);
    }

    public void create() {
        ui.create();
        map.create();
    }

    public void update(){
        vm.update();
        map.update();
        ui.update(map.camera);
    }

    public void render() {
        Drawer.batch.begin();
        map.render();
        ui.render();
        Drawer.batch.end();
    }

    public void dispose() {

    }

    public boolean touchDown(int x, int y, int pointer) {
        uiTouched=ui.touchDown(x,y,pointer);
        if(!uiTouched)map.touchDown(x,y,pointer);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer) {
        if(uiTouched){
            ui.touchUp(x,y,pointer);
        }else{
            map.touchUp(x,y,pointer);
        }
        uiTouched=false;
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        if(!uiTouched) {
            map.drag(x, y, pointer);
        }
        return false;
    }
}