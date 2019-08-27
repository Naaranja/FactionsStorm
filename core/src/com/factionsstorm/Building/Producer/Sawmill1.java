package com.factionsstorm.Building.Producer;

import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Assets;
import com.factionsstorm.Player;

public class Sawmill1 extends Producer {
    public Sawmill1(Vector2 position, int productionIndex, double productionEndTime) {
        super(position, productionIndex, productionEndTime);
        name="Scierie Lv 1";
        level=0;
        texture = Assets.instance.fixed.scierie1;
        productionTexture = Assets.instance.icon.getCommoditieTexture(Player.Commodities.wood);
        textureFixe = texture;
        animation = Assets.instance.animation.animScierieLv1;
        amount.add(Player.Commodities.fcoin,100);

        commoditie = Player.Commodities.wood;
        productionCost[0]=100;
        productionCost[1]=200;
        productionCost[2]=300;
        productionCost[3]=400;
        productionCost[4]=500;
        productionCost[5]=600;
        productionCost[6]=700;
        productionCost[7]=800;
        productionIncome[0]=10;
        productionIncome[1]=20;
        productionIncome[2]=30;
        productionIncome[3]=40;
        productionIncome[4]=50;
        productionIncome[5]=60;
        productionIncome[6]=70;
        productionIncome[7]=80;
    }
}
