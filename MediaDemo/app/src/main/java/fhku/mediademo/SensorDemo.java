package fhku.mediademo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SensorDemo extends AppCompatActivity implements SensorEventListener {

    View field;
    View movable;
    SensorManager sm;
    Sensor s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_demo);
        this.getSupportActionBar().hide();

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        movable = findViewById(R.id.movable_part);
        field = findViewById(R.id.field);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] value = event.values;

        float nextX = this.movable.getX() + (-value[0]*12);
        float nextY = this.movable.getY() + (value[1]*12);

        if (nextX > 0 && nextX < (field.getWidth() - movable.getWidth())) {
            this.movable.setX(nextX);
        }

        if (nextY > 0 && nextY < (field.getHeight() - movable.getHeight())) {
            this.movable.setY(nextY);
        }

        // Log.i("SENSOR_DEMO", value[0] + "/" + value[1] + "/" + value[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i("SENSOR_DEMO", "Accuracy changes: " + sensor.getName());
    }
}
