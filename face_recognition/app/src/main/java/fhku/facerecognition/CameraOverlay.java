package fhku.facerecognition;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CameraOverlay extends View {

    protected Camera.Face[] faces;

    public void setFaces(Camera.Face[] faces) {
        this.faces = faces;
    }

    public CameraOverlay(Context context) {
        super(context);
    }

    public CameraOverlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraOverlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CameraOverlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint redPaint = new Paint();
        redPaint.setStrokeWidth(10);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);

        if (faces != null && faces.length > 0) {
            for (Camera.Face face : faces) {
                int radius = (face.rect.right - face.rect.left);
                canvas.drawCircle(face.rect.centerX(), face.rect.centerY(), radius, redPaint);
            }
        }
    }
}
