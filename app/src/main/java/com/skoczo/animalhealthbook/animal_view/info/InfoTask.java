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
        animal.weight = animalCursor.getLong(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_WEIGHT));
        animal.breed = animalCursor.getString(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_BREED));

        animalCursor.close();

        /* name */
        fragment.setName(animal.name);

        /* birth */
        Calendar cal  = Calendar.getInstance();
        Date birth = new Date();
        birth.setTime(animal.birth);
        fragment.setBorn(dateFormat.format(birth));

        /* age */
        long current = Calendar.getInstance().getTime().getTime();
        long age =  current - birth.getTime();

        long days = age / (1000 * 60 *60 *24);
        long years = days / 365;

        days = days % 365;

        // TODO: language convertion
        if(years != 0) {
            fragment.setAge(years + " " + (years > 1 ? fragment.getResources().getText(R.string.years).toString() : fragment.getResources().getText(R.string.year).toString()) + " " +  fragment.getResources().getText(R.string.and).toString() + " " +  days + " " +  (days > 1 ? fragment.getResources().getText(R.string.days).toString() : fragment.getResources().getText(R.string.day).toString()));
        } else {
            fragment.setAge(days + " " +  (days > 1 ? fragment.getResources().getText(R.string.days).toString() : fragment.getResources().getText(R.string.day).toString()));
        }

        /* weight */
        fragment.setWeight(Long.toString(animal.weight));

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

        /* breed */
        fragment.setBreed(animal.breed);

        return null;
    }

    public void setFragment(AnimalInfo fragment) { 
        this.fragment = fragment;
    }
}
