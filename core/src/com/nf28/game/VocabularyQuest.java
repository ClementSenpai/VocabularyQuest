package com.nf28.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nf28.model.Heros;
import com.nf28.model.Map;

public class VocabularyQuest extends Game {
    MainMenuScreen mainMenuScreen;
    ShopScreen shopScreen;
    MapScreen mapScreen;
    BattleScreen battleScreen;
    Heros heros;
    Map map;


    @Override
    public void create () {
        heros = new Heros();
        map = new Map();
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


    public void updateMap(int x, int y){
        map.updateMap(x,y);
    }
    public void displayMap(Batch b){
        map.displayMap(b);
    }
}
