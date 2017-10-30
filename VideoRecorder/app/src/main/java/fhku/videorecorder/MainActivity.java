package fhku.videorecorder;


import android.content.Intent;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Button recordButton;
    Button playButton;
    VideoView videoView;
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        recordButton = (Button) findViewById(R.id.record);
        playButton = (Button) findViewById(R.id.play);
        videoView = (VideoView) findViewById(R.id.video);
    }

    public void toggleRecord (View view) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, 1);
        }
    }

    public void togglePlay(View view) {
        if (!isPlaying) {
            videoView.start();
            isPlaying = true;
        } else {
            videoView.pause();
            isPlaying = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            videoView.setVideoURI(videoUri);
            playButton.setEnabled(true);
        }
    }


}
