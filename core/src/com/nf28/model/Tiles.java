package com.nf28.model;

/**
 * Created by Clément on 22/05/2017.
 */
public class Tiles {

    boolean seen;
    boolean monster;
    Status status;

    Tiles(Status s, boolean m){
        seen = false;
        monster = m;
        status = s;
    }
    public enum Status {
        plain,
        wall,
        nextfloor;
    }
    public void seen(){
        seen = true;
    }
}
