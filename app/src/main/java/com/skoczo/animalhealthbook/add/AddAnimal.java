package com.skoczo.animalhealthbook.add;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.edit.EditTask;
import com.skoczo.database.AnimalsProvider;
import com.skoczo.database.DbHelper;
import com.skoczo.helpers.UiHelpers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddAnimal extends AppCompatActivity{

    private Calendar bornDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DatePickerFragment newFragment;
    private Integer type = null;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        final Activity actual = this;

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final List<String> types = Arrays.asList(getResources().getStringArray(R.array.types));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, types);

        final ImageView image = (ImageView)findViewById(R.id.add_animal_image);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = position;

                switch (position) {
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.cat_silhouette));
                        break;
                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.dog_silhouette));
                        break;
                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.cow_silhouette));
                        break;
                    case 3:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.horse_silhouette));
                        break;
                };

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button save = (Button)findViewById(R.id.animal_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String name = ((EditText)findViewById(R.id.animal_name)).getText().toString();

                if(name == null || name.length() == 0) {
                    UiHelpers.showToast(actual, R.string.no_name_error, Toast.LENGTH_SHORT);
                    return;
                }

                if(newFragment == null || !newFragment.isPicked()) {
                    UiHelpers.showToast(actual, R.string.no_born_date_error, Toast.LENGTH_SHORT);
                    return;
                }

                String weight = ((EditText)findViewById(R.id.weight)).getText().toString();
                if(weight == null || weight.length() == 0) {
                    UiHelpers.showToast(actual, R.string.no_weight_error, Toast.LENGTH_SHORT);
                    return;
                }

                if(type == null) {
                    UiHelpers.showToast(actual, R.string.no_type_error, Toast.LENGTH_SHORT);
                    return;
                }

                String breed = ((TextView)findViewById(R.id.animal_add_breed)).getText().toString();
                if(breed.isEmpty()) {
                    UiHelpers.showToast(actual, R.string.no_breed_error, Toast.LENGTH_SHORT);
                    return;
                }

                ContentValues values = new ContentValues();
                values.put(AnimalsProvider.AnimalEntry.COLUMN_NAME, name);
                values.put(AnimalsProvider.AnimalEntry.COLUMN_BIRTH, Long.toString(newFragment.getDate().getTime().getTime()));
                values.put(AnimalsProvider.AnimalEntry.COLUMN_WEIGHT, Integer.parseInt(weight));
                values.put(AnimalsProvider.AnimalEntry.COLUMN_TYPE, type);
                values.put(AnimalsProvider.AnimalEntry.COLUMN_BREED, breed);

                long row;

                if(id == null) {
                    row = db.insert(AnimalsProvider.AnimalEntry.TABLE_NAME, null, values);
                } else {
                    row = db.update(AnimalsProvider.AnimalEntry.TABLE_NAME, values, AnimalsProvider.AnimalEntry._ID + " == " + id, null);
                }
                if(row == -1) {
                    UiHelpers.showToast(actual, "Cannot " + id != null ? "update" : "add" + " animal to db", Toast.LENGTH_SHORT);
                } else {
                    UiHelpers.showToast(actual, "Animal succesfully " + id != null ? "updated" : "added", Toast.LENGTH_SHORT);
                            actual.onBackPressed();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("id")!= null) {
            id=bundle.getString("id");
            EditTask edit = new EditTask(id, this);
            edit.execute();
        }
    }

    public void showTimePickerDialog(View v) {
        newFragment = new DatePickerFragment();
        newFragment.setActivity(this);
        newFragment.setDate(bornDate);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void editTimePickerDialog(long date) {
        newFragment = new DatePickerFragment();
        newFragment.setActivity(this);
        newFragment.setDate(bornDate);
        newFragment.setDate(date);
    }

    public void datePickerListener() {
        ((Button)findViewById(R.id.born_date_button)).setText(dateFormat.format(bornDate.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}