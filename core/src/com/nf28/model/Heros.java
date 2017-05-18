package com.nf28.model;

/**
 * Created by Clément on 11/05/2017.
 */
public class Heros {

    int hp;
    int max_hp;
    int coord_x;
    int coord_y;
    int skin;

    public Heros(){
        hp = 10;
        max_hp = 10;
        coord_x = 5;
        coord_y = 5;
        skin = 0;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(int coord_x) {
        this.coord_x = coord_x;
    }

    public int getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(int coord_y) {
        this.coord_y = coord_y;
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public int getHp() {
        return hp;

    }
}
