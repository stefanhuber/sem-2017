package fhku.facerecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.FaceDetectionListener {

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    CameraOverlay cameraOverlay;
    Camera camera;
    int cameraId = 0;
    int numberOfCameras = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        surfaceView = (SurfaceView) findViewById(R.id.video);
        cameraOverlay = (CameraOverlay) findViewById(R.id.overlay);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        numberOfCameras = Camera.getNumberOfCameras();

        Log.i("FACE_DETECTION", "number of cameras: " + numberOfCameras);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        requestCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        requestCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.stopFaceDetection();
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void requestCamera() {
        if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            try {
                if (camera == null) {
                    camera = Camera.open(cameraId);
                }
                camera.stopPreview();

                rotateCamera();
                camera.setPreviewDisplay(this.surfaceHolder);
                camera.startPreview();
                camera.setFaceDetectionListener(this);
                camera.startFaceDetection();
            } catch (Exception e) {

            }

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        requestCamera();
    }

    public void rotateCamera() {
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if (display.getRotation() == Surface.ROTATION_0) {
            camera.setDisplayOrientation(90);
        } else if (display.getRotation() == Surface.ROTATION_270) {
            camera.setDisplayOrientation(180);
        }
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length > 0) {
            cameraOverlay.setFaces(faces);
            cameraOverlay.invalidate();
            Log.i("DETECT_FACES", faces[0].id + "/" + faces[0].rect.toString());
        }
    }

    public void toggleCamera(View v) {
        if (camera != null) {
            camera.stopPreview();
            camera.stopFaceDetection();
            camera.release();
            camera = null;
        }

        if (cameraId < (numberOfCameras-1)) {
            cameraId++;
        } else {
            cameraId = 0;
        }

        requestCamera();
    }
}
