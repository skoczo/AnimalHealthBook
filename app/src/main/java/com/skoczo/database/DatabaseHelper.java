package com.skoczo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.skoczo.animalhealthbook.R;

import java.sql.SQLException;

/**
 * Created by skoczo on 01.04.16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "helloAndroid.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    private Dao<Animal, Integer> animalDao = null;
    private RuntimeExceptionDao<Animal, Integer> animalRuntimeDao = null;

    private Dao<Cost, Integer> costDao = null;
    private RuntimeExceptionDao<Cost, Integer> costRuntimeDao = null;

    private Dao<CostType, Integer> costTypeDao = null;
    private RuntimeExceptionDao<CostType, Integer> costTypeRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    public RuntimeExceptionDao<CostType, Integer> getCostTypeRuntimeDao() {
        if (costTypeRuntimeDao == null) {
            costTypeRuntimeDao = getRuntimeExceptionDao(CostType.class);
        }
        return costTypeRuntimeDao;
    }

    public Dao<CostType, Integer> getCostTypeDao() throws SQLException {
        if (costTypeDao == null) {
            costTypeDao = getDao(CostType.class);
        }
        return costTypeDao;
    }

    public RuntimeExceptionDao<Cost, Integer> getCostRuntimeDao() {
        if (costRuntimeDao == null) {
            costRuntimeDao = getRuntimeExceptionDao(Cost.class);
        }
        return costRuntimeDao;
    }

    public Dao<Cost, Integer> getCostDao() throws SQLException {
        if (costDao == null) {
            costDao = getDao(Cost.class);
        }
        return costDao;
    }

    public RuntimeExceptionDao<Animal, Integer> getAnimalRuntimeDao() {
        if (animalRuntimeDao == null) {
            animalRuntimeDao = getRuntimeExceptionDao(Animal.class);
        }
        return animalRuntimeDao;
    }

    public Dao<Animal, Integer> getAnimalDao() throws SQLException {
        if (animalDao == null) {
            animalDao = getDao(Animal.class);
        }
        return animalDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Animal.class);
            TableUtils.createTable(connectionSource, Cost.class);
            TableUtils.createTable(connectionSource, CostType.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    @Override
    public void close()
    {
        super.close();

        animalDao = null;
        animalRuntimeDao = null;

        costDao = null;
        costRuntimeDao = null;

        costTypeDao = null;
        costTypeRuntimeDao = null;
    }
}

