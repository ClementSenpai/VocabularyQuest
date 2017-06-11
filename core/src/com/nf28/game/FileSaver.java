package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

/**
 * Created by Rachid Khalef on 08/06/2017.
 */

public class FileSaver {

    public static void saveFile(FileHandle saveFile) {
        FileHandle locaSaveDirectory = Gdx.files.local("importedLists/");
        if (!locaSaveDirectory.exists()) Gdx.files.local("importedLists/").mkdirs();
        FileHandle localSave = Gdx.files.local("importedLists/").child(saveFile.name());
        if(!localSave.exists()){
            saveFile.copyTo(Gdx.files.local("importedLists/"));
        }
    }

    public static void saveDefaultFile(String default_file_name) {
        Preferences preferences = Gdx.app.getPreferences("ca.nf28.vocabularyquest.settings");
        preferences.putString("DEFAULT_LIST", default_file_name);
        preferences.flush();
    }

    public static void removeFile(String file_name) {
        FileHandle file_to_remove = Gdx.files.local("importedLists/" + file_name);
        if (file_to_remove.exists()) {
            file_to_remove.delete();
        }
    }

    public static  void renameFile(String file_name, String new_file_name) {
        FileHandle file_to_rename = Gdx.files.local("importedLists/" + file_name);
        if (file_to_rename.exists() && file_name != new_file_name)
            file_to_rename.file().renameTo(new File(file_to_rename.file().getParentFile(), new_file_name));
    }
}
