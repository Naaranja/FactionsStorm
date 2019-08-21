package com.factionsstorm.Building.Producer;

import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Sc;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Button.TimerButton;

public abstract class Producer extends Building {

    private enum State{waiting, producing, finished};

    State state;
    double productionEndTime;

    public Producer(Vector2 position, double productionEndTime){
        super(position);
        dim=2;
        this.productionEndTime=productionEndTime;
        if(productionEndTime==0){
            state=State.waiting;
        }else if(productionEndTime>Sc.time){
            state=State.producing;
        }else{
            state=State.finished;
        }
    }
    @Override
    public void update(){
        super.update();
        if(state==State.producing && Sc.time>=productionEndTime){
            state=State.finished;
        }
    }

    public void produce(int production){
        state=State.producing;
    }

    public void harvest(){
        state=State.waiting;
    }

    @Override
    public void createUI() {
        Button[] buttons = new Button[1];
        buttons[0] = new TimerButton(new Vector2(), new Vector2(), Assets.instance.icon.ressources[0], productionEndTime){
            @Override
            public void action(){
                switch (state){
                    case waiting:

                        break;
                    case finished:
                        harvest();
                        break;
                }
            }
        };

        ui = new UI(){

        };
    }
}
