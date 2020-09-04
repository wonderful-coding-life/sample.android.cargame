package sample.android.cargame;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Balloons {
    private List<Balloon> balloons = new ArrayList<>();
    public Balloons(Context context, int numBalloons, int surfaceWidth, int surfaceHeight, Car car) {
        int balloonGap = surfaceHeight / (numBalloons - 1);
        for (int i = 0; i < numBalloons; i++) {
            balloons.add(new Balloon(context, R.drawable.balloon_yellow, i * -balloonGap, surfaceWidth, surfaceHeight, car));
        }
    }

    public void draw(Canvas canvas) {
        for (Balloon balloon : balloons) {
            balloon.draw(canvas);
        }
    }
}
