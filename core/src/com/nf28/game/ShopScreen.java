package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.GroupLayout;

/**
 * Created by Clément on 11/05/2017.
 */
public class ShopScreen implements Screen {

    Skin skin;
    VocabularyQuest game;
    Stage stage;
    int currentPage = 0;
    Image current;
    Label gold ;
    List<String> listUrl = new ArrayList<String>();
    List<String> listPrice = new ArrayList<String>();
    List<Label> priceLabelList = new ArrayList<Label>();
    List<Image> imageList = new ArrayList<Image>();


    Table table;

    public ShopScreen(final VocabularyQuest game){
        this.game = game;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal("ui/defaultskin.json"));
        //skin = new Skin( Gdx.files.internal( "skin/craftacular/skin/craftacular-ui.json" ));
        table = new Table();
        table.setFillParent(true);
        FileHandle file = Gdx.files.internal("character/character_list.json");
        String text = file.readString();
        JsonValue json = new JsonReader().parse(text);
        JsonValue jsonobj = json.get("character");
        for (JsonValue o : jsonobj.iterator()) {
            listUrl.add(o.getString("url"));
            listPrice.add("" + o.getInt("price"));
        }
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                Image image = new Image();
                image.setScaling(Scaling.fit);
                image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("tiles/white.png"))));
                table.add(image).fill().expand().colspan(2);
                imageList.add(image);
            }
            table.row();
            for(int j=0;j<3;j++) {
                Label priceLabel = new Label("name",skin);
                if(i!=0){
                    table.add(priceLabel).width(priceLabelList.get(0).getWidth());
                }else{
                    table.add(priceLabel).expand();
                }
                priceLabelList.add(priceLabel);
                Image goldimg = new Image();
                table.add(goldimg).height(20).width(20);
                goldimg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("img/gold.png"))));

            }
            table.row();
        }
        refresh(currentPage);

        TextButton button_left =new TextButton("<",skin);
        table.add(button_left).height(100).expand().fill();
        TextButton button_right =new TextButton(">",skin);
        table.add(button_right).height(100).expand().fill();
        current = new Image();
        current.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(game.heros.getImageUrl()))));
        table.add(current).colspan(2);;

        Table backtable = new Table();

        final TextButton retour =new TextButton("Retour",skin);
        gold= new Label(""+game.heros.getGold(),skin);
        backtable.add(gold).expand().fill();
        Image goldimg = new Image();
        backtable.add(goldimg);
        goldimg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("img/gold.png"))));

        backtable.row();
        backtable.add(retour).expand().fill().colspan(2);
        backtable.row();
        table.add(backtable).colspan(2).expand().fill();
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
        for(Image i : imageList) {
            i.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("character/alpha.png"))));
            for(com.badlogic.gdx.scenes.scene2d.EventListener e : i.getListeners())
                i.removeListener(e);
        }
        for(Label l:priceLabelList)
            l.setText("");
        for(int i=0;i<9;i++){
            if(page * 9 + i == listPrice.size())
                break;
            imageList.get(i).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("character/" + listUrl.get(page * 9 + i)))));
            String url=listUrl.get(page * 9 + i);
            imageList.get(i).addListener(new buyListener(url, imageList.get(i), Integer.parseInt(listPrice.get(page * 9 + i))));
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
        Image image;
        int price;
        public buyListener(String url,Image image,int price){
            this.url=url;
            this.image=image;
            this.price=price;

        }
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if(game.heros.getGold()>price) {
                game.heros.setImageUrl("character/" + this.url);
                game.heros.setGold(game.heros.getGold() - price);
                Preferences prefs = Gdx.app.getPreferences("cfg");
                prefs.putInteger("gold", game.heros.getGold());
                prefs.putString("skin", game.heros.getImageUrl());
                current.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(game.heros.getImageUrl()))));
                gold.setText(""+game.heros.getGold());
                prefs.flush();
            }
        }
    }
}
