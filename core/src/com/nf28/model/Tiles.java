package com.nf28.model;

/**
 * Created by Clément on 22/05/2017.
 */
public class Tiles {

    boolean seen;
    boolean monster;
    Status status;

    public enum Status {
        plain,
        wall,
        nextfloor;
    }
}
