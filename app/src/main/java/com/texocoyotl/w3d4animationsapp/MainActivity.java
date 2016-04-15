package com.texocoyotl.w3d4animationsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LOG_";
    private static final int PUZZLE_WIDTH = 3;
    private static final int CELL_PADDING = 10;
    private static final String PUZZLE_KEY = "puzzle";
    private static final String BITMAP_KEY = "bitmap";
    private static final int PICK_IMAGE = 1;

    private int emptyPosition;
    private Puzzle puzzle;
    private ImageView[] imageGrid;
    private Bitmap mPuzzleBitmap;
    private Button mLoadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Point size = new Point();
        //getWindowManager().getDefaultDisplay().getSize(size);
        mLoadButton = (Button) findViewById(R.id.loadButton);
        imageGrid= new ImageView[9];

        imageGrid[0] = (ImageView) findViewById(R.id.img0);
        imageGrid[1] = (ImageView) findViewById(R.id.img1);
        imageGrid[2] = (ImageView) findViewById(R.id.img2);
        imageGrid[3] = (ImageView) findViewById(R.id.img3);
        imageGrid[4] = (ImageView) findViewById(R.id.img4);
        imageGrid[5] = (ImageView) findViewById(R.id.img5);
        imageGrid[6] = (ImageView) findViewById(R.id.img6);
        imageGrid[7] = (ImageView) findViewById(R.id.img7);
        imageGrid[8] = (ImageView) findViewById(R.id.img8);

        if (savedInstanceState != null){
            puzzle = (Puzzle) savedInstanceState.getParcelable(PUZZLE_KEY);
            mPuzzleBitmap = (Bitmap) savedInstanceState.getParcelable(BITMAP_KEY);
            if (mPuzzleBitmap != null) {
                mLoadButton.setVisibility(View.GONE);
                loadPuzzle(mPuzzleBitmap);
            }
        }
        else {
            puzzle = new Puzzle(PUZZLE_WIDTH);
        }

        Log.d(TAG, "onCreate: size " + puzzle);


    }

    private void loadPuzzle(Bitmap bp){
        final Bitmap cropped = Utils.cropBitmap(bp);

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

                        v.animate().setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                if (puzzle.isSolved()){
                                    Toast.makeText(MainActivity.this, "Victory!!!", Toast.LENGTH_LONG).show();

                                    int x = puzzle.get(emptyPosition) % PUZZLE_WIDTH;
                                    int y = puzzle.get(emptyPosition) / PUZZLE_WIDTH;

                                    imageGrid[emptyPosition].setImageBitmap(Utils.getBitmapPiece(cropped, x, y, PUZZLE_WIDTH));
                                    imageGrid[emptyPosition].setVisibility(View.VISIBLE);

                                    for (int i = 0; i < PUZZLE_WIDTH * PUZZLE_WIDTH; i++) {
                                        imageGrid[i].setOnClickListener(null);
                                        imageGrid[i].animate().setStartDelay(2000).setDuration(1000).rotationBy(360).alpha(0);
                                    }
                                }
                            }
                        });

                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(PUZZLE_KEY, puzzle);
        outState.putParcelable(BITMAP_KEY, mPuzzleBitmap);

        super.onSaveInstanceState(outState);
    }

    public void getPicture(View view) {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            mPuzzleBitmap = (Bitmap) data.getExtras().get("data");
            //mPuzzleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grumpy);
            mLoadButton.setVisibility(View.GONE);
            loadPuzzle(mPuzzleBitmap);
        }
    }

}
