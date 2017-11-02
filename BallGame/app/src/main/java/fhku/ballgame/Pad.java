package fhku.ballgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Pad extends View {

    protected float sizeFactor;
    protected float width;
    protected float height;

    public Pad(Context context) {
        super(context);
    }
    public Pad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public Pad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Pad(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        sizeFactor = MeasureSpec.getSize(widthMeasureSpec) / 10;
        width = sizeFactor * 1.5f;
        height = sizeFactor * .4f;
        setY( MeasureSpec.getSize(heightMeasureSpec) - height );
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvas.drawRoundRect(0, 0, width, height * 0.75f, 10, 10, paint);
    }
}
