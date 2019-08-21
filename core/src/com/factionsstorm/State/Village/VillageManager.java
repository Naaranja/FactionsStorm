package com.factionsstorm.State.Village;

import com.factionsstorm.Building.Building;

import java.util.ArrayList;

public class VillageManager {
    public ArrayList<Building> buildings;
    public Building selectedBuilding=null, constructionBuilding=null;
    private boolean sortingNeed=false;

    public VillageManager(VillageParameter villageParameter){
        buildings=villageParameter.buildings;
    }

    public void update(){
        if(constructionBuilding!=null){
            constructionBuilding.update();
        }
        for(Building building : buildings){
            building.update();
        }
        if(sortingNeed){
            sortBuildings();
            sortingNeed=false;
        }
    }

    public void setSelectedBuilding(Building building){
        if(building != selectedBuilding) {
            if (selectedBuilding != null) selectedBuilding.setSelected(false);
            selectedBuilding = building;
        }
    }

    public void updateSelectedBuilding(){
        for(Building building : buildings){
            if(building.selected){
                return;
            }
        }
        setSelectedBuilding(null);
    }

    public void build(){
        if(constructionBuilding!=null && constructionBuilding.valideLocation()){
            constructionBuilding.getAmount().pay();
            constructionBuilding.setPosition();
            constructionBuilding.setSelected(false);
            constructionBuilding.touched=false;
            constructionBuilding.dragged=false;
            constructionBuilding.dragging=false;
            buildings.add(constructionBuilding);
            constructionBuilding=null;
        }
    }

    public void setSortingNeed(){
        sortingNeed=true;
    }

    public void setConstructionBuilding(Building cb){
        setSelectedBuilding(cb);
        constructionBuilding=cb;
        if(cb!=null) {
            constructionBuilding.selected = true;
            constructionBuilding.dragged = true;
        }
    }

    private void sortBuildings(){
        ArrayList<ArrayList<Building>> tab = new ArrayList<ArrayList<Building>>();
        for(int z=0;z<72;z++){tab.add(new ArrayList<Building>());}
        for (Building building : buildings) {
            if(building!=selectedBuilding) tab.get(building.getZIndex()).add(building);
        }
        if(selectedBuilding!=null && selectedBuilding!=constructionBuilding) tab.get(Math.max(0,selectedBuilding.getZIndex())).add(selectedBuilding);
        buildings.clear();
        for(int z=71;z>=0;z--){
            if(tab.get(z).size()>0){
                for(Building building : tab.get(z)){
                    buildings.add(building);
                }
            }
        }
    }
}
