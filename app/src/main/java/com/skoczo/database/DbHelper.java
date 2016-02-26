package com.skoczo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skoczo on 16.01.16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "animalhealthbook.db";
    private static int DB_VERSION = 5;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAnimalDB(db);
        createCostDB(db);
//        createCostTypeDB(db);
    }

    private void createCostTypeDB(SQLiteDatabase db) {
        final String COSTS_DB_CREATE = "create table " + CostTypeProvider.CostTypeEntry.TABLE_NAME + "( "
                + CostTypeProvider.CostTypeEntry._ID + " INTEGER PRIMARY KEY, "
                + CostTypeProvider.CostTypeEntry.COLUMN_TYPE + " TEXT NOT NULL "
                + ");";

        db.execSQL(COSTS_DB_CREATE);
    }

    private void createCostDB(SQLiteDatabase db) {
        final String COSTS_DB_CREATE = "create table " + CostProvider.CostEntry.TABLE_NAME + "( "
                + CostProvider.CostEntry._ID + " INTEGER PRIMARY KEY, "
                + CostProvider.CostEntry.COLUMN_DATE + " INTEGER NOT NULL, "
                + CostProvider.CostEntry.COLUMN_PRICE + " REAL   NOT NULL,"
                + CostProvider.CostEntry.COLUMN_TYPE + " INTEGER NOT NULL "
                + ");";

        db.execSQL(COSTS_DB_CREATE);
    }

    private void createAnimalDB(SQLiteDatabase db) {
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

    private void v6_drop(SQLiteDatabase db) {
        final String COSTS_DB_DROP = "drop table " + CostProvider.CostEntry.TABLE_NAME + ";";
        db.execSQL(COSTS_DB_DROP);

        final String COSTS_TYPE_DB_DROP = "drop table " + CostTypeProvider.CostTypeEntry.TABLE_NAME + ";";
        db.execSQL(COSTS_TYPE_DB_DROP);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 4 && newVersion == 5) {
            createCostDB(db);
//            createCostTypeDB(db);
        } else if (oldVersion == 5 && newVersion == 6) {
            v6_drop(db);
            createCostDB(db);
        }
    }
}
