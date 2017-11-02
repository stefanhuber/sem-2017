package fhku.ballgame;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Ball extends View {

    protected Pad pad;
    protected float sizeFactor;

    public void setPad(Pad pad) {
        this.pad = pad;
    }

    public Ball(Context context) {
        super(context);
    }
    public Ball(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        sizeFactor = MeasureSpec.getSize(widthMeasureSpec) / 10;
        setMeasuredDimension((int) sizeFactor, (int) sizeFactor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(sizeFactor * .5f, sizeFactor * .5f, sizeFactor * .5f, paint);
        setY(sizeFactor*.25f + getY());

        if ( pad.getY() <= (getY() + sizeFactor) ) {
            float centerBall = getX() + sizeFactor * .5f;
            float leftPad = pad.getX();
            float rightPad = pad.getX() + pad.getWidth();

            if ( centerBall >= leftPad && centerBall <= rightPad ) {
                Log.i("BALL_GAME", "catched ball");
            } else {
                Log.i("BALL_GAME", "lost ball");
            }

            setY(0);
            setX(((float) Math.random()) * 9f * sizeFactor);
        }

        postInvalidateDelayed(50);
    }
}
