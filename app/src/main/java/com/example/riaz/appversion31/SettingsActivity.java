package com.example.riaz.appversion31;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Handler;

import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {

    private int REQUEST_ENABLE_BT = 1;
    private boolean enabled = false;
    private String DEVICE_ADDRESS;
    public boolean found = false;
    private String EXTRA_ADDRESS;
    private BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ////////////////////////////////////////////////////////
        ////////////////////SETTING UP CUSTOM TOOLBAR
        ////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);             //get toolbar view
        setSupportActionBar(toolbar);                                       //make it a toolbar


        ////////////////////////////////////////////////////////
        ////////////////////CREATING NAVIGATION BAR
        ////////////////////////////////////////////////////////
        Fragment1 fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        fragment1.constructNavBar("settings", this);

        ////////////////////////////////////////////////////////
        ////////////////////LET MAIN THREAD SLEEP TO PREVENT CRASHES
        ////////////////////////////////////////////////////////
        Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        return;
                    }
                }, 1000);

        /*////////////////////////////////////////////////////////
        ////////////////////CREATING AND/OR USING EVENT LISTENERS FOR BUTTONS
        ////////////////////////////////////////////////////////
        Button button = (Button) findViewById(R.id.button1);*/

        ////////////////////////////////////////////////////////
        ////////////////////GET ADDRESS FROM INTENT FROM OTHER ACTIVITY
        ////////////////////////////////////////////////////////
        /*Intent intent = getIntent();                                                                                 //get intent and the data in it
        //intent.getStringExtra("device_address");
        Fragment3 fragment3 = new Fragment3();
        device = fragment3.getDevice(this, intent);                                   //get the device
        EXTRA_ADDRESS = intent.getStringExtra("device_address");                      //get the address
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_ENABLE_BT){                           //if the request is the intended request
            if(resultCode == RESULT_OK){                //if request went well
                Toast.makeText(this, "Bluetooth succesfully enabled, now searching for device...", Toast.LENGTH_SHORT).show();
                enabled = true;
                Button button = (Button) findViewById(R.id.button1);
                connectBT(button);                                                        //since the program doesn't go back to connectBT(), call this again!
            } else{                                     //request did not go well...
                Toast.makeText(this ,"Bluetooth not enabled. Note that device will not work without enabling bluetooth!", Toast.LENGTH_LONG).show();
                return;                                                                         //exit out of connectBT
            }
        }
    }

    public void connectBT(View view){

        //Get the local bluetooth adapter. If it doesn't exist, device doesn't support bluetooth
        BluetoothAdapter bluetoothAdapter = (BluetoothAdapter) BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(this, "Mobile device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }

        //ask to enable bluetooth
        if(!bluetoothAdapter.isEnabled()){
            enabled = false;
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else{
            enabled = true;
        }

        if(!enabled)    return;                                                                         //Don't proceed to

        //Now search for device in list of paired devices
        Set <BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();                    //get list of all paired devies

        if(!bondedDevices.isEmpty()) {
            for (BluetoothDevice iterator:bondedDevices){
                 if(iterator.getName().equals("HC-06")){
                     found = true;
                     EXTRA_ADDRESS = iterator.getAddress();                                             //store address for passing onto other activities via intents
                     Toast.makeText(this, "Device Found!",Toast.LENGTH_LONG).show();
                     DEVICE_ADDRESS = EXTRA_ADDRESS;
                     //Toast.makeText(this, "Device found!", Toast.LENGTH_SHORT).show();
                     return;
                 }
            }
            Toast.makeText(this, "Please pair the device in Phone Settings > Bluetooth with passcode '1234'", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please pair the device in Phone Settings > Bluetooth with passcode '1234'", Toast.LENGTH_LONG).show();
        }
    }


    ////////////////////////////////////////////////////////
    ////////////////////IMPLEMENTATION OF FRAGMENT1 INTERACTION METHOD, TO ALLOW FOR SWITCHING OF ACTIVITIES
    ////////////////////////////////////////////////////////
    public void activitySwitch(View v) {
        Intent intent = null;
        int imageButtonID = v.getId();                  //get the image ID
        //based on imageID, specify the activity we want to start!
        if (imageButtonID == 0) {
            intent = new Intent(this, HomeActivity.class);
        } else if (imageButtonID == 1) {
            intent = new Intent(this, StatisticsActivity.class);
        } else if (imageButtonID == 2) {
            intent = new Intent(this, TuningActivity.class);
        } else if (imageButtonID == 3) {
            //intent = new Intent(this, SettingsActivity.class);                                        //I still need to make friends activity oops....
            return;
        } else if (imageButtonID == 4) {
            return;
        }
        intent.putExtra("device_address",EXTRA_ADDRESS);
        startActivity(intent);
    }
}