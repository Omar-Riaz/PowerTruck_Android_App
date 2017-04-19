package com.example.riaz.appversion31;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;


public class HomeActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {

    //private String EXTRA_ADDRESS;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    //private Switch switch1, switch2;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ////////////////////////////////////////////////////////
        ////////////////////SETTING UP CUSTOM TOOLBAR
        ////////////////////////////////////////////////////////
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////
        ////////////////////CREATING THE NAVIGATION BAR
        ////////////////////////////////////////////////////////
        Fragment1 fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment1);                    //get reference to fragment_1..
        fragment1.constructNavBar("home", this);                        //call the method to construct the navigation bar

        ////////////////////////////////////////////////////////
        ////////////////////
        ////////////////////////////////////////////////////////
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                return;
            }
        }, 1000);

        ////////////////////////////////////////////////////////
        ////////////////////CREATING UI, FIRST MAKING DATA OUTPUT FRAGMENTS AND THEN SOME TEXTVIEWS
        ////////////////////////////////////////////////////////
        Fragment2 fragment2 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment2.constructOutput("ODOMETER", true, R.drawable.odometer, "150", "miles");                     //odometer

        Fragment2 fragment3 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        fragment3.constructOutput("AVG SPEED", true, R.drawable.speedmeter, "55", "mph");                     //avg speed

        Fragment2 fragment4 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment4);
        fragment4.constructOutput("BATTERY", true, R.drawable.battery2, "50", "% charged");                   //battery charge

    }



    ////////////////////////////////////////////////////////
    ////////////////////SWITCH EVENT LISTENERS
    ////////////////////////////////////////////////////////
    public void activitySwitch(View v) {
        Intent intent = null;
        int imageButtonID = v.getId();                  //get the image ID
        //based on imageID, specify the activity we want to start!
        if (imageButtonID == 0) {
            return;
        } else if (imageButtonID == 1) {
            intent = new Intent(this, StatisticsActivity.class);
        } else if (imageButtonID == 2) {
            intent = new Intent(this, TuningActivity.class);
        } else if (imageButtonID == 3) {
            //Intent intent = new Intent(this, Activity.class);                                         //I still need to make friends activity oops....
            return;
        } else if (imageButtonID == 4) {
            intent = new Intent(this, SettingsActivity.class);
        }
        //intent.putExtra("device_address", EXTRA_ADDRESS);
        if(toast != null){   toast.cancel();                }                                                                 //to prevent a gazillion toasts from being displayed!!!
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
    }

}