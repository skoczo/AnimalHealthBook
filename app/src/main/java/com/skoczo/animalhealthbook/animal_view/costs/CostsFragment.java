package com.skoczo.animalhealthbook.animal_view.costs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.skoczo.animalhealthbook.R;
import com.skoczo.database.Cost;
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
public class CostsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private CostsFragment.OnListFragmentInteractionListener mListener;
    private String id;
    private List<Cost> costs = new ArrayList<Cost>();
    private DatabaseHelper dbHelper;
    private ListView listView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CostsFragment() {
    }

    @SuppressWarnings("unused")
    public static CostsFragment newInstance(int columnCount, String key) {
        CostsFragment fragment = new CostsFragment();
        fragment.setId(key);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(getContext());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        if (savedInstanceState != null) {
            id = savedInstanceState.getString("id");
        }
        

        setHasOptionsMenu(true);
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listView = (ListView)inflater.inflate(R.layout.fragment_animal_costs_items_list, container, false);

        updateCosts();

        listView.setAdapter(new CostListViewAdapter(getContext(), R.layout.fragment_animal_costs_item, costs));

        return listView;
    }

    public ListView getListView()
    {
        return listView;
    }
    public void updateCosts() {
        RuntimeExceptionDao<Cost, Integer> costDao = dbHelper.getCostRuntimeDao();

        QueryBuilder<Cost, Integer> query = costDao.queryBuilder();
        try {
            query.where().eq("ANIMAL_ID", id);
            costs = costDao.query(query.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_animal_costs, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.animal_view_add_cost_action) {
            AddCostDialog cost = new AddCostDialog(this);
            cost.setId(this.id);
            cost.show(getFragmentManager(), this.getClass().getName());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Cost item);
    }
}
