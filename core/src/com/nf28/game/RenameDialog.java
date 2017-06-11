package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Kovalsky2099 on 11/06/2017.
 */

public class RenameDialog extends Dialog {

    private Skin skin;
    private TextField fileNameField;
    private TextButton okButton;
    private TextButton backButton;
    private boolean hasChanged;
    private String previousFileName;

    public String getfileNameField(){ return fileNameField.getText();}
    
    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public RenameDialog(String title, final Skin skin, final String file_name){
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.previousFileName = file_name;

        this.setModal(true);
        this.skin = skin;

        this.okButton = new TextButton("OK", skin);
        this.backButton = new TextButton("Back", skin);

        this.okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                ErrorDialog errorDialog = new ErrorDialog(
                        "Warning", skin, new Label(
                        "Do you really want to rename " + previousFileName + " as " + fileNameField.getText(), skin)) {
                    @Override
                    protected void result(Object object) {
                        if (object.equals("OK")) {
                            FileSaver.renameFile(previousFileName, fileNameField.getText());
                        }
                    }
                };
                if (hasChanged) {
                    errorDialog.showMessage();
                    errorDialog.getBackground().setMinWidth(150);
                    errorDialog.getBackground().setMinHeight(240);
                    errorDialog.show(getStage());
                }
            }
        });

        this.backButton.addListener(new ClickListener() {
           @Override
            public void clicked(InputEvent event, float x, float y) {
               hide();
           }
        });

        this.fileNameField = new TextField(file_name, skin);
        this.fileNameField.setDisabled(false);

        this.fileNameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hasChanged = true;
            }
        });
    }
    
    public void showTextField() {
        final Table table = new Table().top().left();
        table.defaults().left();
        table.row();
        table.add(fileNameField).expandX().fillX();
        table.row();
        table.add(backButton).width(180);
        table.add(okButton).expandX().width(180);
        this.getContentTable().add(table).maxWidth(400).expand().fill();
    }
}
