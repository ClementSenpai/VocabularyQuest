package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.nf28.model.Vocabulary;

/**
 * Created by Clément on 11/05/2017.
 */
public class BattleScreen implements Screen {


    Skin skin;
    VocabularyQuest game;
    Stage stage;
    Table table;
    final TextButton map;
    final TextArea badguyLife ;
    final TextArea goodguyLife ;
    final TextArea desc ;
    TextArea currentWordTextArea ;
    Table answerTable;
    Vocabulary vocab = new Vocabulary();
    Image goodguyImage = new Image();
    Image badguyImage = new Image();
    String currentWord;

    public BattleScreen(final VocabularyQuest game){
        this.game = game;
        currentWord =vocab.getWord();
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
        badguyLife = new TextArea("Bad Guy Life",skin);
        goodguyLife = new TextArea("Good Guy Life",skin);
        currentWordTextArea = new TextArea(currentWord,skin);
        desc = new TextArea("Attack/Defense",skin);
        table = new Table();
        table.debug();
        table.setFillParent(true);
        goodguyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("tiles/character.png"))));
        badguyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("tiles/character.png"))));
        goodguyImage.setScaling(Scaling.fit);
        badguyImage.setScaling(Scaling.fit);
        table.add(goodguyLife).expandX();
        table.add(badguyLife).expandX();
        table.row();
        table.add(goodguyImage).expand().fill();
        table.add(badguyImage).expand().fill();
        table.row();
        table.add(desc).colspan(2).expandX();
        table.row();
        table.add(currentWordTextArea).height(Value.percentWidth(.30F, table)).expand().fill();
        answerTable = new Table();

        for(String i: vocab.getResponse(currentWord,4)){
            answerTable.add(new TextButton(i,skin)).expand().fill();
            answerTable.row();
        }
        table.add(answerTable).expand().fill();
        table.row();
        map =new TextButton("Map",skin);
        table.add(map);
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
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       /* if (map.isPressed()) {
            game.setScreen(game.mapScreen);
            dispose();
        }*/
        stage.act(delta);
        stage.draw();
        stage.setDebugAll(true);
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
