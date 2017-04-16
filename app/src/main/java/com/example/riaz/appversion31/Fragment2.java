package com.example.riaz.appversion31;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/*
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {


    public Fragment2() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        return view;
    }

    public void constructOutput(String heading, boolean image, int image_R_ID, String quantity, String units) {
        TextView heading_textView = (TextView) view.findViewById(R.id.textViewh);                                   //getting references ot viewObjects
        TextView quantity_textView = (TextView) view.findViewById(R.id.textViewH);
        TextView units_textView = (TextView) view.findViewById(R.id.textViewu);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);

        if(heading == null && image == false && image_R_ID == 0 && units == null){              //special case where we just want to update quantity!
            quantity_textView.setText(quantity);
            return;
        }

        if (!image) {                                  //image not specified, therefore remove it and restructure textViews
            //RelativeLayout pView = (RelativeLayout) findViewById(R.id.fragment_fragment2);
            imageView.setVisibility(View.GONE);                                          //remove the view from sight and make it NOT take up space anymore!
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.BELOW, R.id.textViewh);                       //to place the quantity text right below the
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            quantity_textView.setLayoutParams(lp);                                  //bellow heading text due to no image

            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.BELOW, R.id.textViewh);
            lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lp2.setMargins(0,57,0,0);
            units_textView.setLayoutParams(lp2);                                     //bellow quantity text (became "H" somehow...)
        } else {
            imageView.setImageResource(image_R_ID);                     //set image to one specified when calling method
        }

        heading_textView.setText(heading);
        quantity_textView.setText(quantity);
        units_textView.setText(units);
    }
        /*  Index legend
            0 = heading
            1 = quantity
            2 = units

        TextView[] textView = new TextView[3];
        if (image){                                                            //if we ask for an image, then provide it pls...
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(image_R_ID);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);                 //set layout_width and layout_height for all Views
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);                                                                                        //center_Horizontal = true
        lp.setMargins();
        for(int i = 0; i < 3; i++){
            textView[i] = new TextView(context);
            textView[i].setLayoutParams(lp);
        }

*/

}
