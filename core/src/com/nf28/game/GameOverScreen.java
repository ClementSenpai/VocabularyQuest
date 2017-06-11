package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Clément on 12/06/2017.
 */
public class GameOverScreen implements Screen {

    Skin skin;
    Stage stage;
    VocabularyQuest game;

    SpriteBatch batch;

    Sprite main_screen = new Sprite(new Texture("img/gameover.png"));


    public GameOverScreen(final VocabularyQuest game){
        final int WIDTH_BUTTON = Gdx.graphics.getWidth()*6/10;
        final int HEIGHT_BUTTON = Gdx.graphics.getWidth()*15/100;
        this.game = game;
        batch = new SpriteBatch();
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin( Gdx.files.internal( "skin/craftacular/skin/craftacular-ui.json" ));


        main_screen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Table table=new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final TextButton button_return=new TextButton("Return to main menu",skin);
        table.add(button_return);
        table.row();

        stage.addActor(table);

        button_return.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (game.bestfloor < game.floor) {
                    game.bestfloor = game.floor;
                    Preferences prefs = Gdx.app.getPreferences("cfg");
                    prefs.putInteger("bestfloor", game.bestfloor);
                    prefs.flush();
                }

                game.resetGame();
                game.setScreen(new MainMenuScreen(game));
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

        batch.begin();
        main_screen.draw(batch);
        batch.end();
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
