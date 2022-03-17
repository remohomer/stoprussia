package pl.com.stoprussia.core.websiteloader;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetStatus {

    private static boolean OFFLINE_MODE = false;
    private final boolean internetIsConnected;

    public Activity activity;

    public InternetStatus(Activity activity) {
        this.activity = activity;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        internetIsConnected = networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();
    }

    public static void setOfflineMode(boolean isOfflineMode) {
        OFFLINE_MODE = isOfflineMode;
    }
    public static boolean isOfflineMode() {
        return OFFLINE_MODE;
    }
    public boolean internetIsConnected() {
        return internetIsConnected;
    }
}
