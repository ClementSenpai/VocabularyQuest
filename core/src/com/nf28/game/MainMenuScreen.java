package com.nf28.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    Skin basicSkin;
    Skin selectSkin;
    Stage stage;
    VocabularyQuest game;
    Label bestfloor;

    SpriteBatch batch;

    Sprite main_screen = new Sprite(new Texture("img/ecrantitre.png"));


    public MainMenuScreen(final VocabularyQuest game){
        final int WIDTH_BUTTON = Gdx.graphics.getWidth()*6/10;
        final int HEIGHT_BUTTON = Gdx.graphics.getWidth()*15/100;
        this.game = game;
        batch = new SpriteBatch();
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
//        skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));

        skin = new Skin( Gdx.files.internal( "skin/craftacular/skin/craftacular-ui.json" ));
        //selectSkin = new Skin( Gdx.files.internal( "skin/extras/skin/extras-ui.json" ));
        basicSkin  = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
        main_screen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bestfloor = new Label("Best floor : " + game.bestfloor ,skin);

        Table table=new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.bottom();


        table.add(bestfloor).padBottom(Gdx.graphics.getHeight()/10);
        table.row();

        final SelectBox<String> listVoc = new SelectBox<String>(basicSkin);
        Array<String> lists = FileLoader.loadFilesNames();
        listVoc.setItems(lists);

        table.add(listVoc).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/15).top();
        table.row();

        final TextButton startGame=new TextButton("Start game",skin);
        table.add(startGame).width(WIDTH_BUTTON).height(HEIGHT_BUTTON);
        table.row();

        TextButton options=new TextButton("Shop",skin);
        table.add(options).width(WIDTH_BUTTON).height(HEIGHT_BUTTON).padTop(HEIGHT_BUTTON/10).padBottom(HEIGHT_BUTTON/10);
        table.row();

        TextButton credits=new TextButton("Import",skin); //Why credits ??
        table.add(credits).width(WIDTH_BUTTON).height(HEIGHT_BUTTON);
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
                            }
                        }
                    }
                };
                // If emulator use Local StoragePath
                //files.setDirectory(new FileHandle(Gdx.files.getLocalStoragePath()));
                files.setDirectory(new FileHandle(Gdx.files.getExternalStoragePath()));
                files.getBackground().setMinHeight(Gdx.graphics.getHeight()/3);  //TODO Scale with phone resolution
                files.getBackground().setMinWidth(Gdx.graphics.getWidth());
                files.show(stage);
            }
        });

        listVoc.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               final FileHandleView fileOptions = new FileHandleView("Select an action", skin, listVoc.getSelected()){
                   @Override
                   protected void result(final Object object){
                       if (object.equals("Load")){
                           FileSaver.saveDefaultFile(listVoc.getSelected());
                       }
                       else if (object.equals("Remove")){
                           ErrorDialog errorDialog = new ErrorDialog("Warning", skin, new Label("Do you really want to remove " + listVoc.getSelected(), skin)) {
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
                           errorDialog.getBackground().setMinHeight(240);
                           errorDialog.show(getStage());
                       }
                       else if (object.equals("Rename")){
                           final RenameDialog renameDialog = new RenameDialog("Rename your file", skin, listVoc.getSelected());
                           renameDialog.showTextField();
                           renameDialog.getBackground().setMinWidth(240);
                           renameDialog.getBackground().setMinHeight(150);
                           renameDialog.show(stage);
                           listVoc.setItems(FileLoader.loadFilesNames());
                       }

                   }
               };
               fileOptions.showFileName();
               fileOptions.getBackground().setMinWidth(Gdx.graphics.getWidth()/4);
               fileOptions.getBackground().setMinHeight(Gdx.graphics.getHeight()/8);
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
