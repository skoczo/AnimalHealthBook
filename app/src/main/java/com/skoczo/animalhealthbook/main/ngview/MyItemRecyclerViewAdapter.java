package com.skoczo.animalhealthbook.main.ngview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.main.AnimalData;
import com.skoczo.animalhealthbook.main.ngview.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<AnimalData> mValues;
    private final OnListFragmentInteractionListener mListener;

    // Start with first item selected
    private int focusedItem = -1;

    public MyItemRecyclerViewAdapter(List<AnimalData> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void resetSelection()
    {
        focusedItem = -1;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_main_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.db_id.setText(mValues.get(position).id.toString());
        holder.mainAnimalName.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.mItem);
                }
            }
        });

        holder.itemView.setSelected(focusedItem == position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView db_id;
        public final TextView mainAnimalName;
        public AnimalData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            db_id = (TextView) view.findViewById(R.id.db_id);
            mainAnimalName = (TextView) view.findViewById(R.id.mainAnimalName);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    notifyItemChanged(focusedItem);

                    if(focusedItem == getLayoutPosition()) {
                        focusedItem = -1;
                        mListener.onSelection(0, mItem);
                    } else {
                        focusedItem = getLayoutPosition();
                        mListener.onSelection(1, mItem);
                    }

                    notifyItemChanged(focusedItem);



                    return true;
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mainAnimalName.getText() + "'";
        }
    }
}
