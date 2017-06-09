package com.nf28.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.nf28.model.Vocabulary;

import java.io.IOException;

/**
 * Created by Clément on 11/05/2017.
 */
public class MainMenuScreen implements Screen {

    Skin skin;
    Stage stage;
    VocabularyQuest game;


    public MainMenuScreen(final VocabularyQuest game){
        this.game = game;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));



        Table table=new Table();
        table.setSize(800, 480);

        final SelectBox<String> listVoc = new SelectBox<String>(skin);
        Array<String> lists = FileLoader.loadFilesNames();
        listVoc.setItems(lists);
        table.add(listVoc).width(200).height(100).left().top();
        table.row();

        final TextButton startGame=new TextButton("Start game",skin);
        table.add(startGame).width(400).height(100);
        table.row();

        TextButton options=new TextButton("Shop",skin);
        table.add(options).width(400).height(100).padTop(10).padBottom(3);
        table.row();

        TextButton credits=new TextButton("Import",skin); //Why credits ??
        table.add(credits).width(400).height(100);
        table.row();


        stage.addActor(table);
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ShopScreen(game));
            }
        });

        startGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.getRoot().getColor().a = 1;
                SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(Actions.fadeOut(0.5f));
                game.mapScreen = new MapScreen(game);
                game.setScreen(game.mapScreen);
            }
        });

        credits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileChooser files = new FileChooser("Choose The List File", skin) {
                    @Override
                    protected void result(Object object) {
                        if (object.equals("OK")) {
                            FileHandle file = getFile();
                            Gdx.app.setLogLevel(Application.LOG_DEBUG);
                            if (file != null) {
                                FileSaver.saveFile(file);
                                listVoc.setItems(FileLoader.loadFilesNames());
                                try {
                                    Vocabulary listeVocabulaire = FileLoader.parseFile(file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Gdx.app.log(" FileChooser ", "You have chosen this file : " + file.name());
                            }
                        }
                    }
                };
                // If emulator use Local StoragePath
                files.setDirectory(new FileHandle(Gdx.files.getLocalStoragePath()));
                //files.setDirectory(new FileHandle(Gdx.files.getExternalStoragePath()));
                files.getBackground().setMinHeight(640);  //TODO Scale with phone resolution
                files.getBackground().setMinWidth(480);
                files.show(stage);
            }
        });

        listVoc.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               FileHandleView fileOptions = new FileHandleView("Select an action", skin, Gdx.files.local(listVoc.getSelected())){
                   @Override
                   protected void result(Object object){
                       if (object.equals("Load")){
                           FileSaver.saveDefaultFile(listVoc.getSelected());
                       }
                       else if (object.equals("Remove")){
                           ErrorDialog errorDialog = new ErrorDialog("Error", skin, new Label("Do you really want to remove " + listVoc.getSelected(), skin)) {
                               @Override
                               protected void result(Object object) {
                                   if (object.equals("OK")){
                                       FileSaver.removeFile(listVoc.getSelected());
                                       listVoc.setItems(FileLoader.loadFilesNames());
                                   }
                               }
                           };
                           errorDialog.showMessage();
                           errorDialog.getBackground().setMinWidth(150);
                           errorDialog.getBackground().setMinHeight(100);
                           errorDialog.show(getStage());
                       }

                   }
               };
               fileOptions.getBackground().setMinWidth(320);
               fileOptions.getBackground().setMinHeight(240);
               fileOptions.show(stage);
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
