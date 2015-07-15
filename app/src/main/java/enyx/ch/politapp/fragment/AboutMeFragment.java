package enyx.ch.politapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import enyx.ch.politapp.R;
import enyx.ch.politapp.activity.MainActivity;
import enyx.ch.politapp.adapter.ScreenSlidePagerAdapter;
import enyx.ch.politapp.widget.SliderFragmentSingleton;

/**
 * Created by adrien.manikon on 25.06.15.
 */
public class AboutMeFragment extends FragmentListViewBase<ObservableListView> implements ObservableScrollViewCallbacks {

    private static final int NUM_OF_ITEMS = 100;
    private static final int NUM_OF_ITEMS_FEW = 3;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_me, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPager = (ViewPager) parentActivity.findViewById(R.id.slider);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), getListSlideFragments());
        SliderFragmentSingleton.getInstance().setFragment((ImageSlideFragment) mPagerAdapter.getItem(0));
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SliderFragmentSingleton fragmentSingleton = SliderFragmentSingleton.getInstance();
                fragmentSingleton.setFragment((ImageSlideFragment) mPagerAdapter.getItem(position));
                fragmentSingleton.resumeFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (parentActivity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) parentActivity;

            mainActivity.addIgnoredView(mPager);
            mainActivity.setPriorityView(mPager);
        }

    }

    private List<ImageSlideFragment> getListSlideFragments() {

        List<ImageSlideFragment> imageSlideFragments = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            imageSlideFragments.add(ImageSlideFragment.create(i));
        }

        return imageSlideFragments;
    }

    @Override
    protected ObservableListView createScrollable() {
        ObservableListView listView = (ObservableListView) parentActivity.findViewById(R.id.scroll);
        listView.setScrollViewCallbacks(this);
        setDummyDataWithHeader(listView, mFlexibleSpaceImageHeight);
        return listView;
    }

    @Override
    protected void updateViews(int scrollY, boolean animated) {
        super.updateViews(scrollY, animated);

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, ViewHelper.getTranslationY(mHeader));
    }

    /*************
     * DUMMY DATA
     *************/


    protected void setDummyDataWithHeader(ListView listView, int headerHeight) {
        setDummyDataWithHeader(listView, headerHeight, NUM_OF_ITEMS);
    }

    protected void setDummyDataWithHeader(ListView listView, int headerHeight, int num) {
        View headerView = new View(parentActivity);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
        headerView.setMinimumHeight(headerHeight);
        // This is required to disable header's list selector effect
        headerView.setClickable(true);
        setDummyDataWithHeader(listView, headerView, num);
    }

    protected void setDummyDataWithHeader(ListView listView, View headerView, int num) {
        listView.addHeaderView(headerView);
        setDummyData(listView, num);
    }

    protected void setDummyData(GridView gridView) {
        gridView.setAdapter(new ArrayAdapter<>(parentActivity, android.R.layout.simple_list_item_1, getDummyData()));
    }

    public static ArrayList<String> getDummyData() {
        return getDummyData(NUM_OF_ITEMS);
    }

    protected void setDummyData(ListView listView) {
        setDummyData(listView, NUM_OF_ITEMS);
    }

    protected void setDummyDataFew(ListView listView) {
        setDummyData(listView, NUM_OF_ITEMS_FEW);
    }

    protected void setDummyData(ListView listView, int num) {
        listView.setAdapter(new ArrayAdapter<>(parentActivity, android.R.layout.simple_list_item_1, getDummyData(num)));
    }

    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }
}
