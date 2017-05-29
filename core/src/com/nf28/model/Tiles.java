package com.nf28.model;

/**
 * Created by Clément on 22/05/2017.
 */
public class Tiles {

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    boolean seen;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isMonster() {
        return monster;
    }

    public void setMonster(boolean monster) {
        this.monster = monster;
    }

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
