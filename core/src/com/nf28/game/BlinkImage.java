package com.nf28.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by nicolas on 08/06/17.
 */
public class BlinkImage extends Image {

    public Drawable drawable;
    public long creationTime=-1;


    public BlinkImage() {
        super();
    }

    @Override
    public void setDrawable(Drawable drawable) {
        super.setDrawable(drawable);
        this.drawable = drawable;
    }

    private boolean getBlink(){
        if(creationTime!=-1) {
            long age = System.currentTimeMillis() - creationTime;
            if (age > 600) return true;
            if (age > 500) return false;
            if (age > 300) return true;
            if (age > 100) return false;
            return false;
        }
        return true;
    }

    public void activate(){
        creationTime= System.currentTimeMillis();
    }

    public void draw (Batch batch, float parentAlpha) {
        if(!getBlink())
            super.setDrawable(null);
        else
            super.setDrawable(drawable);
        super.draw(batch, parentAlpha);
    }
}
