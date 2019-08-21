package com.factionsstorm.State.Village;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Sc;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Drawer;

import java.util.ArrayList;

public abstract class UI {

    OrthographicCamera camera;
    Matrix4 matrix;

    VillageManager vm;

    ArrayList<Button> buttons = new ArrayList<Button>();

    public void setVM(VillageManager vm){
        this.vm=vm;
    }

    public void create() {
        camera = new OrthographicCamera(Sc.screenW, Sc.screenH);
        camera.position.set(Sc.screenW/2, Sc.screenH/2, 18);
        camera.direction.set(0, 0, -1);
        camera.near = 1;
        camera.far = 1000;

        matrix = new Matrix4();
        matrix.setToRotation(new Vector3(1, 0, 0), 0);
    }
    public void update(OrthographicCamera camera){
        for(Button button : buttons){
            button.update();
        }
    }
    public void render(){
        Drawer.batch.setProjectionMatrix(camera.combined);
        Drawer.batch.setTransformMatrix(matrix);

        for(Button button : buttons){
            button.render();
        }

        camera.update();
    }

    public boolean touchDown(float x, float y, int pointer){
        Vector3 input = camera.unproject(new Vector3(x, y, 0));
        for(Button button : buttons){
            if(button.touchDown(input)){
                return true;
            }
        }
        return false;
    }

    public boolean touchUp(float x, float y, int pointer){
        Vector3 input = camera.unproject(new Vector3(x, y, 0));
        for(Button button : buttons){
            if(button.touchUp(input)){
                return true;
            }
        }
        return false;
    }

    public void reset(){
        for(Button button : buttons){
            button.setTouched(false);
        }
    }
}
