package com.nf28.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Eachid Khalef on 09/06/2017.
 */

public class FileHandleView extends Dialog {
    private Skin skin;
    private TextField fileNameField;

    public String getfileNameField(){ return fileNameField.getText();}

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public FileHandleView(String title, Skin skin, String file_name){
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.getButtonTable().row();
        this.button("Load", "Load");
        this.getButtonTable().row();
        this.button("Rename", "Rename");
        this.getButtonTable().row();
        this.button("Remove", "Remove");

        this.setModal(true);
        this.skin = skin;

        this.fileNameField = new TextField(file_name, skin);
        this.fileNameField.setDisabled(true);
    }

    public void showFileName() {
        final Table table = new Table().top().left();
        table.defaults().left();
        table.row();
        table.add(fileNameField).expandX().fillX();
        this.getContentTable().add(table).maxWidth(400).expand().fill();
    }
}
