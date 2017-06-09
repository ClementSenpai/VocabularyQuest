package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Rachid Khalef on 08/06/2017.
 */

public class FileSaver {

    public static void saveFile(FileHandle saveFile) {
        FileHandle localSave = Gdx.files.local(saveFile.name());
        if(!localSave.exists()){
            saveFile.copyTo(new FileHandle(Gdx.files.getLocalStoragePath()));
        }
        else{
            Gdx.app.log(" FileSaver ", localSave.name() + " already exists ");
        }
    }

    public static void saveDefaultFile(String default_file_name) {
        Preferences preferences = Gdx.app.getPreferences("ca.nf28.vocabularyquest.settings");
        preferences.putString("DEFAULT_LIST", default_file_name);
        preferences.flush();
    }

    public static void removeFile(String file_name) {
        FileHandle file_to_remove = Gdx.files.local(file_name);
        if (file_to_remove.exists()) {
            Gdx.app.log("FileSaver", " It does exist ");
            file_to_remove.delete();
        }
    }
}
