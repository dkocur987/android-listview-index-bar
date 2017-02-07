package com.dominikk.sidebar.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
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

    private Context context;

    private String[] sections;

    private int textColor;

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

        removeAllViews();
        setOrientation(VERTICAL);

        // przypisuje sekcje pobrane z adaptera:
        sections = (String[]) adapter.getSections();

        for (int i = 0; i < sections.length; i++) {
            addTextView(sections[i]);
        }

        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1500);
        //animation.setFillAfter(true);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        int position = (int) (event.getY() * sections.length / v.getHeight());
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

                            letterContainer.clearAnimation();
                            letterContainer.setVisibility(VISIBLE);
                            letterContainer.setAlpha(1.0f);
                        } else {
                            listView.setSelection(0);
                        }

                        return true;
                    case MotionEvent.ACTION_UP:
                        letterContainer.setVisibility(GONE);
                        letterContainer.startAnimation(animation);
                        return true;
                }
                return false;

            }
        });
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


}
