package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Clément on 11/05/2017.
 */
public class BattleScreen implements Screen {


    Skin skin;
    VocabularyQuest game;
    Stage stage;
    Table table;
    final TextButton map;

    public BattleScreen(final VocabularyQuest game){
        this.game = game;

        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));


        table = new Table();
        table.setSize(800,480);

        map =new TextButton("Map",skin);
        table.add(map).width(400).height(300);
        table.row();





        stage.addActor(table);

        map.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //map.addAction(Actions.fadeOut(0.7f));
                game.setScreen(new MapScreen(game));
                game.dispose();
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       /* if (map.isPressed()) {
            game.setScreen(game.mapScreen);
            dispose();
        }*/
        stage.act(delta);
        stage.draw();
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

    }
}
