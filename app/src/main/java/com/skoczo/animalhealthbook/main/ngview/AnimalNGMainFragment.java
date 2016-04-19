package com.skoczo.animalhealthbook.main.ngview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.main.AnimalData;
import com.skoczo.database.Animal;
import com.skoczo.database.DatabaseHelper;

import java.sql.SQLException;
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
    private DatabaseHelper helper;

    private MyItemRecyclerViewAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnimalNGMainFragment() {
    }

    public void loadAnimals() {
        RuntimeExceptionDao<Animal, Integer> simpleDao = getHelper().getAnimalRuntimeDao();
//        Cursor weatherCursor = getContext().getContentResolver().query(
//                AnimalsProvider.AnimalEntry.CONTENT_URI,  // Table to Query
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null // columns to group by
//        );

        manageAnimals(simpleDao.queryForAll());
    }

    public void loadAnimals(CharSequence s) {
        if (s.length() == 0) {
            loadAnimals();
        } else {
            try {
                RuntimeExceptionDao<Animal, Integer> simpleDao = getHelper().getAnimalRuntimeDao();

                QueryBuilder<Animal, Integer> qb = simpleDao.queryBuilder();
                Where where = qb.where();

                where.like("NAME", s + "%");

                PreparedQuery<Animal> preparedQuery = qb.prepare();

                manageAnimals(simpleDao.query(preparedQuery));
            } catch (SQLException e) {
                // TODO:
            }
        }
    }

    private void manageAnimals(List<Animal> dbAnimals) {
        if (animals == null) {
            animals = new ArrayList<AnimalData>();
        } else {
            animals.clear();
        }


        if (dbAnimals.size() == 0) {
            return;
        }

        for (Animal animal : dbAnimals) {
            AnimalData animalData = new AnimalData();
            animalData.name = animal.getNAME();
            animalData.id = animal.get_ID();
            animalData.type = animal.getTYPE();
            animalData.breed = animal.getBREED();
            animalData.birth = animal.getBIRTH().getTime();
            animalData.weight = animal.getWEIGHT();

            animals.add(animalData);
        }
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

    public DatabaseHelper getHelper() {
        if (helper == null) {
            helper = new DatabaseHelper(getContext());
        }
        return helper;
    }
}
