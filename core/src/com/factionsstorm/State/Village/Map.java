package com.factionsstorm.State.Village;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Tool.Dragger;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Sc;

public class Map{

    public OrthographicCamera camera;
    private Matrix4 matrix;
    public Dragger dragger;
    final Plane xyPlane = new Plane(new Vector3(0, 0, 1), 0);

    private VillageManager vm;

    private final float leftLimit=-50.85f, topLimit=83.8f, rightLimit=50.35f, botLimit=-3.65f;

    public Map(VillageManager villageManager){
        this.vm=villageManager;
    }

    public void create() {
        camera = new OrthographicCamera(Sc.W, Sc.H);
        camera.position.set(0, 0, 18);
        camera.direction.set(1, 1, -1);
        camera.rotate(60);
        camera.near = 1;
        camera.far = 1000;

        matrix = new Matrix4();
        matrix.setToRotation(new Vector3(1, 0, 0), 0);

        dragger = new Dragger();

        for(Building building : vm.buildings){
            building.setVM(vm);
        }

        vm.setSortingNeed();
    }

    public void update(){
        Vector2 d = dragger.getMotion();
        d.x/=(float)Math.sqrt(2)/camera.zoom;
        d.y*=(float)Math.sqrt(6)/2*camera.zoom;
        d.x*=Sc.W/Sc.screenW;
        d.y*=Sc.H/Sc.screenH;
        camera.position.add(d.y-d.x,d.y+d.x,0);

        camera.update();

        Vector3 topLeftAngle=new Vector3();
        Ray pickRay = camera.getPickRay(0, 0);
        Intersector.intersectRayPlane(pickRay, xyPlane, topLeftAngle);
        if(topLeftAngle.x - topLeftAngle.y < leftLimit){
            float dif=(leftLimit - (topLeftAngle.x - topLeftAngle.y))*.5f;
            camera.position.add(dif,-dif,0);
        }
        if(topLeftAngle.x + topLeftAngle.y > topLimit){
            float dif=(topLimit - (topLeftAngle.x + topLeftAngle.y))*.5f;
            camera.position.add(dif,dif,0);
        }
        Vector3 botRightAngle=new Vector3();
        pickRay = camera.getPickRay(Sc.screenW, Sc.screenH);
        Intersector.intersectRayPlane(pickRay, xyPlane, botRightAngle);
        if(botRightAngle.x - botRightAngle.y > rightLimit){
            float dif=(rightLimit - (botRightAngle.x - botRightAngle.y))*.5f;
            camera.position.add(dif,-dif,0);
        }
        if(botRightAngle.x + botRightAngle.y < botLimit){
            float dif=(botLimit - (botRightAngle.x + botRightAngle.y))*.5f;
            camera.position.add(dif,dif,0);
        }

        camera.update();
    }

    public void render() {
        Drawer.batch.setProjectionMatrix(camera.combined);
        Drawer.batch.setTransformMatrix(matrix);

        //map
        Drawer.texture(Assets.instance.village.village, -27.25f, 23.6f,2*25.3f*(float) Math.sqrt(2), 25.3f*(float) Math.sqrt(6), -45);

        //buildings
        for (Building building : vm.buildings) {
            building.render();
        }
        if(vm.constructionBuilding!=null){
            vm.constructionBuilding.render();
        }
    }

    public void dispose() {

    }

    public void touchDown(float x, float y, int pointer){
        dragger.add(new Vector2(x,y),pointer);

        if(Sc.fingers==1) {
            Vector3 input = new Vector3();
            Ray pickRay = camera.getPickRay(x, y);
            Intersector.intersectRayPlane(pickRay, xyPlane, input);
            if (vm.constructionBuilding != null) {
                if(vm.constructionBuilding.touch(input)){
                    vm.constructionBuilding.touched=true;
                    vm.constructionBuilding.dragging=true;
                }
            }else{
                for (Building building : vm.buildings) {
                    building.touchDown(input);
                }
            }
        }
    }

    public void touchUp(float x, float y, int pointer){
        if(Sc.fingers==0) {
            Vector3 input=new Vector3();
            Ray pickRay = camera.getPickRay(x, y);
            Intersector.intersectRayPlane(pickRay, xyPlane, input);
            if(vm.constructionBuilding!=null){
                vm.constructionBuilding.dragging=false;
            }else {
                for (Building building : vm.buildings) {
                    building.touchUp(input, dragger.isTrueDrag());
                }
                vm.updateSelectedBuilding();
            }
        }

        dragger.remove(pointer);
    }

    public void drag(float x, float y, int pointer){
        if(Sc.fingers==1 && vm.selectedBuilding!=null && vm.selectedBuilding.isDragging()) {
            Vector3 input=new Vector3();
            Ray pickRay = camera.getPickRay(x, y);
            Intersector.intersectRayPlane(pickRay, xyPlane, input);
            vm.selectedBuilding.drag(input);
        }else{
            dragger.update(new Vector2(x,y), pointer);
            camera.zoom+=dragger.zoom()*camera.zoom*.05f;
            camera.zoom=Math.min(Math.max(camera.zoom,.5f),1.6f);
        }
    }
}