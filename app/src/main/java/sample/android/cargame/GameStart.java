package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GameStart {
    private Bitmap bitmap, bitmapNoMessage;
    private int x, y;
    private long startTimestamp;

    public GameStart(Context context, int surfaceWidth, int surfaceHeight) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_start);
        bitmapNoMessage = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_start_no_message);
        x = (surfaceWidth - bitmap.getWidth()) / 2;
        y = (surfaceHeight - bitmap.getHeight()) / 2;
        startTimestamp = System.currentTimeMillis();
    }

    public void draw(Canvas canvas) {
        if (((System.currentTimeMillis() - startTimestamp) / 1000) % 2 == 0) {
            canvas.drawBitmap(bitmap, x, y, null);
        } else {
            canvas.drawBitmap(bitmapNoMessage, x, y, null);
        }
    }

    public boolean isHit(int x, int y) {
        if (this.x < x && x < this.x + bitmap.getWidth() && this.y < y && y < this.y + bitmap.getHeight()) {
            return true;
        }
        return false;
    }
}
