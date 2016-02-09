package com.skoczo.animalhealthbook.main.ngview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private static int colors[] = { Color.parseColor("#9E7394"), Color.parseColor("#77DD77"), Color.parseColor("#77DD77"), Color.parseColor("#CFCFC4")};
    private final Drawable horse;
    private final Drawable cow;
    private final Drawable dog;
    private final Drawable cat;

    // Start with first item selected
    private int focusedItem = -1;

    public MyItemRecyclerViewAdapter(List<AnimalData> items, OnListFragmentInteractionListener listener, Context ctx) {
        mValues = items;
        mListener = listener;

        cat = ctx.getResources().getDrawable(R.drawable.cat_silhouette);
        dog = ctx.getResources().getDrawable(R.drawable.dog_silhouette);
        cow = ctx.getResources().getDrawable(R.drawable.cow_silhouette);
        horse = ctx.getResources().getDrawable(R.drawable.horse_silhouette);
    }

    public void resetSelection()
    {
        focusedItem = -1;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_main_layout, parent, false);

        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.db_id.setText(mValues.get(position).id.toString());
        holder.mainAnimalName.setText(mValues.get(position).name);
        holder.setBackground(position);
        holder.setImage();

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
        private int position;


        public ViewHolder(View view, Context context) {
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
        
        public void setImage()
        {
            ImageView image = (ImageView) mView.findViewById(R.id.imageView);

            switch (mItem.type) {
                case 0:
                    image.setImageDrawable(cat);
                    break;
                case 1:
                    image.setImageDrawable(dog);
                    break;
                case 2:
                    image.setImageDrawable(cow);
                    break;
                case 3:
                    image.setImageDrawable(horse);
                    break;
            };
        }
        

        public void setBackground(int position) {
            this.position = position;
//            mView.setBackgroundColor(colors[position%colors.length]);
        }
    }
}
