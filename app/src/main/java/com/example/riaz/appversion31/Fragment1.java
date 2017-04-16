package com.example.riaz.appversion31;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.example.riaz.appversion31.R.layout.fragment_fragment1;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout myLayout;
    private OnFragmentInteractionListener mListener;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);                            //inflate the fragments layout
        myLayout = (LinearLayout) view;                                                                         //get the reference to the LinearLayout of the fragments layout
        return view;                                                                                            //return the fragments layout as a View
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/
    //Called by Activities to ensure that the navigation bar is made properly
    public void constructNavBar(String activity_name, Context context){              //note that AppCompatActivity is essentially a Context
        ImageButton[] imageButton = new ImageButton[5];
        //converting 77 density independent pixels to respective normal pixels value
        final float scale = getContext().getResources().getDisplayMetrics().density;
        //int pixels = (int) (64 * scale + 0.5f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT, 0.20f);               //set the layout_width to be 77dp and layout height to "wrap_content".
        //lp.weight = (float )0.20;
        //LinearLayout myLayout = findViewById(R.id.fragment_fragment1);
        //setContentView(R.id.fragment_fragment1);
        for(int i = 0; i < 5; i++){
            imageButton[i] = new ImageButton(context);                  //make ImageButton object
        }
        if(activity_name == "home"){
            imageButton[0].setImageResource(R.drawable.nav_bar_1_selected);         //set the images
            imageButton[1].setImageResource(R.drawable.nav_bar_2_unselected);
            imageButton[2].setImageResource(R.drawable.nav_bar_3_unselected);
            imageButton[3].setImageResource(R.drawable.nav_bar_4_unselected);
            imageButton[4].setImageResource(R.drawable.nav_bar_5_unselected);
        } else if(activity_name == "statistics"){
            imageButton[0].setImageResource(R.drawable.nav_bar_1_unselected);         //set the images
            imageButton[1].setImageResource(R.drawable.nav_bar_2_selected);
            imageButton[2].setImageResource(R.drawable.nav_bar_3_unselected);
            imageButton[3].setImageResource(R.drawable.nav_bar_4_unselected);
            imageButton[4].setImageResource(R.drawable.nav_bar_5_unselected);
        } else if (activity_name == "tuning"){
            imageButton[0].setImageResource(R.drawable.nav_bar_1_unselected);         //set the images
            imageButton[1].setImageResource(R.drawable.nav_bar_2_unselected);
            imageButton[2].setImageResource(R.drawable.nav_bar_3_selected);
            imageButton[3].setImageResource(R.drawable.nav_bar_4_unselected);
            imageButton[4].setImageResource(R.drawable.nav_bar_5_unselected);
        } else if(activity_name == "friends"){
            imageButton[0].setImageResource(R.drawable.nav_bar_1_unselected);         //set the images
            imageButton[1].setImageResource(R.drawable.nav_bar_2_unselected);
            imageButton[2].setImageResource(R.drawable.nav_bar_3_unselected);
            imageButton[3].setImageResource(R.drawable.nav_bar_4_selected);
            imageButton[4].setImageResource(R.drawable.nav_bar_5_unselected);
        } else if (activity_name == "settings") {
            imageButton[0].setImageResource(R.drawable.nav_bar_1_unselected);         //set the images
            imageButton[1].setImageResource(R.drawable.nav_bar_2_unselected);
            imageButton[2].setImageResource(R.drawable.nav_bar_3_unselected);
            imageButton[3].setImageResource(R.drawable.nav_bar_4_unselected);
            imageButton[4].setImageResource(R.drawable.nav_bar_5_selected);
        }
        for(int i = 0; i < 5; i++){
            //imageButton[i] = new ImageButton(context);                             //make the viewObject
            imageButton[i].setBackgroundColor(Color.rgb(109,119,131));                   //the background should be gray
            //imageButton[i].setAlpha(1f);
            imageButton[i].setLayoutParams(lp);                                     //assign desired layout_height and layout_width
            imageButton[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageButton[i].setId(i);                                                //set ID for each imagebutton
            myLayout.addView(imageButton[i]);                                       //finally, create these imageButtons
            //determine if setting layout width and heights to "wrap_content" is needed
            imageButton[i].setOnClickListener(                                          //set an event listener for clicking button
                    new View.OnClickListener() {                                //make an instance of OnClickListener, basically an event listener
                        @Override
                        public void onClick(View v) {               //run activitySwitch() when button is clicked
                            mListener.activitySwitch(v);
                        }
                    }
            );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener{
        public void activitySwitch(View v);
    }

    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
