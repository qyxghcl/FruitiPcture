package com.example.gooview;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by hwj on 2017/3/1.
 */

public class GooView extends View {


    private Paint mPaint;
    private Path mPath;
    private int mStateBarHeight;

    public GooView(Context context) {
        this(context, null);
    }

    public GooView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPath = new Path();

    }

    //固定圆的圆心
    private PointF stickCenter = new PointF(200f, 200f);
    //固定园的半径
    private float sticRadius = 9f;
    private PointF dragCenter = new PointF(200f, 200f);
    private float dragRadius = 9f;
    //定义固定圆的附着点坐标
    private PointF[] stickPoints = new PointF[]{
            new PointF(250f, 300f),
            new PointF(250f, 350f)
    };
    //定义固定圆的附着点坐标
    private PointF[] dragPoints = new PointF[]{
            new PointF(150f, 300f),
            new PointF(150f, 350f)
    };
    //控制点
    private PointF controlPoint = new PointF(200f, 325f);

    public static final float MIN_STICKRADIUDS = 5f;
    public static final float MAX_DISTANCE = 80f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.translate(0, -mStateBarHeight);

        //计算两个圆心之间的距离
        float distance = GeometryUtil.getDistanceBetween2Points(dragCenter, stickCenter);
        distance = Math.min(distance, MAX_DISTANCE);

        float percent = distance / MAX_DISTANCE;

        float tempStickRadius = GeometryUtil.evaluateValue(percent, sticRadius, MIN_STICKRADIUDS);


        //计算斜率
        float offsetX = stickCenter.x - dragCenter.x;
        float offsetY = stickCenter.y - dragCenter.y;

        double linex = 0;
        if (offsetX != 0) {
            linex = offsetY / offsetX;
        }

        //计算固定圆的附着点
        stickPoints = GeometryUtil.getIntersectionPoints(stickCenter, tempStickRadius, linex);
        //计算拖拽圆的附着点
        dragPoints = GeometryUtil.getIntersectionPoints(dragCenter, dragRadius, linex);
        //计算控制点
        controlPoint = GeometryUtil.getMiddlePoint(stickCenter, dragCenter);

        //绘制附着点，用半径为1的圆画
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(stickPoints[0].x, stickPoints[0].y, 1f, mPaint);
        canvas.drawCircle(stickPoints[1].x, stickPoints[1].y, 1f, mPaint);

        canvas.drawCircle(dragPoints[0].x, dragPoints[0].y, 1f, mPaint);
        canvas.drawCircle(dragPoints[1].x, dragPoints[1].y, 1f, mPaint);

        mPaint.setColor(Color.RED);

        if (!isOutofDistance) {
            if (!isOutofRange) {
                mPath.moveTo(stickPoints[0].x, stickPoints[0].y);
                mPath.quadTo(controlPoint.x, controlPoint.y, dragPoints[0].x, dragPoints[0].y);
                mPath.lineTo(dragPoints[1].x, dragPoints[1].y);
                mPath.quadTo(controlPoint.x, controlPoint.y, stickPoints[1].x, stickPoints[1].y);

                canvas.drawPath(mPath, mPaint);
                mPath.reset();

                canvas.drawCircle(stickCenter.x, stickCenter.y, tempStickRadius, mPaint);
            }

            canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, mPaint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float distance = GeometryUtil.getDistanceBetween2Points(dragCenter, stickCenter);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                dragCenter.set(rawX, rawY);
                //获取两个圆心之间的距离
                if (distance < MAX_DISTANCE) {
                    isOutofRange = false;
                    isOutofDistance = false;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                rawX = event.getRawX();
                rawY = event.getRawY();
                dragCenter.set(rawX, rawY);
                //获取两个圆心之间的距离
                if (distance > MAX_DISTANCE) {
                    isOutofRange = true;
                }

                break;

            case MotionEvent.ACTION_UP:

                if (isOutofRange) {
                    //放手时断开，两圆心距离大于80消失，两距离小于80回到原位置
                    if (distance > MAX_DISTANCE) {
                        isOutofDistance = true;

                    }else{
                        dragCenter.set(stickCenter.x,stickCenter.y);
                    }

                } else {
                    //从未断开，通过动画回到固定圆的位置
                    ValueAnimator valueAnimator = ObjectAnimator.ofFloat(MAX_DISTANCE, 0);
                    //记录放手时的圆心坐标
                    final PointF oldDragCenter = new PointF(dragCenter.x,dragCenter.y);

                    //添加一个更新的监听
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            //百分比
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            //获取百分比的点的坐标
                            PointF pointByPercent = GeometryUtil.getPointByPercent(oldDragCenter, stickCenter, animatedFraction);
                            dragCenter.set(pointByPercent.x,pointByPercent.y);
                            invalidate();

                        }
                    });
                    valueAnimator.setDuration(300);
                    //插值器弹弹弹
                    valueAnimator.setInterpolator(new BounceInterpolator());
                    valueAnimator.start();

                }
                break;

        }
        invalidate();
        return true;
    }

    private boolean isOutofRange = false;
    private boolean isOutofDistance = false;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStateBarHeight = getStateBarHeight(this);
    }

    //获取状态栏的高度
    public static int getStateBarHeight(View view) {
        Rect rect = new Rect();

        view.getWindowVisibleDisplayFrame(rect);

        return rect.top;
    }
}
