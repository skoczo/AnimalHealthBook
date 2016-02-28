package com.skoczo.animalhealthbook.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by skoczo on 13.01.16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    private boolean picked = false;
    private Calendar date;
    private OnDatePeak addAnimal;

    public void setActivity(OnDatePeak addAnimal) {
        this.addAnimal = addAnimal;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(picked) {
            addAnimal.datePickerListener();
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date.set(YEAR, year);
        date.set(MONTH, monthOfYear);
        date.set(DAY_OF_MONTH, dayOfMonth);

        picked = true;

        addAnimal.datePickerListener();
    }

    public void setDate(long date) {
        this.date.setTimeInMillis(date);

        picked = true;

        addAnimal.datePickerListener();
    }

    public void setDate(Calendar date) {
        this.date  = date;
    }
    public Calendar getDate() { return  date; }

    public boolean isPicked() { return  picked; }
}