package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Jump {
    private Bitmap bitmap;
    private int x, y;

    public Jump(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_jump);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public boolean isHit(int x, int y) {
        if (this.x < x && x < this.x + bitmap.getWidth() && this.y < y && y < this.y + bitmap.getHeight()) {
            return true;
        }
        return false;
    }
}
