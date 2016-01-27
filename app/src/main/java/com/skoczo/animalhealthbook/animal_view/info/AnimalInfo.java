package com.skoczo.animalhealthbook.animal_view.info;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoczo.animalhealthbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalInfo extends Fragment {
    private String id;
    private InfoTask infoTask;
    private ImageView image;
    private TextView name;
    private TextView bornText;
    private TextView ageText;
//    private OnFragmentInteractionListener mListener;

    public AnimalInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        infoTask = new InfoTask(getContext(), id);
        infoTask.setFragment(this);
        infoTask.execute();

        return v;
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

    public void setAge(final String ageString) {
        setText(ageText, ageString);
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
