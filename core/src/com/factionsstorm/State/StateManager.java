package com.factionsstorm.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.factionsstorm.Sc;

import java.util.LinkedList;

public class StateManager implements InputProcessor {

    public static StateManager instance = new StateManager();
    private LinkedList<State> states = new LinkedList<State>();

    public void setState(State newState){
        for(State state : states){
            state.dispose();
        }
        states.clear();
        newState.create();
        states.add(newState);
    }

    public void open(State newState){
        newState.create();
        states.add(newState);
    }

    public void exit(){
        states.getLast().dispose();
        states.removeLast();
    }

    public void update(){
        Gdx.input.setInputProcessor(this);
        states.getLast().update();
    }

    public void render(){
        states.getLast().render();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        Sc.fingers++;
        states.getLast().touchDown(x,y,pointer);
        updateFingerNumber();
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        Sc.fingers--;
        states.getLast().touchUp(x,y,pointer);
        updateFingerNumber();
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        states.getLast().touchDragged(x,y,pointer);
        return false;
    }

    private void updateFingerNumber(){
        final int MAX_NUMBER_OF_POINTERS = 20;
        Sc.fingers = 0;
        for(int i = 0; i < MAX_NUMBER_OF_POINTERS; i++) {
            if( Gdx.input.isTouched(i) ) Sc.fingers++;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}