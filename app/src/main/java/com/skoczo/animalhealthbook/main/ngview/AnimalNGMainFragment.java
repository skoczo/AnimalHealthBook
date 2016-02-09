package com.skoczo.animalhealthbook.main.ngview;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.main.AnimalData;
import com.skoczo.database.AnimalsProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AnimalNGMainFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    public static List<AnimalData> animals;
    private OnListFragmentInteractionListener mListener;

    private MyItemRecyclerViewAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnimalNGMainFragment() {
    }

    public void loadAnimals() {
        Cursor weatherCursor = getContext().getContentResolver().query(
                AnimalsProvider.AnimalEntry.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        manageAnimals(weatherCursor);
    }

    public void loadAnimals(CharSequence s) {
        Cursor weatherCursor = getContext().getContentResolver().query(
                AnimalsProvider.AnimalEntry.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                AnimalsProvider.AnimalEntry.COLUMN_NAME  + " like \"" + s + "%\"", // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        manageAnimals(weatherCursor);
    }

    private void manageAnimals(Cursor weatherCursor) {
        if(animals == null) {
            animals = new ArrayList<AnimalData>();
        } else {
            animals.clear();
        }

        if (weatherCursor.getCount() == 0) {
            return;
        }

        weatherCursor.moveToFirst();

        do {
            AnimalData animal = new AnimalData();
            animal.name = weatherCursor.getString(weatherCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_NAME));
            animal.id = weatherCursor.getInt(weatherCursor.getColumnIndex(AnimalsProvider.AnimalEntry._ID));
            animal.type = weatherCursor.getInt(weatherCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_TYPE));
            animals.add(animal);

        } while (weatherCursor.moveToNext());

        weatherCursor.close();
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnimalNGMainFragment newInstance(int columnCount) {
        AnimalNGMainFragment fragment = new AnimalNGMainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        loadAnimals();
        adapter.resetSelection();

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onStart() {
        super.onStart();

        loadAnimals();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        loadAnimals();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyItemRecyclerViewAdapter(animals, mListener, getContext());
            recyclerView.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    public void search(String newText) {
        loadAnimals(newText);
        adapter.notifyDataSetChanged();
    }
}
