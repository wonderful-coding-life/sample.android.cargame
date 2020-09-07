package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.util.List;

public class Balloon {
    private static final int FONT_SIZE = 32;

    private Bitmap bitmap;
    private int x, y;
    private int surfaceWidth, surfaceHeight;
    private int number;
    private Paint paint;
    private Car car;
    private boolean isVisible;
    private List<Bitmap> bitmapList;
    private int fireworksIndex;
    private List<Bitmap> fireworksBitmapList;

    public Balloon(Context context, List<Bitmap> bitmapList, List<Bitmap> fireworksBitmapList, int y, int surfaceWidth, int surfaceHeight, Car car) {
        this.bitmapList = bitmapList;
        this.fireworksBitmapList = fireworksBitmapList;

        bitmap = bitmapList.get((int)(Math.random() * bitmapList.size()));
        isVisible = true;
        this.x = (int)(Math.random() * (surfaceWidth - bitmap.getWidth()));
        this.y = y;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.car = car;

        number = (int)(Math.random() * 90) + 10;
        paint = new Paint();

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float fontSizeInPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE, displayMetrics);
        paint.setTextSize(fontSizeInPixel);
        paint.setColor(context.getResources().getColor(R.color.colorPrimaryDark, null));
    }

    public void draw(Canvas canvas) {

        if (isVisible == true && getCollisionRect().intersect(car.getCollisionRect())) {
            isVisible = false;
            fireworksIndex = 0;
        }

        if (isVisible) {
            canvas.drawBitmap(bitmap, x, y, null);
            String numberString = String.valueOf(number);
            float numberX = (bitmap.getWidth() - paint.measureText(numberString)) / 2;
            canvas.drawText(String.valueOf(number), x + numberX, y + (float) (bitmap.getHeight() * 0.70), paint);
        } else {
            if (fireworksIndex < fireworksBitmapList.size() * 5) {
                canvas.drawBitmap(fireworksBitmapList.get(fireworksIndex / 5), x, y, null);
                ++fireworksIndex;
            }
        }

        y += 5;

        if (y > surfaceHeight) {
            number = (int)(Math.random() * 90) + 10;
            x = (int)(Math.random() * (surfaceWidth - bitmap.getWidth()));
            y = 0 - bitmap.getHeight();
            isVisible = true;
            bitmap = bitmapList.get((int)(Math.random() * bitmapList.size()));
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
