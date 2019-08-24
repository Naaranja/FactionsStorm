package com.factionsstorm.State.Village;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Sc;
import com.factionsstorm.State.Menu.Shop;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.Tool.Button.Button;

public class OwnUI extends UI {

    Button[] buttonsContruction = new Button[2];

    @Override
    public void create(){
        super.create();
        buttons.add(new Button(new Vector2(Sc.screenW-Sc.screenH*.25f,Sc.screenH*.05f), new Vector2(Sc.screenH*.2f,Sc.screenH*.2f), Assets.instance.icon.buttonShop){
            @Override
            public void action(){
                StateManager.instance.open(new Shop(vm));
            }
        });

        buttonsContruction[0] = new Button(new Vector2(), new Vector2(), Assets.instance.menu.buttonValiderSeul){
            @Override
            public void action(){
                if(vm.constructionBuilding.valideLocation()) {
                    vm.build();
                }
            }
        };

        buttonsContruction[1] = new Button(new Vector2(), new Vector2(), Assets.instance.menu.buttonQuitterSeul){
            @Override
            public void action(){
                vm.setConstructionBuilding(null);
            }
        };
    }

    @Override
    public void update(OrthographicCamera camera){
        super.update(camera);
        if(vm.constructionBuilding!=null){
            Vector2 position = vm.constructionBuilding.getRenderPosition();
            Vector3 projection = camera.project(new Vector3(position.x, position.y, 0));
            buttonsContruction[0].setPosition(new Vector2(projection.x-Sc.screenH*.13f/camera.zoom, projection.y+Sc.screenH*.15f/camera.zoom));
            buttonsContruction[0].setDim(new Vector2(Sc.screenH*.1f/camera.zoom,Sc.screenH*.1f/camera.zoom));
            buttonsContruction[0].update();
            buttonsContruction[1].setPosition(new Vector2(projection.x+Sc.screenH*.03f/camera.zoom, projection.y+Sc.screenH*.15f/camera.zoom));
            buttonsContruction[1].setDim(new Vector2(Sc.screenH*.1f/camera.zoom,Sc.screenH*.1f/camera.zoom));
            buttonsContruction[1].update();
        }else {
            for (Building building : vm.buildings) {
                if (building.ui != null) building.ui.update();
            }
        }
        this.camera.update();
    }

    @Override
    public void render() {
        super.render();
        if(vm.constructionBuilding!=null){
            buttonsContruction[0].render();
            buttonsContruction[1].render();
        }else {
            for (Building building : vm.buildings) {
                if (building.ui != null) building.ui.render();
            }
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        if(super.touchDown(x,y,pointer)) return true;
        Vector3 input = camera.unproject(new Vector3(x, y, 0));
        if(vm.constructionBuilding!=null){
            if(buttonsContruction[0].touchDown(input)) return true;
            if(buttonsContruction[1].touchDown(input)) return true;
        }else {
            for (Building building : vm.buildings) {
                if (building.ui != null) {
                    if (building.ui.touchDown(input)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        if(super.touchUp(x,y,pointer)) return true;
        Vector3 input = camera.unproject(new Vector3(x, y, 0));
        if(vm.constructionBuilding!=null){
            if(buttonsContruction[0].touchUp(input)) return true;
            if(buttonsContruction[1].touchUp(input)) return true;
        }else {
            for (Building building : vm.buildings) {
                if (building.ui != null) {
                    if (building.ui.touchUp(input)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
