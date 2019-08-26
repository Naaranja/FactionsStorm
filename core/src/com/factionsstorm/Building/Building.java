package com.factionsstorm.Building;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Sc;
import com.factionsstorm.State.Village.VillageManager;
import com.factionsstorm.Tool.Amount;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Tool.Trajectory;

public abstract class Building {

    protected Vector2 position, renderPosition;
    protected int dim;
    protected boolean naval=false;
    protected String name;
    protected int level;
    protected Amount amount = new Amount();

    private VillageManager vm;
    public boolean touched=false, selected=false, dragged=false, dragging=false;
    private Vector2 dragInitCoord=null;

    protected Animation animation;
    public TextureRegion texture;
    private Tile[] tiles;
    public UI ui=null;

    public Building(Vector2 position){
        this.position=position;
        renderPosition=new Vector2(position);
    }

    public void setVM(VillageManager villageManager){this.vm=villageManager;}

    public void update(){
        if(dragged) {
            tiles = new Tile[dim * dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    tiles[i * dim + j] = new Tile(new Vector2(renderPosition.x + i, renderPosition.y + j));
                }
            }

            vm.setSortingNeed();
        }
    }

    public void render(){
        if(selected){
            Drawer.texture(texture,renderPosition.x-1,renderPosition.y+1,dim*(float)Math.sqrt(2),dim*(float)Math.sqrt(6),-45,.5f);
        }else {
            Drawer.texture(texture, renderPosition.x - 1, renderPosition.y + 1, dim * (float) Math.sqrt(2), dim * (float) Math.sqrt(6), -45);
        }
        if(dragged){
            for(Tile tile : tiles){
                tile.render();
            }
        }
    }

    public void touchDown(Vector3 input){
        if(touch(input)){
            touched=true;
            if(selected){
                dragging=true;
                dragged=true;
            }
        }
    }

    public void touchUp(Vector3 input, boolean trueDrag){
        if(touched && touch(input)){
            setSelected(true);
            vm.setSelectedBuilding(this);
        }else if(!dragging && !trueDrag){
            setSelected(false);
        }
        dragging=false;
        touched=false;
    }

    public void drag(Vector3 input){
        if(dragInitCoord==null){
            dragInitCoord=new Vector2(input.x,input.y);
        }else{
            renderPosition.x=Math.min(Math.max(position.x+(int)(input.x-dragInitCoord.x),0),36-dim);
            renderPosition.y=Math.min(Math.max(position.y+(int)(input.y-dragInitCoord.y),0),36-dim);
        }

    }

    public boolean touch(Vector3 input){
        return (input.x>renderPosition.x && input.x<renderPosition.x+dim && input.y>renderPosition.y && input.y<renderPosition.y+dim);
    }

    public void setSelected(boolean selected){
        if(!this.selected && selected) {
            createUI();
        }else if(this.selected && !selected){
            dragInitCoord=null;
            if(dragged) {
                if(valideLocation()) {
                    setPosition();
                }else{
                    renderPosition = new Vector2(position);
                }
                dragged=false;
            }

            if(ui!=null) ui.setClosed();
        }
        this.selected=selected;
    }

    public void setPosition(){
        position = new Vector2(renderPosition);
    }

    public Vector2 getRenderPosition(){
        return renderPosition;
    }
    public int getZIndex(){
        return (int)(renderPosition.x+renderPosition.y+dim);
    }
    public String getName(){ return name; }
    public int getLevel(){ return level; }
    public Amount getAmount(){
        return amount;
    }
    public boolean isDragging(){
        return dragging;
    }

    public boolean valideLocation(){
        update();
        for(Tile tile : tiles){
            if(!tile.valid) return false;
        }
        return true;
    }

    private class Tile{
        private Vector2 position;
        private boolean valid;

        private Tile(Vector2 position){
            this.position=position;
            valid=true;
            for (Building building : vm.buildings) {
                if (building != Building.this) {
                    if (intersect(building) || wrongGround()) {
                        valid=false;
                    }
                }
            }
        }

        private void render(){
            Vector3 color;
            if(valid){
                color = new Vector3(0,1,0);
            }else{
                color = new Vector3(1,0,0);
            }
            Drawer.texture(Assets.instance.village.tile,position.x,position.y,1,1,0, color, .3f);
        }

        private boolean intersect(Building building){
            return(position.x>=building.position.x && position.x<building.position.x+dim && position.y>=building.position.y && position.y<building.position.y+dim);
        }

        private boolean wrongGround(){
            if(Building.this.naval){
                if(position.x>2 || position.y>2) return true;
            }else{
                if(position.x<3 || position.y<3) return true;
            }
            return false;
        }
    }

    public abstract void createUI();

    public class UI {

        public Button[] buttons;
        public final float BUTTON_POS_Y=Sc.screenH*.05f, BUTTON_WIDTH=Sc.screenH*.18f, BUTTON_HEIGHT=Sc.screenH*.18f;

        public void update(){
            for(Button button : buttons){
                button.update();
                button.setAlpha(Math.max(0,1-2*(BUTTON_POS_Y-button.getPosition().y)/(BUTTON_POS_Y+BUTTON_HEIGHT)));
            }
        }

        public void render(){
            if(!dragging) {
                for (Button button : buttons) {
                    button.render();
                }
            }
        }

        public boolean touchDown(Vector3 input){
            for(Button button : buttons){
                if(button.touchDown(input)){
                    return true;
                }
            }
            return false;
        }

        public boolean touchUp(Vector3 input){
            for(Button button : buttons){
                if(button.touchUp(input)){
                    return true;
                }
            }
            return false;
        }

        public void setClosed(){
            for(Button button : buttons){
                Trajectory trajectory = new Trajectory(button.getPosition(), new Vector2(button.getPosition().x,-button.getDim().y), .25f){
                    @Override
                    public float evolution(float t){
                        return (float)((1-Math.exp(3*t))/(1-Math.exp(3)));
                    }
                };
                button.setTrajectory(trajectory);
            }
        }

        public void setButtons(Button[] buttons){
            this.buttons=buttons;
        }
    }
}