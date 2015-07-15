package enyx.ch.politapp.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

import enyx.ch.politapp.adapter.ScreenSlidePagerAdapter;
import enyx.ch.politapp.fragment.ImageSlideFragment;

/**
 * Created by adrien.manikon on 15.07.15.
 */
public class ViewSlider extends ViewPager {

    private static int INTERVAL = 4000;
    private static int START = 4000;

    private boolean autoSlide;
    private boolean slideRunning = false;
    private Handler handler;
    private int currentPage = 0;
    private int numPages;
    private Timer swipeTimer;

    public ViewSlider(Context context) {
        super(context);
    }

    public ViewSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new OnPageChangeListener());
    }

    public boolean isAutoSlide() {
        return autoSlide;
    }

    public void setAutoSlide(boolean autoSlide) {
        this.autoSlide = autoSlide;
    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public void startAutoSlide(int numPage) {

        autoSlide = true;
        this.numPages = numPage;

        if (!slideRunning) {

            handler = new Handler();

            final Runnable update = new Runnable() {
                @Override
                public void run() {

                    currentPage++;
                    if (currentPage == numPages) {
                        currentPage = 0;
                    }

                    ViewSlider.this.setCurrentItem(currentPage, true);

                }
            };

            swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, START, INTERVAL);

            slideRunning = true;

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case 2:
                stopAutoSlide();
        }

        return super.onTouchEvent(ev);
    }

    public void stopAutoSlide() {
        if (swipeTimer != null)
            swipeTimer.cancel();
        slideRunning = false;
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
            currentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
