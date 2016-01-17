package com.skoczo.animalhealthbook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoczo.database.AnimalsProvider;
import com.skoczo.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skoczo on 13.01.16.
 */
public class ImageAdapter extends BaseAdapter {
    private String TAG = ImageAdapter.class.getName();
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;
    public static List<AnimalData> animals;

    public ImageAdapter(Context c) {
        mContext = c;
        dbHelper = new DbHelper(mContext);
        db = dbHelper.getReadableDatabase();

        loadAnimals();
    }

    public void loadAnimals() {
        Cursor weatherCursor = mContext.getContentResolver().query(
                AnimalsProvider.AnimalEntry.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        animals = new ArrayList<AnimalData>();

        if(weatherCursor.getCount() == 0) {
            return;
        }

        weatherCursor.moveToFirst();

        do {
            AnimalData animal = new AnimalData();
            animal.name = weatherCursor.getString(weatherCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_NAME));
            animal.id = weatherCursor.getInt(weatherCursor.getColumnIndex(AnimalsProvider.AnimalEntry._ID));
            animals.add(animal);

        }while (weatherCursor.moveToNext());
    }

    public int getCount() {
        return animals.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.animal_main_layout, null);

        ImageView image = (ImageView)view.findViewById(R.id.imageView);
//        image.setPadding(8, 8, 8, 8);

        ((TextView)view.findViewById(R.id.mainAnimalName)).setText(animals.get(position).name);
        ((TextView)view.findViewById(R.id.db_id)).setText(animals.get(position).id.toString());

        return view;

    }
}

class AnimalData {
    public String name;
    public Integer id;
}
