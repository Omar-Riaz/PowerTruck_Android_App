package com.example.riaz.appversion31;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment5 extends Fragment {

    //private int REQUEST_ENABLE_BT = 1;
    //private boolean enabled = false;
    private String DEVICE_NAME = "HC-06";
    //public boolean found = false;                                                                       //components outside this class will access this value to see if they can transmit addresses, initate connections, etc.
    //private String EXTRA_ADDRESS = "20:13:10:15:33:66";
    //private BluetoothDevice device;
    private BluetoothDevice device = null;

    public Fragment5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    public BluetoothDevice checkConnection(){


        //Get the local bluetooth adapter. If it doesn't exist, device doesn't support bluetooth
        BluetoothAdapter bluetoothAdapter = (BluetoothAdapter) BluetoothAdapter.getDefaultAdapter();

        //Now search for device in list of paired devices
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();                    //get list of all paired devies

        for (BluetoothDevice iterator:bondedDevices){
            if(iterator.getName().equals(DEVICE_NAME)){
                //found = true;
                device = iterator;
                //Toast.makeText(this, "Device found!", Toast.LENGTH_SHORT).show();
            }
        }
        return device;
    }


}
