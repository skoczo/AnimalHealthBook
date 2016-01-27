package com.skoczo.animalhealthbook.animal_view.info;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.main.AnimalData;
import com.skoczo.database.AnimalsProvider;
import com.skoczo.database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by skoczo on 26.01.16.
 */
public class InfoTask extends AsyncTask<Void, Void, Void> {

    private final DbHelper dbHelper;
    private final SQLiteDatabase db;
    private final String id;
    private Context context;
    private AnimalInfo fragment;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public InfoTask(Context c, String _id) {
        dbHelper = new DbHelper(c);
        db = dbHelper.getReadableDatabase();
        id = _id;
        context = c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Cursor animalCursor = context.getContentResolver().query(
                AnimalsProvider.AnimalEntry.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                "_id = " + id, // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        if (animalCursor.getCount() == 0) {
            return null;
        }

        animalCursor.moveToFirst();

        AnimalData animal = new AnimalData();
        animal.name = animalCursor.getString(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_NAME));
        animal.id = animalCursor.getInt(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry._ID));
        animal.type = animalCursor.getInt(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_TYPE));
        animal.birth = animalCursor.getLong(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_BIRTH));

        animalCursor.close();

        fragment.setName(animal.name);
        Calendar cal  = Calendar.getInstance();
        Date d = new Date();
        d.setTime(animal.birth);
        fragment.setBorn(dateFormat.format(d));

        switch (animal.type) {
            case 0:
                fragment.setImage(context.getResources().getDrawable(R.drawable.cat_silhouette));
                break;
            case 1:
                fragment.setImage(context.getResources().getDrawable(R.drawable.dog_silhouette));
                break;
            case 2:
                fragment.setImage(context.getResources().getDrawable(R.drawable.cow_silhouette));
                break;
            case 3:
                fragment.setImage(context.getResources().getDrawable(R.drawable.horse_silhouette));
                break;
        };

        return null;
    }

    public void setFragment(AnimalInfo fragment) { 
        this.fragment = fragment;
    }
}
