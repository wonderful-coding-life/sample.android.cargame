package sample.android.cargame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnTouchListener {

    private GamePlayThread gamePlayThread;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private int hitCount;

    public GameMode gameMode;
    public int surfaceWidth, surfaceHeight;
    public Car car;
    public ScoreBoard scoreBoard;
    public Balloons balloons;
    public Obstacle obstacle;
    public GameStart gameStart;
    public GameCompleted gameCompleted;
    public Pedal accellerator, brake;
    public Jump jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView mainSurfaceViwe = findViewById(R.id.main_surface);
        mainSurfaceViwe.setOnTouchListener(this);
        surfaceHolder = mainSurfaceViwe.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
        surfaceWidth = width;
        surfaceHeight = height;
        gameMode = GameMode.Ready;
        gamePlayThread = new GamePlayThread();
        isRunning = true;
        gamePlayThread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                gamePlayThread.press(true, (int)motionEvent.getX(), (int)motionEvent.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                gamePlayThread.press(false, (int)motionEvent.getX(), (int)motionEvent.getY());
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    public void hitObstacle() {
        hitCount++;
        if (hitCount > 2) {
            gameMode = GameMode.Completed;
        }
    }

    private class GamePlayThread extends Thread {
        public void press(boolean press, int x, int y) {
            switch (gameMode) {
                case Ready:
                    if (press == true && gameStart.isHit(x, y) == true) {
                        gameMode = GameMode.Play;
                        hitCount = 0;
                        scoreBoard.reset();
                        balloons.reset();
                    }
                    break;
                case Play:
                    if (press == true) {
                        if (accellerator.isHit(x, y) == true) {
                            accellerator.press(true);
                        } else if (brake.isHit(x, y) == true) {
                            brake.press(true);
                        } else if (jump.isHit(x, y) == true) {
                            car.jump();
                        }
                    } else {
                        accellerator.press(false);
                        brake.press(false);
                    }
                    break;
                case Completed:
                    if (press == true) {
                        if (gameCompleted.isHitBack(x, y) == true) {
                            gameMode = GameMode.Play;
                            hitCount = 0;
                            scoreBoard.reset();
                            balloons.reset();
                        } else if (gameCompleted.isHitHome(x, y) == true) {
                            gameMode = GameMode.Ready;
                        }
                    }
                    break;
            }
        }

        @Override
        public void run() {
            super.run();

            Paint skyPaint = new Paint();
            skyPaint.setColor(getResources().getColor(R.color.colorSky, null));

            Mountain mountain = new Mountain(MainActivity.this);
            gameStart = new GameStart(MainActivity.this);
            gameCompleted = new GameCompleted(MainActivity.this);
            accellerator = new Pedal(MainActivity.this, 10, 500, R.drawable.accellerator, R.drawable.accellerator_pressed);
            brake = new Pedal(MainActivity.this, 200, 500, R.drawable.brake, R.drawable.brake_pressed);
            jump = new Jump(MainActivity.this, 400, 500);
            car = new Car(MainActivity.this);
            scoreBoard = new ScoreBoard(MainActivity.this);
            balloons = new Balloons(MainActivity.this, 8);
            obstacle = new Obstacle(MainActivity.this);

            scoreBoard.resetAnswer();
            while (isRunning) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawRect(0, 0, surfaceWidth - 1, surfaceHeight - 1, skyPaint);
                    mountain.draw(canvas);
                    switch (gameMode) {
                        case Ready:
                            gameStart.draw(canvas);
                            break;
                        case Play:
                            car.draw(canvas);
                            balloons.draw(canvas);
                            accellerator.draw(canvas);
                            brake.draw(canvas);
                            jump.draw(canvas);
                            obstacle.draw(canvas);
                            scoreBoard.draw(canvas);
                            break;
                        case Completed:
                            gameCompleted.draw(canvas);
                            break;
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private enum GameMode {
        Ready, Play, Completed
    }
}