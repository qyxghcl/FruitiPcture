package com.example.fruitpictures.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyAnimView extends TextView {


    private String color;
    private Paint mPaint;
    public MyAnimView(Context context) {
        this(context,null);
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }
}
  

  