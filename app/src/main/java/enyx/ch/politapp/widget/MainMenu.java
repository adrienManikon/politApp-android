package enyx.ch.politapp.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.special.ResideMenu.ResideMenu;

/**
 * Created by adrien.manikon on 03.07.15.
 */
public class MainMenu extends ResideMenu {
    public MainMenu(Context context) {
        super(context);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v("View :", this.toString());
        Log.v("action :", String.valueOf(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

}
