package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()){
                if (info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
}
