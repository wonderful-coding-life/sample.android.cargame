package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GameStart extends GameObject {
    private Bitmap bitmap, bitmapNoMessage;
    private long startTimestamp;

    public GameStart(MainActivity mainActivity) {
        super(mainActivity, mainActivity.surfaceWidth, mainActivity.surfaceHeight);

        bitmap = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.game_start);
        bitmapNoMessage = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.game_start_no_message);
        setBitmap(bitmap);
        alignCenter();

        startTimestamp = System.currentTimeMillis();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (((System.currentTimeMillis() - startTimestamp) / 1000) % 2 == 0) {
            setBitmap(bitmap);
        } else {
            setBitmap(bitmapNoMessage);
        }
    }
}
