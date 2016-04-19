package com.skoczo.animalhealthbook.edit;

import android.content.Context;
import android.os.AsyncTask;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.add.AddAnimal;
import com.skoczo.database.Animal;
import com.skoczo.database.DatabaseHelper;

/**
 * Created by skoczo on 06.02.16.
 */
public class EditTask extends AsyncTask<Void, Void, Void> {
    private final String id;
    private final AddAnimal addAnimal;
    private final DatabaseHelper dbHelper;

    public EditTask(String id, AddAnimal addAnimal, Context context) {
        dbHelper = new DatabaseHelper(context);
        this.id = id;
        this.addAnimal = addAnimal;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RuntimeExceptionDao<Animal, Integer> simpleDao = dbHelper.getAnimalRuntimeDao();
        final  Animal animal = simpleDao.queryForId(Integer.parseInt(id));

        addAnimal.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((EditText) addAnimal.findViewById(R.id.animal_name)).setText(animal.getNAME());

                addAnimal.editTimePickerDialog(animal.getBIRTH().getTime());

                // TODO: hide or disable editing
                ((EditText)addAnimal.findViewById(R.id.weight)).setText(Long.toString(animal.getWEIGHT()));
                ((EditText)addAnimal.findViewById(R.id.weight)).setInputType(InputType.TYPE_NULL);

                ((Spinner)addAnimal.findViewById(R.id.spinner)).setSelection(animal.getTYPE());

                ((TextView)addAnimal.findViewById(R.id.animal_add_breed)).setText(animal.getBREED());
            }
        });



        return null;
    }
}
