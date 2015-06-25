package enyx.ch.politapp.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

import enyx.ch.politapp.PolitApplication;
import enyx.ch.politapp.R;
import enyx.ch.politapp.helper.ToastHelper;

/**
 * Created by adrien.manikon on 25.06.15.
 */
public class ShareUtils {

    private static final String APPLICATION_TWITTER = "com.twitter.android.composer.ComposerActivity";
    private static final String PACKAGE_TWITTER = "com.twitter.android";

    private static final String APPLICATION_FACEBOOK = "com.facebook.composer.shareintent.ImplicitShareIntentHandlerDefaultAlias";
    private static final String PACKAGE_FACEBOOK = "com.facebook.katana";

    private static final String APPLICATION_EMAIL = "com.google.android.gm.ComposeActivityGmail";
    private static final String PACKAGE_EMAIL = "com.google.android.gm";

    private static final String APPLICATION_MESSAGE = "com.android.mms.ui.ComposeMessageActivity";
    private static final String PACKAGE_MESSAGE = "com.android.mms";

    private static final String APPLICATION_GOOGLE = "com.google.android.libraries.social.gateway.GatewayActivity";
    private static final String PACKAGE_GOOGLE = "com.google.android.apps.plus";


    public static void ShareWith(Activity activity, KindShare kindShare) {

        Intent intent = null;

        switch (kindShare) {
            case EMAIL:
                intent = getIntent(APPLICATION_EMAIL, PACKAGE_EMAIL);
                break;
            case MESSAGE:
                intent = getIntent(APPLICATION_MESSAGE, PACKAGE_MESSAGE);
                break;
            case GOOGLE:
                intent = getIntentPlayStore();
                break;
            case FACEBOOK:
                intent = getIntent(APPLICATION_FACEBOOK, PACKAGE_FACEBOOK);
                break;
            case TWITTER:
                intent = getIntent(APPLICATION_TWITTER, PACKAGE_TWITTER);
                break;
        }

        if (intent != null) {
            activity.startActivity(intent);
        } else {
            ToastHelper.getInstance().showMessageLong(activity, R.string.app_not_installed);
        }

    }

    private static Intent getIntentPlayStore() {

        final String appPackageName = PolitApplication.getContext().getPackageName();
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));

    }

    private static Intent getIntent(String appNameRequest, String packageName) {

        Intent intent = null;

        if (PolitApplication.isAppInstalled(packageName))// Check android app is installed
        {

            PackageManager packageManager = PolitApplication.getContext().getPackageManager();// Create instance of PackageManager
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");

            //Get List of all activities
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);
            for (int j = 0; j < resolveInfoList.size(); j++) {
                ResolveInfo resInfo = resolveInfoList.get(j);
                String appName = resInfo.activityInfo.name;

                if (appName.contains(appNameRequest)) {

                    intent = new Intent();
                    intent.setComponent(new ComponentName(resInfo.activityInfo.packageName, appName));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Nice application !");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Text Sharing");
                    intent.setPackage(appName);

                }

            }
        }

        return intent;

    }

    public enum KindShare {

        EMAIL,

        MESSAGE,

        GOOGLE,

        FACEBOOK,

        TWITTER
    }
}
