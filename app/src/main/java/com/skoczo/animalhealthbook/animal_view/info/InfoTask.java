package com.skoczo.animalhealthbook.animal_view.info;

import android.content.Context;
import android.os.AsyncTask;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.skoczo.animalhealthbook.R;
import com.skoczo.database.Animal;
import com.skoczo.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by skoczo on 26.01.16.
 */
public class InfoTask extends AsyncTask<Void, Void, Void> {

    private final String id;
    private Context context;
    private AnimalInfo fragment;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DatabaseHelper dbHelper;

    public InfoTask(Context c, String _id) {
        dbHelper = new DatabaseHelper(c);
        id = _id;
        context = c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RuntimeExceptionDao<Animal, Integer> animalDao = dbHelper.getAnimalRuntimeDao();

        Animal animal = animalDao.queryForId(Integer.parseInt(id));

        if (animal == null) {
            return null;
        }

        /* name */
        fragment.setName(animal.getNAME());

        /* birth */
        Calendar cal  = Calendar.getInstance();
        fragment.setBorn(dateFormat.format(animal.getBIRTH()));

        /* age */
        long current = Calendar.getInstance().getTime().getTime();
        long age =  current - animal.getBIRTH().getTime();

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
        fragment.setWeight(Long.toString(animal.getWEIGHT()));

        switch (animal.getTYPE()) {
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
        fragment.setBreed(animal.getBREED());

        return null;
    }

    public void setFragment(AnimalInfo fragment) { 
        this.fragment = fragment;
    }
}
