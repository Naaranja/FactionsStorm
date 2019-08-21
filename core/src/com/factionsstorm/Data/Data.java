package com.factionsstorm.Data;

import com.badlogic.gdx.math.Vector2;
import com.factionsstorm.Building.Building;
import com.factionsstorm.Building.Dwelling.*;

import java.util.ArrayList;

public class Data {

    public static class Own {
        static ArrayList<Building> buildings;

        public static ArrayList<Building> getBuildings() {
            buildings = new ArrayList<Building>();
            buildings.add(new HutLv1(new Vector2(3,3),0));
            buildings.add(new HutLv1(new Vector2(6,6),0));
            return buildings;
        }

    }

}
