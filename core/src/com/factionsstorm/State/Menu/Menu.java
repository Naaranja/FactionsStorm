package com.factionsstorm.State.Menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Sc;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Dragger;
import com.factionsstorm.Tool.Drawer;

public abstract class Menu{

    protected OrthographicCamera fixedCamera, camera;
    protected Matrix4 matrix;
    protected Dragger dragger;

    private Button exitButton;
    private boolean dragable=true;
    protected float limitX;

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

        exitButton = new Button(new Vector2(Sc.screenW-Sc.screenH*.17f, Sc.screenH*.83f), new Vector2(Sc.screenH*.14f,Sc.screenH*.14f), Assets.instance.menu.buttonQuitterSeul, new Vector3(.8f,.2f,.2f)){
            @Override
            public void action(){
                StateManager.instance.exit();
            }
        };
    }

    public void update() {
        exitButton.update();

        if(dragable) {
            Vector2 d = dragger.getMotion();
            camera.position.add(-d.x, 0, 0);
            if (camera.position.x > limitX - Sc.screenW / 2)
                camera.position.x = limitX - Sc.screenW / 2;
            if (camera.position.x < Sc.screenW / 2) camera.position.x = Sc.screenW / 2;
        }
        fixedCamera.update();
        camera.update();
    }

    public void render() {
        Drawer.texture(Assets.instance.menu.square,0,0,Sc.screenW,Sc.screenH,0, new Vector3(0,.3f,.5f),1);
        exitButton.render();
    }

    public void dispose() {

    }

    public boolean touchDown(int x, int y, int pointer) {
        dragger.add(new Vector2(x,y),pointer);

        Vector3 input=fixedCamera.unproject(new Vector3(x,y,0));
        if(exitButton.touchDown(input))return true;
        return false;
    }

    public boolean touchUp(int x, int y, int pointer) {
        Vector3 input=fixedCamera.unproject(new Vector3(x,y,0));
        exitButton.touchUp(input);
        setDragable(true);
        dragger.remove(pointer);
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        dragger.update(new Vector2(x,y), pointer);
        return false;
    }

    public void setDragable(boolean dragable){
        this.dragable=dragable;
    }
}