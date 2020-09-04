package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Car {
    private Bitmap carBitmap;
    private int carX, carY;
    private Pedal accellerator, brake;


    public Car(Context context, Pedal accellerator, Pedal brake, int surfaceHeight) {
        carBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.car);
        carX = 0;
        carY = surfaceHeight - carBitmap.getHeight();
        this.accellerator = accellerator;
        this.brake = brake;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(carBitmap, carX, carY, null);

        if (accellerator.isPressed() == true) {
            carX += 5;
        }
        if (brake.isPressed() == true) {
            carX -= 5;
        }
    }
}
