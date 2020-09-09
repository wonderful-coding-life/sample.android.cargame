package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Balloons {
    private MainActivity mainActivity;
    private int numBalloons;
    private List<Balloon> balloons = new ArrayList<>();
    private List<Bitmap> balloonBitmaps = new ArrayList<>();
    private List<Bitmap> fireworksBitmaps = new ArrayList<>();

    public Balloons(MainActivity mainActivity, int numBalloons) {
        this.mainActivity = mainActivity;
        this.numBalloons = numBalloons;
        balloonBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.balloon_black));
        balloonBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.balloon_red));
        balloonBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.balloon_yellow));
        balloonBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.balloon_gray));
        balloonBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.balloon_green));

        fireworksBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.fireworks0));
        fireworksBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.fireworks1));
        fireworksBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.fireworks2));
        fireworksBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.fireworks3));
        fireworksBitmaps.add(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.fireworks4));

        for (int i = 0; i < numBalloons; i++) {
            balloons.add(new Balloon(mainActivity, balloonBitmaps, fireworksBitmaps));
        }
        reset();
    }

    public void reset() {
        int balloonGap = mainActivity.surfaceHeight / (numBalloons - 1);
        for (int i = 0; i < balloons.size(); i++) {
            Balloon balloon = balloons.get(i);
            balloon.setXY((int)(Math.random() * (mainActivity.surfaceWidth - balloon.getWidth())), i * -balloonGap);
        }
    }

    public void draw(Canvas canvas) {
        for (Balloon balloon : balloons) {
            balloon.draw(canvas);
        }
    }

    public List<Balloon> getBalloons() {
        return balloons;
    }
}
