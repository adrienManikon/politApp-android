package enyx.ch.politapp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import enyx.ch.politapp.adapter.ScreenSlidePagerAdapter;
import enyx.ch.politapp.fragment.ImageSlideFragment;

/**
 * Created by adrien.manikon on 15.07.15.
 */
public class ViewSlider extends ViewPager {

    public ViewSlider(Context context) {
        super(context);
    }

    public ViewSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new OnPageChangeListener());
    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            SliderFragmentSingleton fragmentSingleton = SliderFragmentSingleton.getInstance();
            fragmentSingleton.setFragment((ImageSlideFragment) ((ScreenSlidePagerAdapter) getAdapter()).getItem(position));
            fragmentSingleton.resumeFragment();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
