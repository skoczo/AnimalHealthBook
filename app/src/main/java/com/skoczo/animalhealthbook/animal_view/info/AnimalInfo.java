package com.skoczo.animalhealthbook.animal_view.info;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.add.AddAnimal;
import com.skoczo.animalhealthbook.animal_view.DynamicFabUpdater;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalInfo extends Fragment implements Serializable, DynamicFabUpdater {
    private String id;
    private InfoTask infoTask;
    private ImageView image;
    private TextView name;
    private TextView bornText;
    private TextView ageText;
    private TextView weight;
    private TextView breed;

    public AnimalInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            id = savedInstanceState.getString("id");
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_animal_info, container, false);

        image = (ImageView) v.findViewById(R.id.animal_info_img);
        name = (TextView) v.findViewById(R.id.animal_view_name);
        bornText = (TextView) v.findViewById(R.id.animal_view_born);
        ageText = (TextView) v.findViewById(R.id.animal_view_age);
        weight = (TextView) v.findViewById(R.id.animal_view_weight);
        breed = (TextView) v.findViewById(R.id.animal_view_breed);

        infoTask = new InfoTask(getContext(), id);
        infoTask.setFragment(this);
        infoTask.execute();

        // TODO: listener

        fabUpdate();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        infoTask = new InfoTask(getContext(), id);
        infoTask.setFragment(this);
        infoTask.execute();
    }

    @Override
    public void fabUpdate() {
//        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.animal_view_fab);
//        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_edit));
//        fab.setVisibility(View.VISIBLE);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), AddAnimal.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.animal_view_edit_action) {
            Intent intent = new Intent(getContext(), AddAnimal.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", id);
    }

    public static AnimalInfo newInstance(String key) {
        AnimalInfo info = new AnimalInfo();
        info.setId(key);
        return info;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(final Drawable imageDraw) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                image.setImageDrawable(imageDraw);
            }
        });
    }

    public void setName(final String nameString) {
        setText(name, nameString);
    }

    public void setWeight(final String nameString) {
        setText(weight, nameString);
    }

    public void setAge(final String ageString) {
        setText(ageText, ageString);
    }

    public void setBreed(final String ageString) {
        setText(breed, ageString);
    }

    public void setBorn(final String bornString) {
        setText(bornText, bornString);
    }

    private void setText(final TextView v, final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setText(text);
            }
        });
    }
}
