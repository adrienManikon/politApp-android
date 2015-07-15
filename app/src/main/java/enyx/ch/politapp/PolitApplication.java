package enyx.ch.politapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by adrien.manikon on 25.06.15.
 */
public class PolitApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean isAppInstalled(String uri) {

        PackageManager packageManager = mContext.getPackageManager();

        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
