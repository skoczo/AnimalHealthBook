package com.skoczo.animalhealthbook.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.animal_view.AnimalView;
import com.skoczo.database.AnimalsProvider;
import com.skoczo.database.DbHelper;
import com.skoczo.helpers.UiHelpers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.loadAnimals();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.loadAnimals();
        adapter.notifyDataSetChanged();
    }

    public void search(String search) {
        adapter.loadAnimals(search);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        final GridView gridview = (GridView) view.findViewById(R.id.gridview);

        adapter = new ImageAdapter(getActivity());
        adapter.notifyDataSetChanged();
        gridview.setAdapter(adapter);

        gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);

        gridview.setMultiChoiceModeListener(new GridView.MultiChoiceModeListener() {
            private String TAG = GridView.MultiChoiceModeListener.class.getName();

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Delete");
                mode.setSubtitle("One item selected");
                mode.getMenuInflater().inflate(R.menu.delete_menu, menu);
                return true;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int selectCount = gridview.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }

                if (item.getItemId() == R.id.animal_delete) {
                    DbHelper dbHelper = new DbHelper(getContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    SparseBooleanArray checked = gridview.getCheckedItemPositions();
                    String where = buildWhere(checked);

                    if (where.length() == 0) {
                        Log.e(TAG, "where statement empty");
                    } else {
                        int deleted = db.delete(AnimalsProvider.AnimalEntry.TABLE_NAME, where, null);

                        UiHelpers.showToast(getActivity(), "" + deleted + " animals deleted", Toast.LENGTH_SHORT);
                    }
                    adapter.loadAnimals();

                    mode.finish();
                    gridview.deferNotifyDataSetChanged();
                    return true;
                }

                return false;
            }

            private String buildWhere(SparseBooleanArray checked) {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < checked.size(); i++) {
                    if (checked.valueAt(i)) {

                        sb.append(AnimalsProvider.AnimalEntry._ID + " = " + ImageAdapter.animals.get(checked.keyAt(i)).id + " or ");
                    }
                }

                if (sb.length() > 4) {
                    sb.setLength(sb.length() - 4);
                } else {
                    Log.e(TAG, "where is empty. Checked length:" + checked.size());
                }

                return sb.toString();
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                int selectCount = gridview.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }

                gridview.deferNotifyDataSetChanged();
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), AnimalView.class);
                intent.putExtra("key", ((TextView) v.findViewById(R.id.db_id)).getText());
                startActivity(intent);
            }
        });

        return view;
    }
}
