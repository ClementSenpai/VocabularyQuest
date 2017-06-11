package com.nf28.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.nf28.model.Vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Rachid Khalef on 09/06/2017.
 */

public class FileLoader {

    public static FileHandle[] loadFiles(){
        FileSaver.saveFile(Gdx.files.internal("lists/Default.csv"));
        FileHandle local_dir = Gdx.files.local("importedLists");
        return local_dir.list();
    }

    public static Array<String> loadFilesNames(){
        FileHandle[] local_files = loadFiles();
        Array<String> files_names = new Array<String>();
        for (FileHandle file : local_files) {
            files_names.add(file.name());
        }
        return files_names;
    }

    public static String removeAccents(String text) {
        return text == null ? null
                : Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    public static Vocabulary parseFile (FileHandle file) throws IOException {
        Vocabulary listVocabulary = new Vocabulary();
        BufferedReader reader = new BufferedReader(file.reader());
        Array<String> lines = new Array<String>();
        String line = null;
        Pattern p = Pattern.compile("\"([-'\\(\\)\\s\\d\\p{Ll}\\p{Lu}\\p{Punct}\\+\\=\\\\]+)\",\"([-'\\(\\)\\s\\d\\p{Ll}\\p{Lu}\\p{Punct}\\+\\=\\\\]+)\"");
        try {
            line = removeAccents(reader.readLine());
            while( line != null ) {
                Matcher m = p.matcher(line);
                if (m.matches()){
                    if(m.group().length() != 0){
                        //Gdx.app.log("FileLoader", " Found : " + m.group(1) + " --- " + m.group(2));
                        listVocabulary.put(m.group(1), m.group(2));
                    }
                }
                //else  Gdx.app.log("FileLoader", "Debug Line : " + line );
                lines.add(line);
                line = removeAccents(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listVocabulary.refreshKeys();
        return listVocabulary;
    }

    public static boolean checkExistence (FileHandle file) {
        return (Gdx.files.local("importedLists/" + file.name()).exists());
    }

    public static String checkFile (FileHandle file) {
        BufferedReader reader = new BufferedReader(file.reader());
        Array<String> lines = new Array<String>();
        String line = null;
        Pattern p = Pattern.compile("\"([-'\\(\\)\\s\\d\\p{Ll}\\p{Lu}\\p{Punct}\\+\\=\\\\]+)\",\"([-'\\(\\)\\s\\d\\p{Ll}\\p{Lu}\\p{Punct}\\+\\=\\\\]+)\"");
        try {
            line = removeAccents(reader.readLine());
            while( line != null ) {
                Matcher m = p.matcher(line);
                if (m.matches() == false) {
                    //Gdx.app.log("FileLoader", "Debug Line : " + line );
                    return line;
                }
                lines.add(line);
                line = removeAccents(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public static FileHandle loadDefaultFile() {
        Preferences preferences = Gdx.app.getPreferences("ca.nf28.vocabularyquest.settings");
        String default_file_name = preferences.getString("DEFAULT_LIST", "Default.csv");
        FileHandle default_file = Gdx.files.local("importedLists/").child(default_file_name);
        if (default_file.exists()){
            return default_file;
        }
        return Gdx.files.local("importedLists/Default.csv");
    }

    public static Vocabulary loadDefaultList() throws IOException {
        FileHandle default_file = loadDefaultFile();
        return parseFile(default_file);
    }
}
