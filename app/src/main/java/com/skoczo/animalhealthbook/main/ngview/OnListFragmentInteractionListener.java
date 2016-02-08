package com.skoczo.animalhealthbook.main.ngview;

import com.skoczo.animalhealthbook.main.AnimalData;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnListFragmentInteractionListener {
        void onSelection(int count, AnimalData item);
        void onItemClick(AnimalData mItem);
}
