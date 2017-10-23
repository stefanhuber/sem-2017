package fhku.mediademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.media_demo).setOnClickListener(this);
        findViewById(R.id.sensor_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.media_demo) {
            startActivity(new Intent(this, AudioDemo.class));
        } else if (v.getId() == R.id.sensor_demo) {
            startActivity(new Intent(this, SensorDemo.class));
        }
    }
}
