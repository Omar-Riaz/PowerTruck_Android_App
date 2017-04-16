package com.example.riaz.appversion31;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class TuningActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener{

    //public String EXTRA_ADDRESS;
    private BluetoothDevice device;
    private BluetoothSocket socket = null;
    private OutputStream outputStream;
    private Toast toast;
    private Switch switch2;
    private SeekBar seekBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuning);


        ////////////////////////////////////////////////////////
        ////////////////////SETTING UP CUSTOM TOOLBAR
        ////////////////////////////////////////////////////////
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);             //get toolbar view
        //setSupportActionBar(toolbar);                                       //make it a toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);             //get toolbar view
        setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////
        ////////////////////CREATING NAVIGATION BAR
        ////////////////////////////////////////////////////////
        Fragment1 fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        fragment1.constructNavBar("tuning", this);

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

        //By default, disable the UI components
        switch2 = (Switch) findViewById(R.id.switch2);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        switch2.setEnabled(false);
        seekBar1.setEnabled(false);

        ////////////////////////////////////////////////////////
        ////////////////////Handler that HANDLES ALL THE MESSAGES SENT FROM EVERY THREAD
        ////////////////////////////////////////////////////////
        /**
         * the handler is a class because that way each instance can have a context with it, allowing me to use toast messages
         */
        final class ConnectHandler extends Handler{
            private Context context;
            public ConnectHandler(Context context){
                this.context = context;
            }
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0){
                    toast = Toast.makeText(this.context, "Connected!", Toast.LENGTH_SHORT);
                    toast.show();
                    seekBar1.setEnabled(true);
                    switch2.setEnabled(true);
                    setListenerEvents();
                }else if(msg.what == 1){
                    toast = Toast.makeText(this.context, "Go to app settings to connect to the device", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(msg.what == 2){
                    toast = Toast.makeText(this.context, "Make sure device is on!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }

        final ConnectHandler connectHandler = new ConnectHandler(this);

        Thread connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Fragment5 fragment8 = new Fragment5();
                device = fragment8.checkConnection();
                if(device == null){                                //fragment returned no device
                    connectHandler.sendEmptyMessage(1);
                }                         //If not running this if statement, then proper device was found!
                else {
                    Fragment4 fragment9 = new Fragment4();
                    socket = fragment9.prepareConnection(device);                                                   //get a connected socket using the found device
                    if(socket != null) {
                        try {
                            outputStream = socket.getOutputStream();                                                             //get InputStream
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        connectHandler.sendEmptyMessage(2);
                    }
                    if(outputStream != null && socket != null)  connectHandler.sendEmptyMessage(0);
                }
            }
        });
        connectThread.start();
    }

    ////////////////////////////////////////////////////////
    ////////////////////IMPLEMENTATION OF FRAGMENT1 INTERACTION METHOD, TO ALLOW FOR SWITCHING OF ACTIVITIES
    ////////////////////////////////////////////////////////
    public void activitySwitch(View v){
        Intent intent = null;
        int imageButtonID = v.getId();                  //get the image ID
        //based on imageID, specify the activity we want to start!
        if(imageButtonID == 0){
            intent = new Intent(this, HomeActivity.class);
        } else if(imageButtonID == 1){
            intent = new Intent(this, StatisticsActivity.class);
        } else if(imageButtonID == 2){
            return;
        } else if(imageButtonID == 3){
            //Intent intent = new Intent(this, Activity.class);                                         //I still need to make friends activity oops....
            return;
        } else if(imageButtonID == 4){
            intent = new Intent(this, SettingsActivity.class);
        }
        //intent.putExtra("device_address",EXTRA_ADDRESS);
        if(toast != null){ toast.cancel();                  }
        if(socket != null){
            try {
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
    }

    //Listeners that send data to arduino upon changing the seekbar progress
    public void setListenerEvents(){
        if(outputStream != null) {
            switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    byte i;
                    if (isChecked) {
                        i = 3;
                    } else {
                        i = 2;
                    }
                    try {
                        outputStream.write(i);                                                                  //write either 1 or 0...
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            seekBar1.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            try {
                                outputStream.write(progress + 4);                                         //discrete values from 4-7 (inclusive) can be sent
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );
        }
    }

}
