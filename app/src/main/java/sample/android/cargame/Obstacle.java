package sample.android.cargame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.TypedValue;

public class Obstacle extends GameObject {
    private MainActivity mainActivity;
    private Bitmap stoneBitmap, waterBitmap;
    private boolean isValid;

    public Obstacle(MainActivity mainActivity) {
        super(mainActivity, mainActivity.surfaceWidth, mainActivity.surfaceHeight);
        this.mainActivity = mainActivity;
        stoneBitmap = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.ic_stone);
        waterBitmap = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.ic_water);
        reset();
    }

    private void reset() {
        if (Math.random() < 0.5) {
            setBitmap(stoneBitmap);
        } else {
            setBitmap(waterBitmap);
        }
        int x = mainActivity.surfaceWidth + (int)(mainActivity.surfaceWidth * Math.random());
        int y = mainActivity.surfaceHeight - getHeight();
        setXY(x, y);

        isValid = true;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (isValid == true && getCollisionRect(0.7).intersect(mainActivity.car.getCollisionRect(0.7))) {
            int movement = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mainActivity.getResources().getDisplayMetrics());
            if (getBitmap() == stoneBitmap) {
                mainActivity.car.moveX(-movement);
                isValid = false;
            } else if (getBitmap() == waterBitmap) {
                mainActivity.car.moveX(movement);
                isValid = false;
            }
            mainActivity.hitObstacle();
        }

        moveX(-5);
        if (getX() < -getWidth()) {
            reset();
        }
    }
}
