package com.skoczo.animalhealthbook.edit;

import android.database.Cursor;
import android.os.AsyncTask;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.add.AddAnimal;
import com.skoczo.animalhealthbook.main.AnimalData;
import com.skoczo.database.AnimalsProvider;

/**
 * Created by skoczo on 06.02.16.
 */
public class EditTask extends AsyncTask<Void, Void, Void> {
    private final String id;
    private final AddAnimal addAnimal;

    public EditTask(String id, AddAnimal addAnimal) {
        this.id = id;
        this.addAnimal = addAnimal;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Cursor animalCursor = addAnimal.getApplicationContext().getContentResolver().query(
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

        final AnimalData animal = new AnimalData();
        animal.name = animalCursor.getString(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_NAME));
        animal.id = animalCursor.getInt(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry._ID));
        animal.type = animalCursor.getInt(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_TYPE));
        animal.birth = animalCursor.getLong(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_BIRTH));
        animal.weight = animalCursor.getLong(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_WEIGHT));
        animal.breed = animalCursor.getString(animalCursor.getColumnIndex(AnimalsProvider.AnimalEntry.COLUMN_BREED));

        addAnimal.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((EditText) addAnimal.findViewById(R.id.animal_name)).setText(animal.name);

                addAnimal.editTimePickerDialog(animal.birth);

                // TODO: hide or disable editing
                ((EditText)addAnimal.findViewById(R.id.weight)).setText(Long.toString(animal.weight));
                ((EditText)addAnimal.findViewById(R.id.weight)).setInputType(InputType.TYPE_NULL);

                ((Spinner)addAnimal.findViewById(R.id.spinner)).setSelection(animal.type);

                ((TextView)addAnimal.findViewById(R.id.animal_add_breed)).setText(animal.breed);
            }
        });



        return null;
    }
}
