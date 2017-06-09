package com.nf28.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by nicolas on 22/05/17.
 */
public class Vocabulary extends HashMap<String,String> {
    List<String> keys ;

    public Vocabulary(){
        /*this.put("test","test");
        this.put("test1","test1");
        this.put("test2","test2");
        this.put("test3","test3");
        this.put("test4","test4");
        this.put("test5","test5");
        this.put("test6","test6");
        this.put("test7","test7");
        this.put("test8","test8");
        this.put("test9","test9");*/
        keys =  new ArrayList<String>();
        refreshKeys();
    }



    public void refreshKeys(){
        for(String key:keySet())
            keys.add(key);
    }

    public String getWord(){
        String key = keys.get(new Random().nextInt(keys.size()));
        keys.remove(key);
        if(keys.isEmpty())
            refreshKeys();
        return key;
    }

    public String[] getResponse(String key, int number){
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

}
