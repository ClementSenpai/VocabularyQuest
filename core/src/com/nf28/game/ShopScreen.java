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
    int currentPage = 0;
    List<String> listUrl = new ArrayList<String>();
    List<String> listPrice = new ArrayList<String>();
    List<String> listName = new ArrayList<String>();
    List<Label> nameLabelList = new ArrayList<Label>();
    List<Label> priceLabelList = new ArrayList<Label>();
    List<Image> imageList = new ArrayList<Image>();


    Table table;

    public ShopScreen(final VocabularyQuest game){
        this.game = game;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
        table = new Table();
        table.setFillParent(true);
        FileHandle file = Gdx.files.internal("character/character_list.json");
        String text = file.readString();
        table.setDebug(true);
        JsonValue json = new JsonReader().parse(text);
        JsonValue jsonobj = json.get("character");
        int cpt=1;
        for (JsonValue o : jsonobj.iterator()) {
            listUrl.add(o.getString("url"));
            listPrice.add("" + o.getInt("price"));
            listName.add(o.getString("name"));
        }
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                Image image = new Image();
                //test.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("character/" + o.getString("url")))));
                image.setScaling(Scaling.fit);
                table.add(image).fill().expand().colspan(2);
                imageList.add(image);
            }
            table.row();
            for(int j=0;j<3;j++) {
                Label nameLabel = new Label("name",skin);
                table.add(nameLabel).fillX().expandX();
                nameLabelList.add(nameLabel);
                Label priceLabel = new Label("name",skin);
                //test.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("character/" + o.getString("url")))));
                table.add(priceLabel).fillX().expandX();
                priceLabelList.add(priceLabel);
            }
            table.row();
        }
        refresh(currentPage);

        TextButton button_left =new TextButton("<",skin);
        table.add(button_left).height(100).expand().fill();
        TextButton button_right =new TextButton(">",skin);
        table.add(button_right).height(100).expand().fill();
        final TextButton retour =new TextButton("Retour",skin);
        table.add(retour).expand().fill();
        table.row();
        retour.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retour.addAction(Actions.fadeOut(0.7f));
                game.setScreen(new MainMenuScreen(game));
                game.dispose();
            }
        });
        button_left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPage-1>=0){
                    currentPage--;
                    refresh(currentPage);
                }

            }
        });
        button_right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPage+1<=listPrice.size()/9) {
                    currentPage++;
                    refresh(currentPage);
                }
            }
        });
        stage.addActor(table);



    }
    public void refresh(int page){
        for(Image i : imageList)
            i.setDrawable(null);
        for(Label l:nameLabelList)
            l.setText("");
        for(Label l:priceLabelList)
            l.setText("");
        for(int i=0;i<9;i++){
            if(page * 9 + i == listPrice.size())
                break;
            imageList.get(i).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("character/" + listUrl.get(page * 9 + i)))));
            String url=listUrl.get(page * 9 + i);
            imageList.get(i).addListener(new buyListener(url));
            nameLabelList.get(i).setText(listName.get(page * 9 + i));
            priceLabelList.get(i).setText(listPrice.get(page * 9 + i));
        }
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
    class buyListener extends ClickListener{
        String url;
        public buyListener(String url){
            this.url=url;
        }
        @Override
        public void clicked(InputEvent event, float x, float y) {
           game.heros.setImageUrl("character/"+this.url);
        }
    }
}
