package com.skoczo.animalhealthbook.animal_view.costs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skoczo.animalhealthbook.R;
import com.skoczo.database.Cost;

import java.util.List;

/**
 * Created by skoczo on 26.04.16.
 */
public class CostListViewAdapter extends ArrayAdapter<Cost> {
    private final List<Cost> mValues;

    public CostListViewAdapter(Context context, int resource, List<Cost> mValues) {
        super(context, resource, mValues);
        this.mValues = mValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_animal_costs_item, parent, false);

        ((TextView) view.findViewById(R.id.id)).setText(Float.toString(mValues.get(position).getPRICE()));
        ((TextView) view.findViewById(R.id.content)).setText(mValues.get(position).getTYPE());
        ((TextView) view.findViewById(R.id.comment)).setText(mValues.get(position).getCOMMENT());


        return view;
    }

}
