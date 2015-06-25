package enyx.ch.politapp.helper;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import enyx.ch.politapp.activity.MainActivity;

/**
 * Created by adrien.manikon on 25.06.15.
 */
public class ToastHelper {

    /** @hide */
    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    private static ToastHelper _instance;

    private Toast toast;

    public static ToastHelper getInstance() {

        if (_instance == null) {
            _instance = new ToastHelper();
        }

        return _instance;
    }


    public void showMessage(Activity mContext, int idMessage, @Duration int duration) {

        cleanToast();
        toast = Toast.makeText(mContext, idMessage, duration);
        toast.show();

    }

    public void showMessageShort(Activity mContext, int idMessage) {
        showMessage(mContext, idMessage, Toast.LENGTH_SHORT);
    }

    public void showMessageLong(Activity mContext, int idMessage) {
        showMessage(mContext, idMessage, Toast.LENGTH_LONG);
    }

    private void cleanToast() {

        if (toast != null) {
            toast.cancel();
        }

    }
}
