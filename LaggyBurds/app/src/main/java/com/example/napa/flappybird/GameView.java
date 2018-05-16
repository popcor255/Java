package com.example.napa.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.ActionMode;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.math.*;
import java.util.Random;


/**
 * Created by napa on 5/10/18.
 */

public class GameView extends View {

    Handler handler;//Redraws 30ms after the game, like event-handler but with time
    Runnable runnable;
    final int UPDATE_MILLIS = 30;
    Bitmap background;
    Bitmap topTube, botTube;
    Display display;
    Point point;
    int dWidth, dHeight;
    Rect rect;
    Bitmap[] birds;
    int birdFrame = 0;
    int velocity = 0;
    int gravity = 3;
    int gap = 180; //tube gap
    int offsetBound = (gap);
    int birdX, birdY;
    boolean gameState = false;
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 4;
    int distanceBetweenTubes;
    int[] topTubeY = new int[numberOfTubes];
    int[] tubeX = new int[numberOfTubes];
    Random rng;
    int tubeVelocity;

    //This is a custom view class
    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate(); //This will call on draw
            }
        };

        initGame();
    }

    public void initGame(){
        birdFrame = 0;
        velocity = 0;
        gravity = 3;
        gap = 180; //tube gap
        offsetBound = (gap);
        gameState = false;
        numberOfTubes = 4;
        topTubeY = new int[numberOfTubes];
        tubeX = new int[numberOfTubes];

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        birds = new Bitmap[2];
        topTube = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        botTube = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird0);
        setRectToScreenSize(getContext());
        birdX = dWidth / 2 - birds[0].getWidth() / 2;
        birdY = dHeight / 2 - birds[0].getHeight() / 2;
        distanceBetweenTubes = dWidth * 3 / 4;
        tubeVelocity = dWidth / 50;
        minTubeOffset = Math.max(dHeight - gap - topTube.getHeight(), 0);
        maxTubeOffset = topTube.getHeight();
        rng = new Random();
        for (int i = 0; i < numberOfTubes; i++) {
            tubeX[i] = dWidth + (i * distanceBetweenTubes);
            topTubeY[i] = minTubeOffset + rng.nextInt(maxTubeOffset - minTubeOffset + 1);
        }
    }

    public void setRectToScreenSize(Context context){
        display = ((Activity)context).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0,0,dWidth,dHeight);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //Draw Background inside here
        canvas.drawBitmap(background, null, rect, null);

        if(birdFrame == 0){
            birdFrame = 1;
        }
        else{
            birdFrame = 0;
        }

        if(gameState) {

            if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
                velocity += gravity;
                birdY += velocity;
            }

            for(int i = 0; i < numberOfTubes; i++){
                if(tubeX[i] + topTube.getWidth() < 0) {
                    tubeX[i] =  numberOfTubes * distanceBetweenTubes;
                }
                else{
                    tubeX[i] -= tubeVelocity;
                }

                if(tubeX[i] <= (birdX + birds[0].getWidth()) && tubeX[i] + topTube.getWidth() >= birdX){
                    if(!(topTubeY[i] + gap >= (birdY + birds[0].getHeight()) && topTubeY[i] <= birdY)){
                        initGame();
                    }
                }


                canvas.drawBitmap(topTube, tubeX[i], topTubeY[i] - topTube.getHeight(), null);
                canvas.drawBitmap(botTube, tubeX[i], topTubeY[i] + gap, null);
            }
        }
        //Draw Bird
        canvas.drawBitmap(birds[birdFrame], birdX, birdY, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            velocity = -30;
            gameState = true;
        }

        return true;
    }
}
