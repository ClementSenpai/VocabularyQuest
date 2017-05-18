package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Clément on 11/05/2017.
 */
public class MapScreen implements Screen {

    Skin skin;
    VocabularyQuest game;
    Stage stage;
    final TextButton button_up;
    final TextButton button_down;
    final TextButton button_left;
    final TextButton button_right;
    final TextButton battle;
    Label hp;
    Label coord;

    SpriteBatch batch;
    Sprite wall_tiles = new Sprite(new Texture("tiles/wall64.png"));
    Sprite heros_sprite = new Sprite(new Texture("tiles/character.png"));
    final int SQUARE_SIZE = 64;
    final int SQUARE_LINE = 10;
    final int BUTTON_SIZE = 100;

    public MapScreen(final VocabularyQuest game){
        batch = new SpriteBatch();
        this.game = game;


        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));


        Table table=new Table();
        //table.setSize(800,480);
        table.setSize(Gdx.graphics.getWidth() - SQUARE_LINE * SQUARE_LINE,Gdx.graphics.getHeight());
        table.bottom();


        button_up =new TextButton("^",skin);
        table.add(button_up ).width(BUTTON_SIZE).height(BUTTON_SIZE).padLeft(BUTTON_SIZE);
        table.row();
        button_left =new TextButton("<",skin);
        table.add(button_left ).width(BUTTON_SIZE).height(BUTTON_SIZE).padRight(BUTTON_SIZE);
        button_right =new TextButton(">",skin);
        table.add(button_right).width(BUTTON_SIZE).height(BUTTON_SIZE);
        table.row();
        button_down =new TextButton("v",skin);
        table.add(button_down ).width(BUTTON_SIZE).height(BUTTON_SIZE).padLeft(BUTTON_SIZE);
        table.row();

        battle =new TextButton("battle",skin);
        table.add(battle);//.width(400).height(100);
        table.row();

        Table tableStat=new Table();
        tableStat.setSize(200,2000);

        hp =new Label(game.heros.getHp() + " / " +game.heros.getMax_hp() + " HP",skin);
        tableStat.add(hp).width(100).height(100);
        tableStat.row();

        coord =new Label("Coord : X "+ game.heros.getCoord_x() + " Y " + game.heros.getCoord_y(),skin);
        tableStat.add(coord).width(100).height(100).padTop(5);
        tableStat.row();





        stage.addActor(table);
        stage.addActor(tableStat);
        battle.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //battle.addAction(Actions.fadeOut(0.7f));

                //game.battleScreen = new BattleScregen(game);
                game.heros.setCoord_x(game.heros.getCoord_x()+1);
                game.setScreen(new BattleScreen(game));
            }
        });

        button_left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.heros.setCoord_x(game.heros.getCoord_x()-1);
            }
        });

        button_up.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.heros.setCoord_y(game.heros.getCoord_y()-1);
            }
        });

        button_down.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.heros.setCoord_y(game.heros.getCoord_y()+ 1);
            }
        });

        button_right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.heros.setCoord_x(game.heros.getCoord_x()+ 1);
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        float squareWidth = camera.viewportWidth / squaresOnWidth;
//        float squareHeight = camera.viewportHeight / squaresOnHeight;
//        wall_tiles.setWidth(squareWidth);
//        wall_tiles.setHeight(squareHeight);
        batch.begin();
        int pad = (Gdx.graphics.getWidth()-SQUARE_LINE*SQUARE_SIZE) /2;
        int height = Gdx.graphics.getHeight();
        for (int y = 0 ; y < SQUARE_LINE; y++) {
            for (int x = 0; x < SQUARE_LINE ; x++) {
                wall_tiles.setX(pad + x * SQUARE_SIZE);
                wall_tiles.setY(height - pad - (y+1) * SQUARE_SIZE);
                wall_tiles.draw(batch);
            }
        }

        heros_sprite.setX(pad + game.heros.getCoord_x() * SQUARE_SIZE);
        heros_sprite.setY(height - pad - (game.heros.getCoord_y()+1) * SQUARE_SIZE);
        heros_sprite.draw(batch);

        batch.end();

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