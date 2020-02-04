package com.hercules2000.control;
import android.os.*;
import android.util.Log;

import java.net.*;
import java.io.*;


public class ConnectionHandler {

    public static String SERVER_IP ; //your computer IP address
    public static int SERVER_PORT ;
    private static String mServerMessage;
    private static OnMessageReceived mMessageListener = null;
    private static boolean mRun = false;
    
    private static PrintWriter mBufferOut;
    private static BufferedReader mBufferIn;

    public ConnectionHandler(OnMessageReceived listener) {
        mMessageListener = listener;

    }

    public static Boolean isConnected(){
        if(mRun == true)
            return true;
        else
            return false;
    }
    public static void sendMessage(String message) {
        if (mBufferOut != null && !mBufferOut.checkError()) {
            mBufferOut.println(message);
            mBufferOut.flush();
        }
    }

    public static void stopClient() {
        Log.i("Debug", "stopClient");
        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public static void run(String IP, int port) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(tp);
        }

        SERVER_IP = IP;
        SERVER_PORT = port;



        try {
            InetAddress serverIP = InetAddress.getByName(SERVER_IP);

            Log.e("TCP Client", "C: Connecting...");

            Socket socket = new Socket(serverIP,SERVER_PORT);
            try {
                mRun = true;
                Log.i("Debug", "inside try catch");

                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
    }
