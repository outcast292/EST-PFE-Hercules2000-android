package com.hercules2000.control;
import android.os.*;
import android.util.Log;

import java.net.*;
import java.io.*;


public class connectionUtils {


    public static Socket socket;

    public static String SERVER_IP ;
    public static int SERVER_PORT ;
    private static String mServerMessage;
    private static OnMessageReceived mMessageListener = null;
    private static boolean mRun = false;
    
    private static PrintWriter mBufferOut;
    private static BufferedReader mBufferIn;

    public connectionUtils(OnMessageReceived listener) {
        mMessageListener = listener;

    }

    public static Socket getSocket() {
        return socket;
    }

    public static boolean ismRun() {
        return mRun;
    }

    public static void sendMessage(String message) {
        if (mBufferOut != null && !mBufferOut.checkError()) {
            message = message + "\r\n";
            mBufferOut.println(message);
            Log.e("Hercules 2000", "C: " + message);

            mBufferOut.flush();
        }
    }

    public static StringBuffer readMessage() {
        int i = 0;
        StringBuffer response = new StringBuffer("");
        try {
            Log.e("Hercules 2000", "C: Waiting for response ...");
                response = response.append(mBufferIn.readLine());


        } catch(Exception e) {
            e.printStackTrace();
        }

        Log.e("Hercules 2000", "S: " + response);

        return response;
    }

    public static void stopClient() {
        Log.i("Hercules 2000", "stopClient");
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

            Log.e("Hercules 2000", "C: Connecting...");

            socket = new Socket(serverIP,SERVER_PORT);

            Log.e("Hercules 2000", "C: Connected : " + socket.isConnected());
            mRun = true;
            initIO();

        } catch (Exception e) {
            Log.e("Hercules 2000", "C: Error", e);
        }

    }

    public static void initIO(){
        try {
            Log.i("Hercules 2000", "I/O inititalized");

            mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (Exception e) {
            Log.e("Hercules 2000", "S: Error", e);
        }
    }


    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
    }
