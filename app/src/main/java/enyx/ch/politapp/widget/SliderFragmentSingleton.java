package enyx.ch.politapp.widget;

import enyx.ch.politapp.fragment.ImageSlideFragment;

/**
 * Created by adrien.manikon on 14.07.15.
 */
public class SliderFragmentSingleton {

    private static SliderFragmentSingleton _instance;

    private ImageSlideFragment fragment;

    public static SliderFragmentSingleton getInstance() {

        if (_instance == null) {
            _instance = new SliderFragmentSingleton();
        }

        return _instance;
    }

    public ImageSlideFragment getFragment() {
        return fragment;
    }

    public void setFragment(ImageSlideFragment fragment) {
        fragment.setAnimationDone(false);
        this.fragment = fragment;
    }

    public void resumeFragment() {
        fragment.resume();
    }
}
