package enyx.ch.politapp.activity;

import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;

import enyx.ch.politapp.R;

/**
 * Created by adrien.manikon on 26.06.15.
 */
public abstract class BaseActivity extends FragmentActivity {

    public int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

}
