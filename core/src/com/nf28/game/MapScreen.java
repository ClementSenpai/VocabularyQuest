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
import com.nf28.model.Map;
import com.nf28.model.Tiles;
import com.nf28.model.Transition;

/**
 * Created by Clément on 11/05/2017.
 */
public class MapScreen implements Screen {

    Skin skin;
    VocabularyQuest game;
    Stage stage;
    Transition transition;
    boolean transition_start;

    final TextButton button_up;
    final TextButton button_down;
    final TextButton button_left;
    final TextButton button_right;
    final TextButton battle;
    Label hp;
    Label coord;

    SpriteBatch batch;
    Sprite heros_sprite;
    Sprite wall_tiles = new Sprite(new Texture("tiles/wall64.png"));
    final int BUTTON_SIZE = 100;
    final int SQUARE_SIZE = Gdx.graphics.getWidth()/11 ;
    final int SQUARE_LINE = 11;


    public MapScreen(final VocabularyQuest game){
        batch = new SpriteBatch();
        transition = new Transition();
        transition_start = false;
        this.game = game;
        heros_sprite = new Sprite(new Texture(game.heros.getImageUrl()));
        heros_sprite.setSize(Gdx.graphics.getWidth()/11,Gdx.graphics.getWidth()/11);
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));


        Table table=new Table();
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


        game.heros.setCoord_x(game.map.getHeros_coord_x());
        game.heros.setCoord_y(game.map.getHeros_coord_y());


        stage.addActor(table);
        stage.addActor(tableStat);
        battle.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                transition_start = true;
                //game.heros.setCoord_x(game.heros.getCoord_x()+1);
               // game.setScreen(new BattleScreen(game));
            }
        });

        button_left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.heros.checkMove( game.heros.getCoord_x()-1, game.heros.getCoord_y(), game.map))
                game.heros.setCoord_x(game.heros.getCoord_x()-1);
            }
        });

        button_up.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.heros.checkMove( game.heros.getCoord_x(), game.heros.getCoord_y()-1, game.map))
                game.heros.setCoord_y(game.heros.getCoord_y()-1);
            }
        });

        button_down.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.heros.checkMove( game.heros.getCoord_x(), game.heros.getCoord_y()+1, game.map))
                game.heros.setCoord_y(game.heros.getCoord_y()+ 1);
            }
        });

        button_right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.heros.checkMove( game.heros.getCoord_x()+1, game.heros.getCoord_y(), game.map))
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

        batch.begin();


            int pad = (Gdx.graphics.getWidth() - SQUARE_LINE * SQUARE_SIZE) / 2;
            int height = Gdx.graphics.getHeight();

            heros_sprite.setX(pad + game.heros.getCoord_x() * SQUARE_SIZE);
            heros_sprite.setY(height - pad - (game.heros.getCoord_y() + 1) * SQUARE_SIZE);


            game.updateMap(game.heros.getCoord_x(), game.heros.getCoord_y());
            game.displayMap(batch);

            heros_sprite.draw(batch);

            if (game.map.tiles[game.heros.getCoord_x()][game.heros.getCoord_y()].isMonster()) {
                game.map.tiles[game.heros.getCoord_x()][game.heros.getCoord_y()].setMonster(false);
                game.map.setHeros_coord_x(game.heros.getCoord_x());
                game.map.setHeros_coord_y(game.heros.getCoord_y());
                game.setScreen(new BattleScreen(game));
            }

        if (game.map.tiles[game.heros.getCoord_x()][game.heros.getCoord_y()].getStatus() == Tiles.Status.nextfloor) {
            game.floor++;
            game.map = new Map(game.floor);
            game.setScreen(new MapScreen(game));
        }


        if (transition.getI() < 255)
        {
            transition.black_fadein(batch);
        }
        else if (transition_start && transition.getJ() > 0)
        {
            transition.black_fadeout(batch);
        }
        else if (transition_start)
        {
            game.setScreen(new BattleScreen(game));
        }

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
