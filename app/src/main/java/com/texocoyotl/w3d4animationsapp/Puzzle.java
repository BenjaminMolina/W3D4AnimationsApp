package com.texocoyotl.w3d4animationsapp;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by admin on 4/14/2016.
 */
public class Puzzle {

    private Vector<Integer> pieces;
    private int size;

    public Puzzle(int size){
        this.size = size;
        pieces = new Vector<Integer>(size * size);

        for (int i = 0; i < size * size; i++) {
            pieces.add(i);
        }
        Collections.shuffle(pieces);
    }

    @Override
    public String toString() {
        return "Puzzle{" +
                "pieces=" + pieces +
                '}';
    }

    public static int getIndexAt(int pos, int x, int y, int size) {
        int newPos = pos + y * size + x;
        if (newPos / size != pos / size && x != 0) return -1;
        else return newPos;
    }

    public int get(int pos){
        return pieces.get(pos);
    }

    public void swap(int pos, int emptyPosition) {
        if (pieces.get(emptyPosition) == 0){
            int swap = pieces.get(pos);
            pieces.set(pos, pieces.get(emptyPosition));
            pieces.set(emptyPosition, swap);
        }

    }
}
