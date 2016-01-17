package com.skoczo.animalhealthbook;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

                    int deleted = db.delete(AnimalsProvider.AnimalEntry.TABLE_NAME, where, null);

                    UiHelpers.showToast(getActivity(), "" + deleted + " animals deleted", Toast.LENGTH_SHORT);

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
                    if (checked.get(i)) {
                        sb.append(AnimalsProvider.AnimalEntry._ID + " = " + ImageAdapter.animals.get(i).id + " or ");
                    }
                }

                sb.setLength(sb.length() - 4);

                return sb.toString();
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                if (checked) {
                    gridview.getChildAt(position).setBackgroundColor(
                            Color.parseColor("#6633b5e5"));
                } else {
                    gridview.getChildAt(position).setBackgroundColor(
                            Color.parseColor("#00000000"));
                }

                int selectCount = gridview.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                view.setBackgroundColor(Color.parseColor("#6633b5e5"));

                return true;
            }
        });

        return view;
    }
}
