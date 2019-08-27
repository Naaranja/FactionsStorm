package com.factionsstorm.State.Village;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Building.Harvestable;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.State.Menu.Shop;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Drawer;

public class OwnUI extends UI {

    Button[] buttonsContruction = new Button[2];
    Ressources ressources;

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

        ressources = new Ressources();
    }

    @Override
    public void update(OrthographicCamera mapCamera){
        super.update(mapCamera);
        if(vm.constructionBuilding!=null){
            Vector2 position = vm.constructionBuilding.getRenderPosition();
            Vector3 projection = mapCamera.project(new Vector3(position.x, position.y, 0));
            buttonsContruction[0].setPosition(new Vector2(projection.x-Sc.screenH*.13f/mapCamera.zoom, projection.y+Sc.screenH*.15f/mapCamera.zoom));
            buttonsContruction[0].setDim(new Vector2(Sc.screenH*.1f/mapCamera.zoom,Sc.screenH*.1f/mapCamera.zoom));
            buttonsContruction[0].update();
            buttonsContruction[1].setPosition(new Vector2(projection.x+Sc.screenH*.03f/mapCamera.zoom, projection.y+Sc.screenH*.15f/mapCamera.zoom));
            buttonsContruction[1].setDim(new Vector2(Sc.screenH*.1f/mapCamera.zoom,Sc.screenH*.1f/mapCamera.zoom));
            buttonsContruction[1].update();
        }else {
            for (Building building : vm.buildings) {
                if (building.ui != null) building.ui.update();
                if(Harvestable.class.isAssignableFrom(building.getClass())){
                    ((Harvestable)building).updateParticles(mapCamera);
                }
            }
        }
        ressources.update();
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
                if(Harvestable.class.isAssignableFrom(building.getClass())){
                    ((Harvestable)building).renderParticles();
                }
            }
            for (Building building : vm.buildings) {
                if (building.ui != null) building.ui.render();
            }
        }
        ressources.render();
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
        if(ressources.touchDown(input)) return true;
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
        if(ressources.touchUp(input)) return true;
        return false;
    }

    private class Ressources{

        boolean renderAll=false;
        Res[] baseRes = new Res[5], otherRes = new Res[4];
        Button button;

        private Ressources(){
            baseRes[0] = new Res(Player.Commodities.fcoin, new Vector2(Sc.screenW*.01f, Sc.screenH*.91f));
            baseRes[1] = new Res(Player.Commodities.wood, new Vector2(Sc.screenW*.17f, Sc.screenH*.91f));
            baseRes[2] = new Res(Player.Commodities.oil, new Vector2(Sc.screenW*.33f, Sc.screenH*.91f));
            baseRes[3] = new Res(Player.Commodities.iron, new Vector2(Sc.screenW*.01f, Sc.screenH*.82f));
            baseRes[4] = new Res(Player.Commodities.plasma, new Vector2(Sc.screenW*.01f, Sc.screenH*.73f));

            otherRes[0] = new Res(Player.Commodities.copper, new Vector2(Sc.screenW*.17f, Sc.screenH*.82f));
            otherRes[1] = new Res(Player.Commodities.aluminium, new Vector2(Sc.screenW*.33f, Sc.screenH*.82f));
            otherRes[2] = new Res(Player.Commodities.gold, new Vector2(Sc.screenW*.17f, Sc.screenH*.73f));
            otherRes[3] = new Res(Player.Commodities.uranium, new Vector2(Sc.screenW*.33f, Sc.screenH*.73f));

            button = new Button(new Vector2(Sc.screenW*.16f - Sc.screenH*.04f, Sc.screenH*.82f), new Vector2(Sc.screenH*.08f, Sc.screenH*.08f), Assets.instance.icon.bouclier){
                @Override
                public void action(){
                    renderAll=!renderAll;
                }
            };
        }

        private void update(){
            for(Res res : baseRes){
                res.update();
            }
            button.update();
            if(renderAll){
                for(Res res : otherRes){
                    res.update();
                }
            }
        }

        private void render(){
            for(Res res : baseRes){
                res.render();
            }
            button.render();
            if(renderAll){
                for(Res res : otherRes){
                    res.render();
                }
            }
        }

        private boolean touchDown(Vector3 input){
            if(button.touchDown(input)) return true;
            return false;
        }

        private boolean touchUp(Vector3 input){
            if(button.touchUp(input)) return true;
            return false;
        }

        private class Res{

            Vector2 position, dim = new Vector2(Sc.screenW*.15f, Sc.screenH*.08f);
            Player.Commodities commoditie;
            TextureRegion textureRegion;
            Vector3 color = new Vector3(.1f,.4f,.6f);
            int value;
            String valueChain;

            private Res(Player.Commodities commoditie, Vector2 position){
                this.position=position;
                this.commoditie=commoditie;
                textureRegion = Assets.instance.icon.getCommoditieTexture(commoditie);
                value = Player.instance.get(commoditie);
                valueChain = Sc.formatedInt(value);
            }

            private void update(){
                int target = Player.instance.get(commoditie);
                if(value != target){
                    value += (double)(target - value)*.1f;
                    if(Math.abs(target-value)<10) value = target;
                    valueChain = Sc.formatedInt(value);
                }
            }

            private void render(){
                Drawer.texture(Assets.instance.menu.square, position.x+dim.y*.5f, position.y+dim.y*.1f, dim.x-dim.y*.5f, dim.y*.8f, 0, color, 1);
                Drawer.texture(textureRegion, position.x, position.y, dim.y, dim.y, 0);
                Drawer.setFontScale(.4f);
                Drawer.text(valueChain, position.x+dim.y*.5f, position.y+dim.y*.75f, dim.x-dim.y*.5f);
            }
        }
    }
}