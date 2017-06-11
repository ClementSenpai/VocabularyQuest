package com.nf28.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Kovalsky2099 on 09/06/2017.
 */

public class FileHandleView extends Dialog {
    private Skin skin;
    private TextField fileNameField;
    private FileHandle choosenFile;

    public String getfileNameField(){ return fileNameField.getText();}

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public FileHandleView(String title, Skin skin, FileHandle file){
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
        this.choosenFile = file;
    }

    public FileHandleView(String title, Skin skin, String windowStyleName){
        super(title, skin, windowStyleName);
        this.setModal(true);
        this.skin = skin;
    }



}
