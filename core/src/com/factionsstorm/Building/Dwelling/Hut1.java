package com.factionsstorm.Building.Dwelling;

import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Assets;
import com.factionsstorm.Player;

public class Hut1 extends Dwelling{

    public Hut1(Vector2 position, double productionEndTime){
        super(position, productionEndTime);
        name="Hut Lv 1";
        level=0;
        textureFixe = Assets.instance.fixed.hutte1;
        amount.add(Player.Commodities.fcoin,100);
        amount.add(Player.Commodities.wood,100);
        amount.add(Player.Commodities.oil,100);
        amount.add(Player.Commodities.uraniumn,100);

        productionTime=3;
        productionValue=100;
    }

}