package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Clément on 11/05/2017.
 */
public class MainMenuScreen implements Screen {

    Skin skin;
    Stage stage;
    VocabularyQuest game;


    public MainMenuScreen(final VocabularyQuest game){
        this.game = game;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));

        Table table=new Table();
        table.setSize(800,480);

        final TextButton startGame=new TextButton("Start game",skin);
        table.add(startGame).width(400).height(100);
        table.row();

        TextButton options=new TextButton("Shop",skin);
        table.add(options).width(400).height(100).padTop(10).padBottom(3);
        table.row();

        TextButton credits=new TextButton("Import",skin);
        table.add(credits).width(400).height(100);
        table.row();




        stage.addActor(table);

        startGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame.addAction(Actions.fadeOut(0.7f));
                game.mapScreen = new MapScreen(game);
                game.setScreen(game.mapScreen);
            }
        });

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        //stage.setViewport(800,480,false);

    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
