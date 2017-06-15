package com.nf28.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nf28.model.Heros;
import com.nf28.model.Map;


public class VocabularyQuest extends Game {
    MainMenuScreen mainMenuScreen;
    MapScreen mapScreen;
    Heros heros;
    Map map;
    int floor=0;
    int bestfloor;
    int or;

    @Override
    public void create () {
        Preferences prefs = Gdx.app.getPreferences("cfg");
        heros = new Heros();
        heros.setImageUrl(prefs.getString("skin","character/character.png"));
        or = prefs.getInteger("gold",0);
        bestfloor = prefs.getInteger("bestfloor",0);
        map = new Map(floor);
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }


    @Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {

	}

    public int getOr() {
        return or;
    }

    public void setOr(int or) {
        this.or = or;
    }


    public void resetGame(){
        heros = new Heros();
        floor = 0;
        map = new Map(floor);

    }

    public void updateMap(int x, int y){
        map.updateMap(x,y);
    }
    public void displayMap(Batch b){
        map.displayMap(b);
    }
}
