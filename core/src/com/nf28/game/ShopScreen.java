package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clément on 11/05/2017.
 */
public class ShopScreen implements Screen {

    Skin skin;
    VocabularyQuest game;
    Stage stage;

    public ShopScreen(final VocabularyQuest game){
        this.game = game;

        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));


        final Table table = new Table();
        table.setFillParent(true);


        FileHandle file = Gdx.files.internal("character/character_list.json");
        String text = file.readString();


        table.setDebug(true);
        JsonValue json = new JsonReader().parse(text);
        JsonValue jsonobj = json.get("character");
        int cpt=1;
        List<Integer> price = new ArrayList<Integer>();
        List<String> name = new ArrayList<String>();
        for (JsonValue o : jsonobj.iterator())
        {
            if(cpt==13)
                break;
            Image test = new Image();
            test.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("character/"+o.getString("url")))));
            test.setScaling(Scaling.fit);
            table.add(test).fill().expand().colspan(2);
            price.add(o.getInt("price"));
            name.add(o.getString("name"));
            if(cpt++%3==0){
                table.row();
                table.add(new Label(name.get(0),skin)).expandX();
                table.add(new Label("" + price.get(0), skin)).expandX();
                table.add(new Label(name.get(1),skin)).expandX();
                table.add(new Label("" + price.get(1), skin)).expandX();
                table.add(new Label(name.get(2),skin)).expandX();
                table.add(new Label("" + price.get(2), skin)).expandX();
                table.row();
                name.remove(0);
                name.remove(0);
                name.remove(0);
                price.remove(0);
                price.remove(0);
                price.remove(0);
            }

        }

        final TextButton retour =new TextButton("Retour",skin);
        table.add(retour).expandX().fillX();
        table.row();
        retour.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retour.addAction(Actions.fadeOut(0.7f));
                game.setScreen(game.mainMenuScreen);
            }
        });
        stage.addActor(table);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
