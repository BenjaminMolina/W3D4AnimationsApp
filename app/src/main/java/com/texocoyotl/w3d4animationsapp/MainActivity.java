package com.texocoyotl.w3d4animationsapp;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LOG_";
    private int maxWidth = 350;
    private int maxHeight = 350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        Log.d(TAG, "onCreate: size " + size);

        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.grumpy);
        Bitmap cropped = Utils.cropBitmap(bp);

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

        for (int i = 0; i < 9; i++) {
            imageGrid[i].setImageBitmap(Utils.getBitmapPiece(cropped, i % 3, i / 3, 3));
            imageGrid[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                    v.animate().alpha(0);
                    Log.d(TAG, "onClick: end");
                }
            });
        }

    }

    public void move(View view) {
//        Button b = (Button) findViewById(R.id.button);
//        ObjectAnimator fadeAltAnim = ObjectAnimator.ofFloat(b, View.TRANSLATION_X, 10);
//        fadeAltAnim.start();
        view.animate().setDuration(100).translationXBy(150).translationYBy(150);
    }
}
