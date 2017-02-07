package com.dominikk.sidebar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dominikk.sidebar.R;
import com.dominikk.sidebar.adapter.IdTextAdapter;

/**
 * Created by Dominik K on 2017-02-07.
 */

public class ListIndexView extends LinearLayout {

    Handler mHandler = new Handler();
    private Runnable fadeRunnable = new Runnable() {
        @Override
        public void run() {
            sliderAlpha -= 4;
            if(sliderAlpha > 0) {
                sliderPaint.setAlpha(sliderAlpha);
                invalidate();
                mHandler.postDelayed(this, 1);
            } else {
                mHandler.removeCallbacks(this);
                sliderAlpha = 0;
                sliderPaint.setAlpha(sliderAlpha);
                drawSlider = false;
                invalidate();
            }
        }
    };

    private Context context;
    private String[] sections;

    private int textColor;

    private Paint sliderPaint;
    private int sliderAlpha = 255;
    private boolean drawSlider = false;
    private int sliderColor;
    private int sliderHeight;
    private int sliderWidth;

    private float touchY = 0.0f;

    private int padLeft;
    private int padRight;

    public ListIndexView(Context context) {
        super(context);
        this.context = context;
    }

    public ListIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ListIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(final IdTextAdapter adapter, final LinearLayout letterContainer, final TextView textView, final ListView listView) {
        textColor = ContextCompat.getColor(context, R.color.colorText);
        sliderColor = ContextCompat.getColor(context, R.color.sliderColor);

        sliderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sliderPaint.setStyle(Paint.Style.FILL);
        sliderPaint.setColor(sliderColor);

        removeAllViews();
        setOrientation(VERTICAL);

        sections = (String[]) adapter.getSections();

        for (int i = 0; i < sections.length; i++) {
            addTextView(sections[i]);
        }

        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1100);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacks(fadeRunnable);
                        sliderAlpha = 255;
                        sliderPaint.setAlpha(sliderAlpha);
                    case MotionEvent.ACTION_MOVE:

                        float eventY = event.getY();

                        if(eventY >= v.getHeight() - 1) {
                            eventY = v.getHeight() - 1;
                        }

                        int position = (int) (eventY * sections.length / v.getHeight());

                        if (position >= 0 && position <= sections.length - 1) {

                            String s;
                            int localPosition;
                            if (position == 0) {
                                s = sections[0];
                                localPosition = adapter.getPositionForSection(position);
                            } else {
                                s = sections[position];
                                localPosition = adapter.getPositionForSection(position);
                            }

                            if (localPosition != -1) {
                                listView.setSelection(localPosition);

                            }
                            textView.setText(s);

                            if(eventY != touchY) {
                                invalidate();
                            }

                            touchY = eventY;

                            if(!drawSlider) {
                                drawSlider = true;
                                invalidate();
                            }

                            letterContainer.clearAnimation();
                            letterContainer.setVisibility(VISIBLE);
                            letterContainer.setAlpha(1.0f);
                        } else {
                            listView.setSelection(0);
                        }

                        return true;

                    case MotionEvent.ACTION_UP:
                        if(drawSlider) {
//                            drawSlider = false;
//                            invalidate();
                            sliderFadeOut();
                        }
                        letterContainer.setVisibility(GONE);
                        letterContainer.startAnimation(animation);
                        return true;
                }
                return false;
            }
        });
    }

    private void sliderFadeOut() {
        mHandler.removeCallbacks(fadeRunnable);
        mHandler.post(fadeRunnable);
    }

    private void addTextView(String str) {
        TextView textView = new TextView(getContext());
        textView.setText(str);
        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        lllp.weight = 1;
        addView(textView, lllp);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        padLeft = getWidth() / 4;
        padRight = getWidth() / 4;

        sliderHeight = (int)((getHeight() / sections.length) * 2.0);
        if(sliderHeight > getHeight() / 2) {
            sliderHeight = getHeight() / 2;
        }

        sliderWidth = getWidth() - padLeft - padRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(drawSlider) {
            float left = 0.0f + (float)padLeft;
            float top = touchY - ((float)(sliderHeight / 2));
            float right = ((float)sliderWidth) + left;

            if(top < 0.0f)
                top = 0.0f;

            if(top > ((float)getHeight() - sliderHeight))
                top = (float)getHeight() - sliderHeight;

            float bottom = top + (float)sliderHeight;
            float radius = (float)(sliderWidth/2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(left, top, right, bottom, radius, radius, sliderPaint);
            } else {
                canvas.drawRect(left, top, right, bottom, sliderPaint);
            }
        }
    }
}
