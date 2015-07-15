package enyx.ch.politapp.widget;

import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by adrien.manikon on 03.07.15.
 */
public class SlidePagerListener extends ViewPager.SimpleOnPageChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.v("Slider page listener", String.valueOf(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
