package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;

public class Car extends GameObject {
    private MainActivity mainActivity;
    private boolean isJumping;
    private int jumpIndex;
    private int[] jumpSteps = {-6, -6, -6, -6, -4, -4, -4, -4, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 6, 6, 6, 6};

    public Car(MainActivity mainActivity) {
        super(mainActivity, mainActivity.surfaceWidth, mainActivity.surfaceHeight);
        this.mainActivity = mainActivity;
        setBitmap(R.drawable.car);
        setXY(0, mainActivity.surfaceHeight - getHeight());
        isJumping = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (isJumping) {
            moveYdp(jumpSteps[jumpIndex]);
            jumpIndex++;
            if (jumpIndex >= jumpSteps.length) {
                isJumping = false;
            }
        }
        if (mainActivity.accellerator.isPressed() == true) {
            moveXdp(5);
        }
        if (mainActivity.brake.isPressed() == true) {
            moveXdp(-5);
        }
    }

    public void jump() {
        if (isJumping == false) {
            jumpIndex = 0;
            isJumping = true;
        }
    }
}
