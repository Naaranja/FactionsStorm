package com.factionsstorm.Building.Producer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.State.Menu.ProducerProduction;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Button.TimerButton;
import com.factionsstorm.Tool.Trajectory;

public abstract class Producer extends Building {

    private enum State{waiting, producing, finished};
    protected TextureRegion ressourceTexture;

    protected State state;
    protected double productionEndTime;
    protected Player.Commodities commoditie;
    protected int[] productionCost = new int[8], productionIncome = new int[8];

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

    public void produce(int index){
        state=State.producing;
    }

    public void harvest(){
        state=State.waiting;
    }

    public void openProcuderProduction(){
        StateManager.instance.open(new ProducerProduction(this, commoditie, productionCost, productionIncome));
    }

    @Override
    public void createUI() {
        ui = new UI();
        Button[] buttons = new Button[1];
        buttons[0] = new TimerButton(new Vector2(Sc.screenW*.5f-ui.BUTTON_WIDTH/2,-ui.BUTTON_HEIGHT), new Vector2(ui.BUTTON_WIDTH,ui.BUTTON_HEIGHT), ressourceTexture, productionEndTime){
            @Override
            public void action(){
                if(productionEndTime<Sc.time){
                    openProcuderProduction();
                }
                ((TimerButton)ui.buttons[0]).setEndTime(productionEndTime);
            }
        };
        Trajectory trajectory = new Trajectory(buttons[0].getPosition(),new Vector2(Sc.screenW*.5f-ui.BUTTON_WIDTH/2,ui.BUTTON_POS_Y),.25f);
        buttons[0].setTrajectory(trajectory);
        ui.setButtons(buttons);
    }
}
