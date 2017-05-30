package com.nf28.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.concurrent.TimeUnit;

/**
 * Created by Clément on 30/05/2017.
 */
public class Transition {
    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    int i;
    int j;

    Sprite black_fadeout = new Sprite(new Texture("tiles/black_fadeout.png"));
    Sprite white_fadeout = new Sprite(new Texture("tiles/white_fadeout.png"));

    public Transition(){
        i = 0;
        j = 255;

        black_fadeout.setX(0);
        black_fadeout.setY(0);
    }

    public void black_fadeout(Batch batch){
            black_fadeout.setAlpha(j);
            black_fadeout.draw(batch);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            j -= 15;

        }

    public void black_fadein(Batch batch){
        black_fadeout.setAlpha(i);
        black_fadeout.draw(batch);
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i+=15;
    }

}
