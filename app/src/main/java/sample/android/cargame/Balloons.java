package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Balloons {
    private List<Balloon> balloons = new ArrayList<>();
    private List<Bitmap> balloonBitmaps = new ArrayList<>();

    public Balloons(Context context, int numBalloons, int surfaceWidth, int surfaceHeight, Car car) {
        balloonBitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_black));
        balloonBitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_red));
        balloonBitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_yellow));
        balloonBitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_gray));
        balloonBitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_green));

        int balloonGap = surfaceHeight / (numBalloons - 1);
        for (int i = 0; i < numBalloons; i++) {
            balloons.add(new Balloon(context, balloonBitmaps, i * -balloonGap, surfaceWidth, surfaceHeight, car));
        }
    }

    public void draw(Canvas canvas) {
        for (Balloon balloon : balloons) {
            balloon.draw(canvas);
        }
    }
}
