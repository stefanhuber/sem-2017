package fhku.mediademo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AudioDemo extends AppCompatActivity implements View.OnClickListener {

    String mediaFile;

    Button playButton;
    Button recordButton;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    boolean hasMedia = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_demo);

        playButton   = (Button) findViewById(R.id.play);
        recordButton = (Button) findViewById(R.id.record);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] { Manifest.permission.RECORD_AUDIO }, 100);
        }

        playButton.setEnabled(hasMedia);
        playButton.setText("Play");
        playButton.setOnClickListener(this);
        recordButton.setEnabled(true);
        recordButton.setText("Record");
        recordButton.setOnClickListener(this);

        mediaFile = getFilesDir().getAbsolutePath() + "/mediafile.3gp";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermission = false;

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            hasPermission = true;
        }

        if (!hasPermission) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.record && recordButton.getText().equals("Record")) {
            recordButton.setText("Stop");
            playButton.setEnabled(false);

            try {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(mediaFile);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (Exception e) {
                Log.e("MEDIA_DEMO", e.getMessage());
            }
        } else if (v.getId() == R.id.record && recordButton.getText().equals("Stop")) {
            recordButton.setText("Record");
            playButton.setEnabled(true);

            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        } else if (v.getId() == R.id.play && playButton.getText().equals("Play")) {
            playButton.setText("Stop");
            recordButton.setEnabled(false);

            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(mediaFile);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                Log.e("MEDIA_DEMO", e.getMessage());
            }
        } else if (v.getId() == R.id.play && playButton.getText().equals("Stop")) {
            playButton.setText("Play");
            recordButton.setEnabled(true);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
