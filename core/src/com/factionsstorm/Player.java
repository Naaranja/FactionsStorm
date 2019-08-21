package com.factionsstorm;

import java.util.HashMap;

public class Player {

    public static final Player instance = new Player();

    public enum Commodities{fcoin,wood,oil,iron,bronze,aluminium,gold,uraniumn,plasma,ruby}

    private int level=0;
    private HashMap<Commodities, Integer> ressources;

    public Player(){
        ressources = new HashMap<Commodities, Integer>();
        ressources.put(Commodities.fcoin,1000);
        ressources.put(Commodities.wood,0);
        ressources.put(Commodities.oil,0);
        ressources.put(Commodities.iron,0);
        ressources.put(Commodities.bronze,0);
        ressources.put(Commodities.aluminium,0);
        ressources.put(Commodities.gold,0);
        ressources.put(Commodities.uraniumn,0);
        ressources.put(Commodities.plasma,0);
        ressources.put(Commodities.ruby,0);
    }

    public void add(Commodities commoditie, int value){
        ressources.put(commoditie,ressources.get(commoditie)+value);
    }

    public int get(Commodities commoditie){
        return ressources.get(commoditie);
    }

    public int getLevel(){
        return level;
    }

}
