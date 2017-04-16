package com.example.riaz.appversion31;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public class StatisticsActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {

    private BluetoothDevice device;
    //private String EXTRA_ADDRESS;
    private InputStream inputStream = null;
    private BluetoothSocket socket = null;
    private UUID UUID;
    private Toast toast;
    private String[] strings = new String[3];
    private Fragment2 fragment2, fragment3, fragment4, fragment5, fragment6, fragment7;
    private boolean stopThread = true;
    private int counter = 0;
    private String string;
    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        ////////////////////////////////////////////////////////
        ////////////////////SETTING UP CUSTOM TOOLBAR
        ////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);             //get toolbar view
        setSupportActionBar(toolbar);                                       //make it a toolbar

        ////////////////////////////////////////////////////////
        ////////////////////CREATING THE NAVIGATION BAR
        ////////////////////////////////////////////////////////
        Fragment1 fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        fragment1.constructNavBar("statistics", this);


        ////////////////////////////////////////////////////////
        ////////////////////LET MAIN THREAD SLEEP TO PREVENT CRASHES
        ////////////////////////////////////////////////////////
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                return;
            }
        }, 1000);

        ////////////////////////////////////////////////////////
        ////////////////////Add text and pic...
        ////////////////////////////////////////////////////////
        fragment6 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment6);
        fragment6.constructOutput("CURRENT BATTERY CHARGE", false, R.drawable.battery2, "--", "% charged");


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
                    connected = true;
                    stopThread = false;
                    toast = Toast.makeText(this.context, "Connected!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(msg.what == 1){
                    toast = Toast.makeText(this.context, "Go to app settings to connect to the device", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(msg.what == 2) {
                    toast = Toast.makeText(this.context, "Make sure device is on!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(msg.what == 3) {
                    fragment6.constructOutput("CURRENT BATTERY CHARGE", false, 0, string, "% charged");
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
                            inputStream = socket.getInputStream();                                                             //get InputStream
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        connectHandler.sendEmptyMessage(2);
                    }
                    if(inputStream != null && socket != null)  connectHandler.sendEmptyMessage(0);
                }
            }
        });
        connectThread.start();


        final Thread thread  = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() &&  !stopThread)
                {
                    try
                    {
                        if(connected) {                     //some functionality that runs only when activity receives information for the first time
                            connected = false;
                        }
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            string = new String(rawBytes,"UTF-8");
                            Log.i("Get Data Thread", "Running Get Data Thread");
                            connectHandler.sendEmptyMessage(3);
                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        //thread.start();                                         //WILL NOT WORK. THE THREAD STARTS BUT DOES NOT OPERATE --> PROBABLY BECAUSE OF BLOCKING CALLS THAT PREVENT OTHER THREADS FROM RUNNING...

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable(){                    //this will work because you have assured that there are no more blocking calls
            @Override
            public void run(){
                thread.start();
            }
        }, 10000);
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
        } else if (imageButtonID == 1){
            return;
        } else if(imageButtonID == 2){
            intent = new Intent(this, TuningActivity.class);
        } else if(imageButtonID == 3){
            //intent = new Intent(this, Activity.class);
            return;
        } else if(imageButtonID == 4){
            intent = new Intent(this, SettingsActivity.class);                                        //I still need to make friends activity oops....
        }
        //intent.putExtra("device_address", EXTRA_ADDRESS);
        if(toast != null){   toast.cancel();            }
        if(socket != null){
            try {
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
    }
}
