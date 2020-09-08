package sample.android.cargame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

public class GameCompleted {
    private Context context;
    private Bitmap bitmap;
    private int x, y;
    private Paint paint;

    public GameCompleted(Context context, int surfaceWidth, int surfaceHeight) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_completed);
        x = (surfaceWidth - bitmap.getWidth()) / 2;
        y = (surfaceHeight - bitmap.getHeight()) / 2;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);

        int buttonX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 188, context.getResources().getDisplayMetrics());
        int buttonY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86, context.getResources().getDisplayMetrics());
        int buttonWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        int buttonHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());

        canvas.drawRect(this.x + buttonX, this.y + buttonY, this.x + buttonX + buttonWidth, this.y + buttonY + buttonHeight, paint);
    }

    public boolean isHitBack(int x, int y) {
        int buttonX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 188, context.getResources().getDisplayMetrics());
        int buttonY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86, context.getResources().getDisplayMetrics());
        int buttonWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        int buttonHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());

        if (this.x + buttonX < x && x < this.x + buttonX + buttonWidth && this.y + buttonY < y && y < this.y + buttonY + buttonHeight) {
            return true;
        }
        return false;
    }

    public boolean isHitHome(int x, int y) {
        int buttonX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 226, context.getResources().getDisplayMetrics());
        int buttonY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86, context.getResources().getDisplayMetrics());
        int buttonWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        int buttonHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());

        if (this.x + buttonX < x && x < this.x + buttonX + buttonWidth && this.y + buttonY < y && y < this.y + buttonY + buttonHeight) {
            return true;
        }
        return false;
    }
}
