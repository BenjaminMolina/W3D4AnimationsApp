package com.texocoyotl.w3d4animationsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LOG_";
    private static final int PUZZLE_WIDTH = 3;
    private static final int CELL_PADDING = 10;
    private static final String PUZZLE_KEY = "puzzle";
    private int emptyPosition;
    private Puzzle puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Point size = new Point();
        //getWindowManager().getDefaultDisplay().getSize(size);

        if (savedInstanceState != null){
            puzzle = (Puzzle) savedInstanceState.getParcelable(PUZZLE_KEY);
        }
        else {
            puzzle = new Puzzle(PUZZLE_WIDTH);
        }

        Log.d(TAG, "onCreate: size " + puzzle);

        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.grumpy);
        final Bitmap cropped = Utils.cropBitmap(bp);

        ImageView[] imageGrid= new ImageView[9];

        imageGrid[0] = (ImageView) findViewById(R.id.img0);
        imageGrid[1] = (ImageView) findViewById(R.id.img1);
        imageGrid[2] = (ImageView) findViewById(R.id.img2);
        imageGrid[3] = (ImageView) findViewById(R.id.img3);
        imageGrid[4] = (ImageView) findViewById(R.id.img4);
        imageGrid[5] = (ImageView) findViewById(R.id.img5);
        imageGrid[6] = (ImageView) findViewById(R.id.img6);
        imageGrid[7] = (ImageView) findViewById(R.id.img7);
        imageGrid[8] = (ImageView) findViewById(R.id.img8);

        for (int i = 0; i < PUZZLE_WIDTH * PUZZLE_WIDTH; i++) {

            if (puzzle.get(i) == 0) {
                imageGrid[i].setVisibility(View.GONE);
                emptyPosition = i;
            }
            else {
                int x = puzzle.get(i) % PUZZLE_WIDTH;
                int y = puzzle.get(i) / PUZZLE_WIDTH;

                imageGrid[i].setImageBitmap(Utils.getBitmapPiece(cropped, x, y, PUZZLE_WIDTH));
                imageGrid[i].setTag(i);
                imageGrid[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int pos = (int) v.getTag();
                        Log.d(TAG, "onClick: pos " + pos + " empty " + emptyPosition);

                        if (Puzzle.getIndexAt(pos, -1, 0, PUZZLE_WIDTH) == emptyPosition){
                            v.animate().translationXBy(-v.getWidth() - CELL_PADDING);
                        }
                        else if (Puzzle.getIndexAt(pos, 1, 0, PUZZLE_WIDTH) == emptyPosition){
                            v.animate().translationXBy(v.getWidth() + CELL_PADDING);
                        }
                        else if (Puzzle.getIndexAt(pos, 0, 1, PUZZLE_WIDTH) == emptyPosition){
                            v.animate().translationYBy(v.getHeight() + CELL_PADDING);
                        }
                        else if (Puzzle.getIndexAt(pos, 0, -1, PUZZLE_WIDTH) == emptyPosition){
                            v.animate().translationYBy(-v.getHeight() - CELL_PADDING);
                        }
                        else return;

                        puzzle.swap(pos, emptyPosition);
                        int swap = emptyPosition;
                        emptyPosition = pos;
                        v.setTag(swap);
                        Log.d(TAG, "onClick: puzzle " + puzzle);

                    }
                });
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(PUZZLE_KEY, puzzle);
        super.onSaveInstanceState(outState);
    }

    public void move(View view) {
//        Button b = (Button) findViewById(R.id.button);
//        ObjectAnimator fadeAltAnim = ObjectAnimator.ofFloat(b, View.TRANSLATION_X, 10);
//        fadeAltAnim.start();
        view.animate().setDuration(100).translationXBy(150).translationYBy(150);
    }
}
