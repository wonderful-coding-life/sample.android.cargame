package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;

public class Car {
    private Context context;
    private Bitmap bitmap;
    private int x, y;
    private Pedal accellerator, brake;
    private boolean isJumping;
    private int jumpIndex;
    private int[] jumpSteps = {-6, -6, -6, -6, -4, -4, -4, -4, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 6, 6, 6, 6};

    public Car(Context context, Pedal accellerator, Pedal brake, int surfaceHeight) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.car);
        x = 0;
        y = surfaceHeight - bitmap.getHeight();
        this.accellerator = accellerator;
        this.brake = brake;
        isJumping = false;

        int sum = 0;
        for (int i = 0; i < jumpSteps.length; i++) {
            sum += jumpSteps[i];
        }
    }

    public void draw(Canvas canvas) {
        if (isJumping) {
            y += (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, jumpSteps[jumpIndex], context.getResources().getDisplayMetrics());
            jumpIndex++;
            if (jumpIndex >= jumpSteps.length) {
                isJumping = false;
            }
        }

        canvas.drawBitmap(bitmap, x, y, null);

        if (accellerator.isPressed() == true) {
            x += 5;
        }
        if (brake.isPressed() == true) {
            x -= 5;
        }
    }

    public void jump() {
        if (isJumping == false) {
            jumpIndex = 0;
            isJumping = true;
        }
    }

    public void moveX(int x) {
        this.x += x;
    }

    public Rect getRect() {
        return new Rect(x, y, x + bitmap.getWidth() - 1, y + bitmap.getHeight() - 1);
    }

    private static final double collisionRate = 0.7;
    public Rect getCollisionRect() {
        return new Rect((int)(x + (bitmap.getWidth() * (1 - collisionRate)) / 2), (int)(y + (bitmap.getHeight() * (1 - collisionRate)) / 2), (int)(x + (bitmap.getWidth() * collisionRate) - 1), (int)(y + (bitmap.getHeight() * collisionRate) - 1));
    }
}
