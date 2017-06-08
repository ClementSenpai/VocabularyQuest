package com.nf28.model;

import com.nf28.ressource.MonsterTemplate;

/**
 * Created by Clément on 07/06/2017.
 */
public class Monster {
    int vie;
    int attaque;
    int exp;

    String imageUrl;

    public Monster(int floor) {
        vie = MonsterTemplate.vie[floor];
        attaque = MonsterTemplate.attaque[floor];
        exp = MonsterTemplate.exp[floor];
        imageUrl = "tiles/character.png";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }


    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }


}
