package com.elatienda.kaytamarka.sofra.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetState {
    static ConnectivityManager conMgr;

    static public boolean isConnected(Context context) {
        try {
            conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (NullPointerException e) {

        }

        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

//    public boolean isOnline(Context context) {
//        // Check Connectivity
//        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//        // if internet Not Access
//        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
//            //Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        // if Internet Access
//        return true;
//    }
}
