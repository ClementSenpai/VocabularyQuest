package com.nf28.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.nf28.model.Vocabulary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clément on 11/05/2017.
 */
public class BattleScreen implements Screen {


    Skin skin;
    VocabularyQuest game;
    Stage stage;
    Table table;
    final TextButton map;
    Label badguyLifeLabel ;
    Label goodguyLifeLabel ;
    Label desc ;
    Label currentWordLabel ;
    Table answerTable;
    Vocabulary vocab = new Vocabulary();
    Image goodguyImage = new Image();
    Image badguyImage = new Image();
    String currentWord;
    int badguylife=5;
    int badguyMaxHP=5;
    boolean isAttacking=true;
    Map<TextButton,AnswerListener> answer_list= new HashMap<TextButton,AnswerListener>();

    public BattleScreen(final VocabularyQuest game){
        this.game = game;
        stage=new Stage();

        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
        currentWord = vocab.getWord();
        currentWordLabel = new Label(currentWord,skin);
        desc = new Label("Attack",skin);
        table = new Table();
        table.debug();
        table.setFillParent(true);
        goodguyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("tiles/character.png"))));
        badguyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("tiles/character.png"))));
        goodguyImage.setScaling(Scaling.fit);
        badguyImage.setScaling(Scaling.fit);
        goodguyLifeLabel = new Label("",skin);
        badguyLifeLabel = new Label("",skin);
        table.add(goodguyLifeLabel).expandX();
        table.add(badguyLifeLabel).expandX();
        table.row();
        table.add(goodguyImage).height(Value.percentHeight(.35F, table)).expandX().fill();
        table.add(badguyImage).expandX().fill();;
        table.row();
        table.add(desc).colspan(2).expandX();
        table.row();
        table.add(currentWordLabel).height(Value.percentHeight(.25F, table)).expandX().fill();
        answerTable = new Table();
        for(int i=0;i<4;i++) {
            TextButton tb = new TextButton("", skin);
            answerTable.add(tb).expand().fill();
            AnswerListener al =  new AnswerListener();
            answer_list.put(tb, al);
            answerTable.row();
            tb.addListener(al);
        }
        table.add(answerTable).fill();
        table.row();
        map =new TextButton("Map",skin);
        table.add(map).colspan(2).expandX();
        stage.addActor(table);
        refresh();

            map.addListener(new ClickListener() {
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

    public void buttonClicked(String i){
            if (isAttacking && vocab.get(currentWord).equals(i))
            badguylife--;
        else if(!isAttacking && !vocab.get(currentWord).equals(i))
            game.heros.setHp(game.heros.getHp()-1);
        isAttacking = !isAttacking;
        currentWord = vocab.getWord();
        refresh();

    }
    public void refresh(){
        if(isAttacking)
            desc.setText("Attack");
        else
            desc.setText("Defense");
        currentWordLabel.setText(currentWord);
        badguyLifeLabel.setText(badguylife+"/"+badguyMaxHP);
        goodguyLifeLabel.setText(game.heros.getHp()+"/"+game.heros.getMax_hp());
        String[] answers = vocab.getResponse(currentWord,4);
        int cpt=0;
        for(Map.Entry<TextButton,AnswerListener> e : answer_list.entrySet()){
            e.getKey().setText(answers[cpt]);
            e.getValue().answer = answers[cpt++];
        }


        if(badguylife==0){
            game.setScreen(new MapScreen(game));
            game.dispose();
        }
        if(game.heros.getHp()==0)
        {
            game.setScreen(new MainMenuScreen(game));
            game.dispose();
        }

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
    class AnswerListener extends  ClickListener{
        public String answer;
        public void clicked(InputEvent event, float x, float y) {
            buttonClicked(answer);
        }
    }
}
