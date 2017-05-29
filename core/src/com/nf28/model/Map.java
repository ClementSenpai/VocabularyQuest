package com.nf28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Clément on 22/05/2017.
 */
public class Map {
    final int MAX_SIZE = 10;
    final int MIN_SIZE = 7;
    final int SQUARE_SIZE = 64;
    final int SQUARE_LINE = 10;

    Sprite wall_tiles = new Sprite(new Texture("tiles/wall64.png"));
    Sprite plain_tiles = new Sprite(new Texture("tiles/plain64.png"));
    Sprite nextfloor_tiles = new Sprite(new Texture("tiles/nextfloor64.png"));
    Sprite unseen_tiles = new Sprite(new Texture("tiles/unseen64.png"));

    int size;
    Tiles map[][];

    public Map() {

        map_generation();
    }

    public Map(Batch batch)

    {
        map_generation();
        render_map(batch);
    }

    public void displayMap(Batch batch)
    {
        render_map(batch);
    }

    public void updateMap(int x, int y){
        map[x][y].seen();

        if(x-1> 0) map[x-1][y].seen();
        if(x+1< SQUARE_LINE) map[x+1][y].seen();
        if(y-1> 0) map[x][y-1].seen();
        if(y+1< SQUARE_LINE) map[x][y+1].seen();
    }

    private void map_generation()
    {
        map = new Tiles[SQUARE_LINE][SQUARE_LINE];
        for (int y = 0 ; y < SQUARE_LINE; y++) {
            for (int x = 0; x < SQUARE_LINE; x++) {
                map[x][y] = new Tiles(Tiles.Status.plain, false);
            }
        }
    }

    private void render_map(Batch batch)
    {
        int pad = (Gdx.graphics.getWidth()-SQUARE_LINE*SQUARE_SIZE) /2;
        int height = Gdx.graphics.getHeight();

        for (int y = 0 ; y < SQUARE_LINE; y++) {
            for (int x = 0; x < SQUARE_LINE ; x++) {
                if(map[x][y].seen) {
                    switch (map[x][y].status) {
                        case plain:
                            plain_tiles.setX(pad + x * SQUARE_SIZE);
                            plain_tiles.setY(height - pad - (y + 1) * SQUARE_SIZE);
                            plain_tiles.draw(batch);
                            break;

                        case wall:
                            wall_tiles.setX(pad + x * SQUARE_SIZE);
                            wall_tiles.setY(height - pad - (y + 1) * SQUARE_SIZE);
                            wall_tiles.draw(batch);
                            break;

                        case nextfloor:
                            nextfloor_tiles.setX(pad + x * SQUARE_SIZE);
                            nextfloor_tiles.setY(height - pad - (y + 1) * SQUARE_SIZE);
                            nextfloor_tiles.draw(batch);
                            break;
                    }
                }
                else {
                    unseen_tiles.setX(pad + x * SQUARE_SIZE);
                    unseen_tiles.setY(height - pad - (y + 1) * SQUARE_SIZE);
                    unseen_tiles.draw(batch);
                }
            }

        }
    }
}




