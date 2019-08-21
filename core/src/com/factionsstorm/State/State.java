package com.factionsstorm.State;

public interface State {
    void create();
    void update();
    void render();
    void dispose();
    boolean touchDown(int x, int y, int pointer);
    boolean touchUp(int x, int y, int pointer);
    boolean touchDragged(int x, int y, int pointer);
}
