package com.factionsstorm.Building.Producer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Building.Harvestable;
import com.factionsstorm.Player;
import com.factionsstorm.Sc;
import com.factionsstorm.State.Menu.ProducerProduction;
import com.factionsstorm.State.StateManager;
import com.factionsstorm.Tool.Button.Button;
import com.factionsstorm.Tool.Button.TimerButton;
import com.factionsstorm.Tool.Trajectory;

public abstract class Producer extends Harvestable {

    protected TextureRegion ressourceTexture;
    protected TextureRegion textureFixe;
    protected Animation animation;

    protected State state;
    protected double productionEndTime;
    protected Player.Commodities commoditie;
    protected int productionIndex;
    protected int[] productionCost = new int[8], productionIncome = new int[8];

    int[] times = {5, 1800, 3600, 7200, 14400, 43200, 86400, 172800};

    public Producer(Vector2 position, int productionIndex, double productionEndTime){
        super(position);
        dim=2;
        this.productionIndex=productionIndex;
        this.productionEndTime=productionEndTime;
        if(productionIndex==-1){
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

    @Override
    public void render(){
        if(state==State.producing){
            texture = (TextureRegion) animation.getKeyFrame((float)(Sc.time%10000),true);
        }else{
            texture = textureFixe;
        }
        super.render();
    }

    public void produce(int index){
        productionIndex=index;
        this.productionEndTime=Sc.time+times[index];
        if(ui!=null){
            ((TimerButton)ui.buttons[0]).setEndTime(productionEndTime);
        }
        state=State.producing;
    }

    public void harvest(){
        Player.instance.add(commoditie, productionIncome[productionIndex]);
        state=State.waiting;
        harvest(commoditie, productionIncome[productionIndex]);
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
                if(state==State.waiting){
                    openProcuderProduction();
                }else if(state==State.finished){
                    harvest();
                }

            }
        };
        Trajectory trajectory = new Trajectory(buttons[0].getPosition(),new Vector2(Sc.screenW*.5f-ui.BUTTON_WIDTH/2,ui.BUTTON_POS_Y),.25f);
        buttons[0].setTrajectory(trajectory);
        ui.setButtons(buttons);
    }
}