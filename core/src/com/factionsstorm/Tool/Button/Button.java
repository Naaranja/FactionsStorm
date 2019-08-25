package com.factionsstorm.Tool.Button;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.factionsstorm.Assets;
import com.factionsstorm.Tool.Drawer;
import com.factionsstorm.Tool.Trajectory;

public class Button {

    Vector2 position, dim, renderPosition, renderDim;
    Trajectory trajectory = null;
    TextureRegion textureRegion=null;
    private Vector3 color = new Vector3(.1f,.4f,.6f);
    private float alpha=1;
    private boolean touched=false;
    protected boolean locked=false;
    protected float scale=1;

    private final float MIN_SCALE=.9f;

    public Button(Vector2 position, Vector2 dim, TextureRegion textureRegion){
        this.position=position;
        this.dim=dim;
        this.textureRegion=textureRegion;
        renderPosition=new Vector2(position);
        renderDim=new Vector2(dim);
    }

    public Button(Vector2 position, Vector2 dim, TextureRegion textureRegion, Vector3 color){
        this.position=position;
        this.dim=dim;
        this.textureRegion=textureRegion;
        this.color=color;
        renderPosition=new Vector2(position);
        renderDim=new Vector2(dim);
    }

    public void update(){
        if(trajectory!=null){
            position=trajectory.getPosition();
            if(trajectory.isFinished())trajectory=null;
        }
        if(touched){
            scale=MIN_SCALE+(scale-MIN_SCALE)*.8f;
        }else{
            scale=1-(1-scale)*.8f;
        }
        renderDim.x=dim.x*scale;
        renderDim.y=dim.y*scale;
        renderPosition.x=position.x+(1-scale)/2*dim.x;
        renderPosition.y=position.y+(1-scale)/2*dim.y;
    }

    public void render(){
        Vector3 renderColor = locked ? new Vector3(color.x*.3f,color.y*.3f,color.z*.3f) : color;
        Drawer.texture(Assets.instance.menu.square, renderPosition.x, renderPosition.y, renderDim.x, renderDim.y, 0, renderColor, alpha);
        Drawer.texture(textureRegion, renderPosition.x+renderDim.x*.1f, renderPosition.y+renderDim.y*.1f, renderDim.x*.8f, renderDim.y*.8f, 0, locked ? new Vector3(.3f, .3f, .3f) : new Vector3(1,1,1), alpha);
        Drawer.texture(Assets.instance.menu.leftTriangle, renderPosition.x-renderDim.x*.05f, renderPosition.y, renderDim.x*.05f, renderDim.y, 0, renderColor, alpha);
        Drawer.texture(Assets.instance.menu.rightTriangle, renderPosition.x+renderDim.x, renderPosition.y, renderDim.x*.05f, renderDim.y, 0, renderColor, alpha);
    }

    public boolean touchDown(Vector3 input){
        if(touch(input)){
            setTouched(true);
            return true;
        }
        return false;
    }

    public boolean touchUp(Vector3 input){
        if(touched && touch(input)){
            action();
            setTouched(false);
            return true;
        }
        setTouched(false);
        return false;
    }

    public void action(){}

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void setLocked(boolean locked){
        this.locked=locked;
    }

    private boolean touch(Vector3 input){
        return (input.x>position.x && input.x<position.x+dim.x && input.y>position.y && input.y<position.y+dim.y);
    }

    public void setPosition(Vector2 position){
        this.position=position;
    }

    public void setDim(Vector2 size){
        this.dim=size;
    }

    public void setTrajectory(Trajectory trajectory){ this.trajectory=trajectory; }
    public void setAlpha(float alpha){ this.alpha=alpha; }

    public Vector2 getPosition(){ return position; }
    public Vector2 getDim(){ return dim; }
}
