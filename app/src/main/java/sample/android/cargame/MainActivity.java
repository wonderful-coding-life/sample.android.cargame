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
    private int surfaceWidth, surfaceHeight;

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

    private class GamePlayThread extends Thread {
        private Pedal accellerator, brake;
        private Jump jump;
        private Car car;

        public void press(boolean press, int x, int y) {
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
        }

        @Override
        public void run() {
            super.run();

            Paint skyPaint = new Paint();
            skyPaint.setColor(getResources().getColor(R.color.colorSky, null));

            Mountain mountain = new Mountain(MainActivity.this, surfaceHeight);
            accellerator = new Pedal(MainActivity.this, 10, 500, R.drawable.accellerator, R.drawable.accellerator_pressed);
            brake = new Pedal(MainActivity.this, 200, 500, R.drawable.brake, R.drawable.brake_pressed);
            jump = new Jump(MainActivity.this, 400, 500);
            car = new Car(MainActivity.this, accellerator, brake, surfaceHeight);
            Balloons balloons = new Balloons(MainActivity.this, 8, surfaceWidth, surfaceHeight, car);
            Obstacle obstacle = new Obstacle(MainActivity.this, surfaceWidth, surfaceHeight, car);

            while (isRunning) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawRect(0, 0, surfaceWidth - 1, surfaceHeight - 1, skyPaint);
                    mountain.draw(canvas);
                    car.draw(canvas);
                    balloons.draw(canvas);
                    accellerator.draw(canvas);
                    brake.draw(canvas);
                    jump.draw(canvas);
                    obstacle.draw(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}