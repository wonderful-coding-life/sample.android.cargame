package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GameCompleted {
    private Bitmap bitmap;
    private int x, y;

    public GameCompleted(Context context, int surfaceWidth, int surfaceHeight) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_completed);
        x = (surfaceWidth - bitmap.getWidth()) / 2;
        y = (surfaceHeight - bitmap.getHeight()) / 2;
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
