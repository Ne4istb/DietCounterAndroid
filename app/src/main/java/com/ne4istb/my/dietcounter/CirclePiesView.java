package com.ne4istb.my.dietcounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import java.util.List;

public class CirclePiesView extends View {

    public CirclePiesView(Context context) {
        super(context);
        init();
    }

    public CirclePiesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePiesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        bgpaint = new Paint();
        bgpaint.setColor(getContext().getResources().getColor(R.color.gray));
        bgpaint.setAntiAlias(true);
        bgpaint.setStyle(Paint.Style.FILL);

        rect = new RectF();
    }

    Paint bgpaint;
    RectF rect;

    List<Pair<Float, Integer>> pies;

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        //draw background circle anyway
        int left = 0;
        int width = getWidth();
        int top = 0;

        rect.set(left, top, left + width, top + width);
        canvas.drawArc(rect, -90, 360, true, bgpaint);

        float startPiePosition = -90.0f;
        for (Pair<Float, Integer> pie : pies) {
            float pieZone = 360 * pie.first;

            drawPie(canvas, startPiePosition, pieZone, pie);
            startPiePosition += pieZone;
        }
    }

    private void drawPie(Canvas canvas, float startPiePosition, float pieZone, Pair<Float, Integer> pie) {

        Paint paint = new Paint();
        paint.setColor(pie.second);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawArc(rect, startPiePosition, pieZone, true, paint);
    }

    public void setPies(List<Pair<Float, Integer>> pies) {
        this.pies = pies;
        invalidate();
    }
}