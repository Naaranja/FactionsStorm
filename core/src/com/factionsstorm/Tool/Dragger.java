package com.factionsstorm.Tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dragger {

    Map<Integer, ArrayList<Vector2>> fingers = new HashMap<Integer, ArrayList<Vector2>>();
    ArrayList<Vector2> globalMotion = new ArrayList<Vector2>();
    private Vector2 speed=new Vector2();
    final int MAX_MOTION=5;

    boolean trueDrag=false;
    Vector2 firstPosition;

    public void update(Vector2 input, int pointer){
        if(fingers.containsKey(pointer)){
            ArrayList<Vector2> position=fingers.get(pointer);
            position.add(0, input);
            while(position.size()>2){
                position.remove(2);
            }

            if(distance(firstPosition,input)>Gdx.graphics.getWidth()*.05f){
                trueDrag=true;
            }
        }
        if(fingers.size()!=Sc.fingers){
            reset();
        }
    }

    public Vector2 getMotion(){
        int fingersNbr=fingers.size();
        if(fingersNbr==0){
            speed.x*=.9f;
            speed.y*=.9f;
            return new Vector2(speed);
        }else if(fingersNbr>0){
            Vector2 motion = new Vector2();
            for(ArrayList<Vector2> position : fingers.values()){
                motion.x+=position.get(0).x-position.get(1).x;
                motion.y+=position.get(0).y-position.get(1).y;
            }
            motion.x/=fingersNbr;
            motion.y/=fingersNbr;

            globalMotion.add(0,motion);
            while(globalMotion.size()>MAX_MOTION){
                globalMotion.remove(MAX_MOTION);
            }

            return new Vector2(motion);
        }
        return new Vector2();
    }

    public float zoom(){
        if(fingers.size()!=2) return 0;
        ArrayList<ArrayList<Vector2>> vectors = new ArrayList<ArrayList<Vector2>>(fingers.values());
        float lastDistance=distance(vectors.get(0).get(1),vectors.get(1).get(1));
        float distance=distance(vectors.get(0).get(0),vectors.get(1).get(0));
        return (lastDistance-distance)*Sc.W/Sc.screenW;
    }

    public void add(Vector2 input, int pointer) {
        if (fingers.isEmpty()) {
            firstPosition = new Vector2(input);
        }
        fingers.put(pointer, new ArrayList<Vector2>());
        fingers.get(pointer).add(input);
        fingers.get(pointer).add(input);
    }

    public void remove(int pointer){
        if(!fingers.isEmpty()) {
            fingers.remove(pointer);
            if (fingers.isEmpty()) {
                if(globalMotion.size()<MAX_MOTION) {
                    speed = new Vector2();
                }else{
                    Vector2 average = new Vector2();
                    for (Vector2 motion : globalMotion) {
                        average.x += motion.x;
                        average.y += motion.y;
                    }
                    average.x /= MAX_MOTION;
                    average.y /= MAX_MOTION;
                    speed = average;
                }
                globalMotion.clear();
                trueDrag = false;
            }
        }
    }

    public void reset(){
        fingers.clear();
        speed=new Vector2();
    }

    public boolean isTrueDrag(){
        return trueDrag;
    }

    private float distance(Vector2 v1, Vector2 v2){
        return (float)Math.sqrt((v1.x-v2.x)*(v1.x-v2.x)+(v1.y-v2.y)*(v1.y-v2.y));
    }
}
