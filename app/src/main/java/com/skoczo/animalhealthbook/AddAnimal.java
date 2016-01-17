package com.skoczo.animalhealthbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skoczo.database.AnimalsProvider;
import com.skoczo.database.DbHelper;
import com.skoczo.helpers.UiHelpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAnimal extends AppCompatActivity{

    private Calendar bornDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DatePickerFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        final Activity actual = this;

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
                    UiHelpers.showToast(actual, R.string.no_name_weight, Toast.LENGTH_SHORT);
                    return;
                }

                ContentValues values = new ContentValues();
                values.put(AnimalsProvider.AnimalEntry.COLUMN_NAME, name);
                values.put(AnimalsProvider.AnimalEntry.COLUMN_BIRTH, newFragment.getDate().getTime().getTime());
                values.put(AnimalsProvider.AnimalEntry.COLUMN_WEIGHT, Integer.parseInt(weight));

                long row = db.insert(AnimalsProvider.AnimalEntry.TABLE_NAME, null, values);
                if(row == -1) {
                    UiHelpers.showToast(actual, "Cannot add animal to db", Toast.LENGTH_SHORT);
                } else {
                    UiHelpers.showToast(actual, "Animal succesfully added", Toast.LENGTH_SHORT);
                    actual.onBackPressed();
                }
            }
        });
    }

    public void showTimePickerDialog(View v) {
        newFragment = new DatePickerFragment();
        newFragment.setDate(bornDate);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void datePickerListener() {
        Toast.makeText(getApplicationContext(), "Date picked", Toast.LENGTH_SHORT).show();
        ((Button)findViewById(R.id.born_date_button)).setText(dateFormat.format(bornDate.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}