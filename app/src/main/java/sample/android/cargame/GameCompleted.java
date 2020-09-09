package sample.android.cargame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class GameCompleted extends GameObject {
    private MainActivity mainActivity;
    private Rect backRect, homeRect;

    public GameCompleted(MainActivity mainActivity) {
        super(mainActivity, mainActivity.surfaceWidth, mainActivity.surfaceHeight);
        this.mainActivity = mainActivity;
        setBitmap(R.drawable.game_completed);
        alignCenter();

        backRect = getRect(188, 86, 40, 40);
        homeRect = getRect(226, 86, 40, 40);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawText(canvas, mainActivity.scoreBoard.getScore() + " 점",  48, 120, 36, Color.BLACK);
    }

    public boolean isHitBack(int x, int y) {
        return backRect.contains(x, y);
    }

    public boolean isHitHome(int x, int y) {
        return homeRect.contains(x, y);
    }
}
