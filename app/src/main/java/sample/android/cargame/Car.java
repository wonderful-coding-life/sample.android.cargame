package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Car {
    private Bitmap bitmap;
    private int x, y;
    private Pedal accellerator, brake;


    public Car(Context context, Pedal accellerator, Pedal brake, int surfaceHeight) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.car);
        x = 0;
        y = surfaceHeight - bitmap.getHeight();
        this.accellerator = accellerator;
        this.brake = brake;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);

        if (accellerator.isPressed() == true) {
            x += 5;
        }
        if (brake.isPressed() == true) {
            x -= 5;
        }
    }

    public Rect getRect() {
        return new Rect(x, y, x + bitmap.getWidth() - 1, y + bitmap.getHeight() - 1);
    }

    private static final double collisionRate = 0.7;
    public Rect getCollisionRect() {
        return new Rect((int)(x + (bitmap.getWidth() * (1 - collisionRate)) / 2), (int)(y + (bitmap.getHeight() * (1 - collisionRate)) / 2), (int)(x + (bitmap.getWidth() * collisionRate) - 1), (int)(y + (bitmap.getHeight() * collisionRate) - 1));
    }
}
