package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Mountain {
    private Bitmap mountainBitmap;
    private int mountain1X, mountain2X, mountainY;

    public Mountain(Context context, int surfaceHeight) {
        mountainBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mountain);
        mountain1X = 0;
        mountain2X = mountainBitmap.getWidth();
        mountainY = surfaceHeight - mountainBitmap.getHeight();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mountainBitmap, mountain1X, mountainY, null);
        canvas.drawBitmap(mountainBitmap, mountain2X, mountainY, null);
        mountain1X -= 10;
        mountain2X -= 10;
        if (mountain1X + mountainBitmap.getWidth() < 0) {
            mountain1X = mountain2X + mountainBitmap.getWidth();
        }
        if (mountain2X + mountainBitmap.getWidth() < 0) {
            mountain2X = mountain1X + mountainBitmap.getWidth();
        }
    }
}
