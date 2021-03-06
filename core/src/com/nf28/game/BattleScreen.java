package com.nf28.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.nf28.model.Monster;
import com.nf28.model.Vocabulary;
import com.nf28.ressource.HerosTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clément on 11/05/2017.
 */
public class BattleScreen implements Screen {

    Skin skin;
    SpriteBatch batch;
    Sprite background;
    VocabularyQuest game;
    Stage stage;
    Table table;
    Table tableCheat;
    final TextButton map;
    //final TextButton objet;
    //final TextButton magie;
    Label badguyLifeLabel ;
    Label goodguyLifeLabel ;
    Label desc ;
    Label currentWordLabel ;
    Table answerTable;
    Vocabulary vocab = new Vocabulary();
    BlinkImage goodguyImage = new BlinkImage();
    BlinkImage badguyImage = new BlinkImage();
    String currentWord;
    int badguylife=5;
    int badguyMaxHP=5;
    boolean isAttacking=true;
    Monster monster;
    Map<TextButton,AnswerListener> answer_list= new HashMap<TextButton,AnswerListener>();

    public BattleScreen(final VocabularyQuest game){
        this.game = game;
        try {
            vocab = FileLoader.loadDefaultList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        batch = new SpriteBatch();
        monster = new Monster(game.floor);
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
//        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
        skin = new Skin( Gdx.files.internal( "skin/craftacular/skin/craftacular-ui.json" ));
        background = new Sprite(new Texture("background/bckp_defaut.png"));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        currentWord = vocab.getAttaqueWord();
        currentWordLabel = new Label(currentWord,skin);
        desc = new Label("Attack",skin);
        table = new Table();
        table.setFillParent(true);
        goodguyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(game.heros.getImageUrl()))));

        badguyImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(monster.getImageUrl()))));
        badguylife=monster.getVie();
        badguyMaxHP=monster.getVie();
        goodguyImage.setScaling(Scaling.fit);
        badguyImage.setScaling(Scaling.fit);
        goodguyLifeLabel = new Label("",skin);
        badguyLifeLabel = new Label("",skin);
        table.add(goodguyLifeLabel).expandX();
        table.add(badguyLifeLabel).expandX();
        table.row();
        table.add(goodguyImage).height(Value.percentHeight(.35F, table)).width(Value.percentWidth(.50F, table)).fill();
        table.add(badguyImage).width(Value.percentWidth(.50F, table)).fill();
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
        map =new TextButton("Flee",skin);
        table.add(map).colspan(2).expandX();
//        table.row();

        //tableCheat= new Table();
        //tableCheat.setFillParent(true);
        //tableCheat.bottom();
       // map =new TextButton("Flee",skin);
       // map.setWidth(Gdx.graphics.getWidth());
       // table.add(map).colspan(2).expandX();
        //tableCheat.add(map).expandX();
        //tableCheat.row();
        //objet=new TextButton("Item",skin);
        //objet.setWidth(Gdx.graphics.getWidth());
        //tableCheat.add(objet);
        //tableCheat.row();
        //magie=new TextButton("Skill",skin);
        //magie.setWidth(Gdx.graphics.getWidth());
       // tableCheat.add(magie);
        //table.add(tableCheat).fill();
        stage.addActor(table);
        //stage.addActor(tableCheat);
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
        System.out.println("attk:"+isAttacking+" current:"+currentWord+" voc:"+vocab.get(currentWord));
        System.out.println(i);
        if (isAttacking) {
            if (vocab.get(currentWord).equals(i)) {
                badguylife -= HerosTemplate.attaque[game.heros.getLevel()];
                badguyImage.activate();
            }
            currentWord = vocab.getDefenceWord();
        }else{
            if (!vocab.get(i).equals(currentWord)) {
                game.heros.setHp(game.heros.getHp() - monster.getAttaque());
                goodguyImage.activate();
            }
            currentWord = vocab.getAttaqueWord();

        }
        isAttacking = !isAttacking;
        refresh();

    }
    public void refresh(){
        if(isAttacking)
            desc.setText("Attack");
        else
            desc.setText("Defense");
        currentWordLabel.setText("  " + currentWord);
        currentWordLabel.setWrap(true);

        badguyLifeLabel.setText(badguylife+"/"+badguyMaxHP);
        goodguyLifeLabel.setText(game.heros.getHp()+"/"+game.heros.getMax_hp());
        String answers[] =null;
        if(isAttacking)
            answers = vocab.getResponseAttaque(currentWord,4);
        else
            answers = vocab.getResponseDefence(currentWord,4);

        int cpt=0;
        for(Map.Entry<TextButton,AnswerListener> e : answer_list.entrySet()){
            System.out.println(answers[cpt]);
            e.getKey().setText(answers[cpt]);
            e.getKey().getLabel().setWrap(true);
            e.getValue().answer = answers[cpt++];
        }


        if(badguylife<=0){
            game.heros.setExp(game.heros.getExp() + monster.getExp());
            if(game.heros.getExp() >= HerosTemplate.palier_exp[game.heros.getLevel() ])
                game.heros.levelUp();
            game.setOr(game.getOr() + monster.getOr() );

            Preferences prefs = Gdx.app.getPreferences("cfg");
            prefs.putInteger("gold", game.getOr());
            prefs.flush();
            game.setScreen(new MapScreen(game));
            game.dispose();
        }
        if(game.heros.getHp()<=0)
        {
            game.setScreen(new GameOverScreen(game));
            game.dispose();
        }

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255,255,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
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
    class AnswerListener extends  ClickListener{
        public String answer;
        public void clicked(InputEvent event, float x, float y) {
            buttonClicked(answer);
        }
    }
}
