package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.TypedValue;

public class Obstacle {

    private Context context;
    private int surfaceWidth, surfaceHeight;
    private Car car;
    private int x, y;
    private Bitmap bitmap, stoneBitmap, waterBitmap;
    private boolean isValid;

    public Obstacle(Context context, int surfaceWidth, int surfaceHeight, Car car) {
        this.context = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.car = car;
        stoneBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_stone);
        waterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_water);
        reset();
    }

    private void reset() {
        if (Math.random() < 0.5) {
            bitmap = stoneBitmap;
        } else {
            bitmap = waterBitmap;
        }
        x = surfaceWidth + (int)(surfaceWidth * Math.random());
        y = surfaceHeight - bitmap.getHeight();
        isValid = true;
    }

    public void draw(Canvas canvas) {
        if (isValid == true && getCollisionRect().intersect(car.getCollisionRect())) {
            int movement = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
            if (bitmap == stoneBitmap) {
                car.moveX(-movement);
                isValid = false;
            } else if (bitmap == waterBitmap) {
                car.moveX(movement);
                isValid = false;
            }
        }
        canvas.drawBitmap(bitmap, x, surfaceHeight - bitmap.getHeight(), null);
        x -= 5;
        if (x < -bitmap.getWidth()) {
            reset();
        }
    }

    private static final double collisionRate = 0.8;
    public Rect getCollisionRect() {
        return new Rect((int)(x + (bitmap.getWidth() * (1 - collisionRate)) / 2), (int)(y + (bitmap.getHeight() * (1 - collisionRate)) / 2), (int)(x + (bitmap.getWidth() * collisionRate) - 1), (int)(y + (bitmap.getHeight() * collisionRate) - 1));
    }
}
