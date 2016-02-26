package com.skoczo.animalhealthbook.animal_view.costs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.animal_view.costs.type.CostType;
import com.skoczo.animalhealthbook.animal_view.costs.type.CostTypes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by skoczo on 13.02.16.
 */
public class AddCostDialog extends DialogFragment {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DatePickerDialog datepicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_cost_dialog, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.add_cost_layout);

        Spinner spinner = (Spinner) view.findViewById(R.id.add_cost_dialog_spinner);

        CostTypes types = CostTypes.getIntance();

        ArrayAdapter<CostType> dataAdapter = new ArrayAdapter<CostType>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<CostType>(types.types.values()));
        spinner.setAdapter(dataAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setTitle(R.string.add_cost)
                .setView(view);

        Button date = (Button)view.findViewById(R.id.add_cost_date);
        date.setText(df.format(Calendar.getInstance().getTime()));

        return builder.create();
    }
}
