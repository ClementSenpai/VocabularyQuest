package com.nf28.game;

import com.badlogic.gdx.Game;
import com.nf28.model.Heros;

public class VocabularyQuest extends Game {
    MainMenuScreen mainMenuScreen;
    ShopScreen shopScreen;
    MapScreen mapScreen;
    BattleScreen battleScreen;
    Heros heros;


    @Override
    public void create () {
        heros = new Heros();
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
}
