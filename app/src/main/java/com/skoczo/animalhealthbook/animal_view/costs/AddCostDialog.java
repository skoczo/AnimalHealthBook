package com.skoczo.animalhealthbook.animal_view.costs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.add.DatePickerFragment;
import com.skoczo.animalhealthbook.add.OnDatePeak;
import com.skoczo.animalhealthbook.animal_view.costs.type.CostType;
import com.skoczo.animalhealthbook.animal_view.costs.type.CostTypes;
import com.skoczo.database.Cost;
import com.skoczo.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by skoczo on 13.02.16.
 */
public class AddCostDialog extends DialogFragment implements OnDatePeak{

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DatePickerFragment datepicker = new DatePickerFragment();
    private Calendar cost_time = Calendar.getInstance();
    private Button cost_date;
    private String id;
    private EditText cost;
    private DatabaseHelper dbHelper;
    private EditText comment;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        dbHelper = new DatabaseHelper(getContext());
        final RuntimeExceptionDao<Cost, Integer> costDao = dbHelper.getCostRuntimeDao();

        datepicker.setDate(cost_time);
        datepicker.setActivity(this);

        View view = inflater.inflate(R.layout.add_cost_dialog, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.add_cost_layout);

        cost = (EditText)view.findViewById(R.id.cost);

        final Spinner spinner = (Spinner) view.findViewById(R.id.add_cost_dialog_spinner);

        cost_date = (Button) view.findViewById(R.id.add_cost_date);
        cost_date.setText(df.format(cost_time.getTime()));

        cost_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker.show(getFragmentManager(),"");

            }
        });

        comment = (EditText)view.findViewById(R.id.comment);

        CostTypes types = CostTypes.getIntance();

        ArrayAdapter<CostType> dataAdapter = new ArrayAdapter<CostType>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<CostType>(types.types.values()));
        spinner.setAdapter(dataAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cost costEntity = new Cost();
                costEntity.setANIMAL_ID(Integer.parseInt(id));
                costEntity.setDATE(cost_time.getTime());
                costEntity.setPRICE(Float.parseFloat(cost.getText().toString()));
                costEntity.setTYPE(((CostType) spinner.getSelectedItem()).getName());
                costEntity.setCOMMENT(comment.getText().toString());

                if(cost.getText().length() == 0) {
                    Toast.makeText(getContext(), R.string.empty_cost, Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = costDao.create(costEntity);
                if(result <= 0) {
                    Toast.makeText(getContext(), "Can't add cost",Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void datePickerListener() {
        cost_date.setText(df.format(cost_time.getTime()));
    }

    public void setId(String id) {
        this.id = id;
    }
}
