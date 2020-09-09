package sample.android.cargame;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.List;

public class ScoreBoard extends GameObject {
    private MainActivity mainActivity;
    private int score;
    private int number1, number2;

    public ScoreBoard(MainActivity mainActivity) {
        super(mainActivity, mainActivity.surfaceWidth, mainActivity.surfaceHeight);
        this.mainActivity = mainActivity;
        setBitmap(R.drawable.score_board);
        setXYdp(10, 30);
        reset();
    }

    public void reset() {
        score = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawText(canvas, "점수: " + score, 16, 56, 28, Color.BLACK);
        drawText(canvas, "문제: " + number1 + " + " + number2, 16, 92, 28, Color.BLACK);
    }

    public void resetAnswer() {
        List<Balloon> balloonList = mainActivity.balloons.getBalloons();
        Balloon balloon = balloonList.get((int)(Math.random() * balloonList.size()));
        int balloonNumber = balloon.getNumber();
        number1 = (int)(balloonNumber / 2 * Math.random());
        number2 = balloonNumber - number1;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public int getAnswer() {
        return number1 + number2;
    }
}
