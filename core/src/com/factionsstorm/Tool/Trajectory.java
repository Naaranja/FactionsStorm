package com.factionsstorm.Tool;

import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Sc;

public class Trajectory {

    private Vector2 start, end;
    private double startTime;
    private float duration;
    private boolean finished=false;

    public Trajectory(Vector2 start, Vector2 end, float duration){
        this.start=start;
        this.end=end;
        this.duration=duration;
        startTime= Sc.time;
    }

    public Vector2 getPosition(){
        float t=(float)(Sc.time-startTime)/duration;
        if(t>1){
            finished=true;
            return end;
        }
        float r=evolution(t);
        return new Vector2(end.x*r+start.x*(1-r),end.y*r+start.y*(1-r));
    }

    public boolean isFinished(){
        return finished;
    }

    //A fonction such as f(0)=0 and f(1)=1
    //Can be override
    public float evolution(float t){
        return (float)((1-Math.exp(-3*t))/(1-Math.exp(-3)));
    }
}
