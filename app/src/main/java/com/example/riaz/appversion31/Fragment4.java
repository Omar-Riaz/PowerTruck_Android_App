package com.example.riaz.appversion31;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/*
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {

    //private ParcelUuid[] ParcelUUID;
    //private UUID UUID;
    private UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket bluetoothSocket;
    private BluetoothServerSocket bluetoothServerSocket;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    public BluetoothSocket prepareConnection(BluetoothDevice device){
        //UUID = device.getUuids().toString();                                               //first get the device UUID. becomes type String
        //UUID = UUID.fromString(UUIDs);                                            //Take the String and make it into a UUID
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();                                                         //preparing for creating RFCOMM sockets

        try {
            //bluetoothServerSocket =  bluetoothAdapter.listenUsingRfcommWithServiceRecord("PowerTruck",UUID);              //create open BluetoothServerSocket with the hardcoded UUID, name is arbitrarilly assigned

            bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);                       //Client (the module) creates an RFCOMM socket using the provided UUID
            bluetoothSocket.connect();                                                              //connect to client
            //BluetoothSocket bluetoothSocket2 = bluetoothServerSocket.accept(12000);                                                                                                 //timeout of 12 seconds
            return bluetoothSocket;
        } catch (IOException e) {                                                                   //throwing exception. so Connection not succesfull...
            e.printStackTrace();
            //Toast.makeText(context, "Make sure device is on!", Toast.LENGTH_SHORT).show();                //could not create socket
            return null;                                                                            //if not sucessful, indicate by returnign a useless socket
        }
    }
}
