package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

public class GameObject {
    private Context context;
    private int surfaceWidth, surfaceHeight;
    private Bitmap bitmap;
    private float x, y;
    private Paint paint;

    public GameObject(Context context, int surfaceWidth, int surfaceHeight) {
        this.context = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.paint = new Paint();
    }

    public void setBitmap(int resourceId) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public void setXY(int x, int y) {
        this.x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, context.getResources().getDisplayMetrics());
        this.y = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, context.getResources().getDisplayMetrics());
    }

    public void alignCenter() {
        if (bitmap != null) {
            x = (surfaceWidth - bitmap.getWidth()) / 2;
            y = (surfaceHeight - bitmap.getHeight()) / 2;
        }
    }

    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    public void drawText(Canvas canvas, String text, int x, int y, int size, int color) {
        float textX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, context.getResources().getDisplayMetrics());
        float textY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, context.getResources().getDisplayMetrics());
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
        paint.setTextSize(textSize);
        paint.setColor(color);
        canvas.drawText(text, this.x + textX, this.y + textY, paint);
    }

    public Rect getRect() {
        return new Rect((int) x, (int) y, (int)(x + bitmap.getWidth() - 1), (int)(y + bitmap.getHeight() - 1));
    }

    public Rect getCollisionRect(double collisionRate) {
        return new Rect((int)(x + (bitmap.getWidth() * (1 - collisionRate)) / 2), (int)(y + (bitmap.getHeight() * (1 - collisionRate)) / 2), (int)(x + (bitmap.getWidth() * collisionRate) - 1), (int)(y + (bitmap.getHeight() * collisionRate) - 1));
    }

    public Rect getRect(int x, int y, int width, int height) {
        int buttonX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, context.getResources().getDisplayMetrics());
        int buttonY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, context.getResources().getDisplayMetrics());
        int buttonWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        int buttonHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        return new Rect(getX() + buttonX, getY() + buttonY, getX() + buttonX + buttonWidth - 1, getY() + buttonY + buttonHeight - 1);
    }
}
