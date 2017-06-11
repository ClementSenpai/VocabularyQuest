package com.nf28.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by nicolas on 22/05/17.
 */
public class Vocabulary extends HashMap<String,String> {
    List<String> attaquekeys ;
    List<String> defencekeys ;

    public Vocabulary(){
        attaquekeys =  new ArrayList<String>();
        defencekeys =  new ArrayList<String>();
        refreshAttaqueKeys();
        refreshDefenceKeys();
    }

    public void refreshAttaqueKeys(){
        for(String key:keySet())
            attaquekeys.add(key);
    }
    public void refreshDefenceKeys(){
        for(String value:values())
            defencekeys.add(value);
    }


    public String getAttaqueWord(){
        String key = attaquekeys.get(new Random().nextInt(attaquekeys.size()));
        attaquekeys.remove(key);
        if(attaquekeys.isEmpty())
            refreshAttaqueKeys();
        return key;
    }
    public String getDefenceWord(){
        String value = defencekeys.get(new Random().nextInt(defencekeys.size()));
        defencekeys.remove(value);
        if(defencekeys.isEmpty())
            refreshDefenceKeys();
        return value;
    }

    public String[] getResponseAttaque(String key, int number){
        String[] possible_answer = new String[number];
        possible_answer[new Random().nextInt(number)]=get(key);
        List<String> values = new ArrayList<String>(values());
        for(int i=0;i<number;i++){
            if(possible_answer[i]==null){
                possible_answer[i] = values.get(new Random().nextInt(values.size()));
                values.remove(possible_answer[i]);
            }
        }
        return possible_answer;
    }
    public String[] getResponseDefence(String value, int number){
        String[] possible_answer = new String[number];
        String res=null;
        for (Entry<String, String> entry : entrySet()) {
            if (value.equals(entry.getValue())) {
                res=entry.getKey();
            }
        }
        possible_answer[new Random().nextInt(number)]=res;
        List<String> keys = new ArrayList<String>(keySet());
        for(int i=0;i<number;i++){
            if(possible_answer[i]==null){
                possible_answer[i] = keys.get(new Random().nextInt(keys.size()));
                keys.remove(possible_answer[i]);
            }
        }
        return possible_answer;
    }

}
