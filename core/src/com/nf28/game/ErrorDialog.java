package com.nf28.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by Kovalsky2099 on 09/06/2017.
 */

public class ErrorDialog extends Dialog {
    private Skin skin;
    private Label errorLabel;

    public Label getErrorLabel() { return errorLabel;}

    public  void setErrorLabel(Label error) { this.errorLabel = error; }

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public ErrorDialog(String title, Skin skin, Label label){
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Back", "Back");
        this.button("OK", "OK");

        this.errorLabel = label;
        errorLabel.setWrap(true);
        this.setModal(true);
        this.skin = skin;
    }

    public ErrorDialog(String title, Skin skin, String windowStyleName){
        super(title, skin, windowStyleName);
        this.setModal(true);
        this.skin = skin;
    }

    public void showMessage() {
        final Table table = new Table().top().left();
        table.defaults().left();
        table.row();
        table.add(errorLabel).expandX().fillX();
        this.getContentTable().add(table).maxWidth(400).expand().fill();
    }
}
