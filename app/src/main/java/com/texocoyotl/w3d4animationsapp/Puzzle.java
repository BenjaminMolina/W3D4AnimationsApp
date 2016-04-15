package com.texocoyotl.w3d4animationsapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by admin on 4/14/2016.
 */
public class Puzzle implements Parcelable {

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

    protected Puzzle(Parcel in) {
        if (in.readByte() == 0x01) {
            pieces = new Vector<Integer>();
            in.readList(pieces, Integer.class.getClassLoader());
        } else {
            pieces = null;
        }
        size = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (pieces == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(pieces);
        }
        dest.writeInt(size);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Puzzle> CREATOR = new Parcelable.Creator<Puzzle>() {
        @Override
        public Puzzle createFromParcel(Parcel in) {
            return new Puzzle(in);
        }

        @Override
        public Puzzle[] newArray(int size) {
            return new Puzzle[size];
        }
    };
}