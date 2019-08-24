package com.factionsstorm.Building.Dwelling;

import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Assets;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Button.TimerButton;
import com.factionsstorm.Tool.Trajectory;

public abstract class Dwelling extends Building {

    protected int productionValue, productionTime;
    double productionEndTime;
    private boolean producing;

    public Dwelling(Vector2 position, double productionEndTime){
        super(position);
        dim=2;
        this.productionEndTime=productionEndTime;
    }
    @Override
    public void update(){
        super.update();
        if(producing && Sc.time>=productionEndTime){
            producing=false;
        }
    }

    public void harvest(){
        Player.instance.add(Player.Commodities.fcoin,productionValue);
        productionEndTime=Sc.time+productionTime;
    }

    @Override
    public void createUI() {
        ui = new UI();
        Button[] buttons = new Button[1];
        buttons[0] = new TimerButton(new Vector2(Sc.screenW*.5f-ui.BUTTON_WIDTH/2,-ui.BUTTON_HEIGHT), new Vector2(ui.BUTTON_WIDTH,ui.BUTTON_HEIGHT), Assets.instance.icon.ressources[0], productionEndTime){
            @Override
            public void action(){
                if(productionEndTime<Sc.time){
                    harvest();
                }
                ((TimerButton)ui.buttons[0]).setEndTime(productionEndTime);
            }
        };
        Trajectory trajectory = new Trajectory(buttons[0].getPosition(),new Vector2(Sc.screenW*.5f-ui.BUTTON_WIDTH/2,ui.BUTTON_POS_Y),.25f);
        buttons[0].setTrajectory(trajectory);
        ui.setButtons(buttons);
    }

}