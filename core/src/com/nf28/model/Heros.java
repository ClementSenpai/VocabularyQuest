package com.nf28.model;

/**
 * Created by Clément on 11/05/2017.
 */
public class Heros {

    int hp;
    int level;
    int max_hp;
    int coord_x;
    int coord_y;
    int skin;
    int exp;

    String imageUrl="tiles/character.png";

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public Heros(){
        hp = 10;
        max_hp = 10;
        coord_x = 5;
        coord_y = 5;
        skin = 0;
        exp = 0;
        level = 1;
    }
    public boolean checkMove(int x, int y, Map map){

        return !( x < 0
                || x >= map.size
                || y < 0
                || y >= map.size
                || map.tiles[x][y].getStatus() == Tiles.Status.wall);

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

    public void setExp(int exp) {this.exp = exp;}

    public int getExp() {return exp;}
}
