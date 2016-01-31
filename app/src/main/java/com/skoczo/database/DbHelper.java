package com.skoczo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skoczo on 16.01.16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static String DB_NAME= "animalhealthbook.db";
    private static int DB_VERSION = 4;

    public DbHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String ANIMALS_DB_CREATE = "create table " + AnimalsProvider.AnimalEntry.TABLE_NAME + "( "
                + AnimalsProvider.AnimalEntry._ID + " INTEGER PRIMARY KEY, "
                + AnimalsProvider.AnimalEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + AnimalsProvider.AnimalEntry.COLUMN_BIRTH + " INTEGER NOT NULL,"
                + AnimalsProvider.AnimalEntry.COLUMN_WEIGHT + " INTEGER NOT NULL, "
                + AnimalsProvider.AnimalEntry.COLUMN_TYPE + " INTEGER NOT NULL, "
                + AnimalsProvider.AnimalEntry.COLUMN_BREED + " TEXT NOT NULL "
                + ");";

        db.execSQL(ANIMALS_DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: not delete data from DB on upgrade
        if(oldVersion == 3 && newVersion == 4) {
            db.execSQL("DROP TABLE IF EXISTS " + AnimalsProvider.AnimalEntry.TABLE_NAME);
            onCreate(db);
//            db.execSQL("ALTER TABLE " + AnimalsProvider.AnimalEntry.TABLE_NAME + " add column " + AnimalsProvider.AnimalEntry.COLUMN_BREED + " TEXT NOT NULL");
        }
    }
}
