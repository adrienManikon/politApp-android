package enyx.ch.politapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import enyx.ch.politapp.fragment.ImageSlideFragment;

/**
 * Created by adrien.manikon on 03.07.15.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    List<ImageSlideFragment> slideFragments;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<ImageSlideFragment> slideFragments) {
        super(fm);
        this.slideFragments = slideFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return slideFragments.get(position);
    }

    @Override
    public int getCount() {
        return slideFragments.size();
    }

}
