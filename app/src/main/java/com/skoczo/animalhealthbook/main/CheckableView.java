package com.skoczo.animalhealthbook.main;

import android.content.Context;
import android.graphics.Color;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by skoczo on 17.01.16.
 */
class CheckableView extends LinearLayout implements Checkable {

    private boolean checked;

    public CheckableView(Context context) {
        super(context);
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;

        if (checked) {
            setBackgroundColor(Color.parseColor("#6633b5e5"));
        } else {
            setBackgroundColor(Color.parseColor("#00000000"));
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
