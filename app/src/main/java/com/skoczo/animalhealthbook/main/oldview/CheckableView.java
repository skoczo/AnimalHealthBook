package com.skoczo.animalhealthbook.main.oldview;

import android.content.Context;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.skoczo.animalhealthbook.R;

/**
 * Created by skoczo on 17.01.16.
 */
class CheckableView extends LinearLayout implements Checkable{

    private boolean checked;

    public CheckableView(Context context) {
        super(context);
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        LinearLayout frame = (LinearLayout)findViewById(R.id.frame);
        if (checked) {
            frame.setBackground(getResources().getDrawable(R.drawable.border_selected));
        } else {
            frame.setBackground(getResources().getDrawable(R.drawable.border));
        }

    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
