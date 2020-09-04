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

        private int accelleratorX = 10;
        private int accelleratorY = 500;
        private int brakeX = 200;
        private int brakeY = 500;
        private Bitmap accelleratorBitmap;
        private Bitmap accelleratorPressedBitmap;
        private Bitmap brakeBitmap;
        private Bitmap brakePressedBitmap;

        private boolean accelleratorPressed = false;
        private boolean brakePressed = false;
        public void press(boolean press, int x, int y) {

            if (press == true) {
                if (accelleratorX < x && x < accelleratorX + accelleratorBitmap.getWidth() && accelleratorY < y && y < accelleratorY + accelleratorBitmap.getHeight()) {
                    accelleratorPressed = true;
                } else if (brakeX < x && x < brakeX + brakeBitmap.getWidth() && brakeY < y && y < brakeY + brakeBitmap.getHeight()) {
                    brakePressed = true;
                }
            } else {
                accelleratorPressed = false;
                brakePressed = false;
            }

        }

        @Override
        public void run() {
            super.run();

            Paint skyPaint = new Paint();
            skyPaint.setColor(getResources().getColor(R.color.colorSky, null));

            Bitmap mountainBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mountain);
            int mountain1X = 0;
            int mountain2X = mountainBitmap.getWidth();
            int mountainY = surfaceHeight - mountainBitmap.getHeight();

            Bitmap carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
            int carX = 0;
            int carY = surfaceHeight - carBitmap.getHeight();

            accelleratorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.accellerator);
            accelleratorPressedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.accellerator_pressed);
            brakeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brake);
            brakePressedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brake_pressed);

            while (isRunning) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {

                    // draw rect with full screen size in black
                    canvas.drawRect(0, 0, surfaceWidth - 1, surfaceHeight - 1, skyPaint);

                    // draw mountain
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

                    // draw car bitmap at carX, carY on canvas
                    canvas.drawBitmap(carBitmap, carX, carY, null);

                    if (accelleratorPressed == true) {
                        carX += 5;
                    }
                    if (brakePressed == true) {
                        carX -= 5;
                    }

                    if (accelleratorPressed == true) {
                        canvas.drawBitmap(accelleratorPressedBitmap, accelleratorX, accelleratorY, null);
                    } else {
                        canvas.drawBitmap(accelleratorBitmap, accelleratorX, accelleratorY, null);
                    }
                    if (brakePressed == true) {
                        canvas.drawBitmap(brakePressedBitmap, brakeX, brakeY, null);
                    } else {
                        canvas.drawBitmap(brakeBitmap, brakeX, brakeY, null);
                    }

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }




}