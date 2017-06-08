package com.nf28.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.nf28.ressource.MapTemplate;

import java.util.Random;

/**
 * Created by Clément on 22/05/2017.
 */
public class Map {
     int SQUARE_SIZE = 0;
     int SQUARE_LINE = 11;
    int heros_coord_x;

    public int getHeros_coord_y() {
        return heros_coord_y;
    }

    public void setHeros_coord_y(int heros_coord_y) {
        this.heros_coord_y = heros_coord_y;
    }

    public int getHeros_coord_x() {
        return heros_coord_x;
    }

    public void setHeros_coord_x(int heros_coord_x) {
        this.heros_coord_x = heros_coord_x;
    }

    int heros_coord_y;

    Sprite wall_tiles = new Sprite(new Texture("tiles/wall64.png"));
    Sprite plain_tiles = new Sprite(new Texture("tiles/plain64.png"));
    Sprite nextfloor_tiles = new Sprite(new Texture("tiles/nextfloor64.png"));
    Sprite unseen_tiles = new Sprite(new Texture("tiles/unseen64.png"));

    int size;
    public Tiles tiles[][];

    public Map(int floor) {
        wall_tiles.setSize(Gdx.graphics.getWidth()/11,Gdx.graphics.getWidth()/11);
        plain_tiles.setSize(Gdx.graphics.getWidth()/11,Gdx.graphics.getWidth()/11);
        nextfloor_tiles.setSize(Gdx.graphics.getWidth()/11,Gdx.graphics.getWidth()/11);
        unseen_tiles.setSize(Gdx.graphics.getWidth()/11,Gdx.graphics.getWidth()/11);


        this.SQUARE_SIZE = Gdx.graphics.getWidth()/11;
        size = SQUARE_LINE;
        tiles = new Tiles[size][size];
        map_generation(floor);
    }

    public void displayMap(Batch batch)
    {
        render_map(batch);
    }

    public void updateMap(int x, int y){
        tiles[x][y].seen();
        if(x-1>= 0) tiles[x-1][y].seen();
        if(x+1< size) tiles[x+1][y].seen();
        if(y-1>= 0) tiles[x][y-1].seen();
        if(y+1< size) tiles[x][y+1].seen();
    }

    private void map_generation(int floor)
    {
        int map[][] = new MapTemplate().generateNextFloor(floor);
        new MapTemplate().aff(map);
        for (int y = 0 ; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if(map[y][x] == 0)
                    tiles[x][y] = new Tiles(Tiles.Status.plain, false);
                else if (map[y][x] == 1)
                    tiles[x][y] = new Tiles(Tiles.Status.wall, false);
                else if (map[y][x] == 2)
                    tiles[x][y] = new Tiles(Tiles.Status.plain, true);
                else if (map[y][x] == 3) {
                    tiles[x][y] = new Tiles(Tiles.Status.plain, false);
                    heros_coord_x = x;
                    heros_coord_y = y;
                }
                else if (map[y][x] == 4)
                    tiles[x][y] = new Tiles(Tiles.Status.nextfloor, true);
            }
        }
    }

    private void render_map(Batch batch)
    {
        int pad = (Gdx.graphics.getWidth()-size*SQUARE_SIZE) /2;
        int height = Gdx.graphics.getHeight();

        for (int y = 0 ; y < size; y++) {
            for (int x = 0; x < size ; x++) {
                if(tiles[x][y].seen) {
                    switch (tiles[x][y].status) {
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




