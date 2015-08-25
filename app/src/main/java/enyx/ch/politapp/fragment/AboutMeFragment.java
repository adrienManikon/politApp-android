package enyx.ch.politapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import enyx.ch.politapp.R;
import enyx.ch.politapp.activity.MainActivity;
import enyx.ch.politapp.adapter.ListImageTextAdapter;
import enyx.ch.politapp.adapter.ScreenSlidePagerAdapter;
import enyx.ch.politapp.api.ArticleApi;
import enyx.ch.politapp.model.Article;
import enyx.ch.politapp.widget.ViewSlider;

/**
 * Created by adrien.manikon on 25.06.15.
 */
public class AboutMeFragment extends FragmentListViewBase<ObservableListView> implements ObservableScrollViewCallbacks {

    private ViewSlider viewSlider;
    private ObservableListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_me, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        viewSlider = (ViewSlider) parentActivity.findViewById(R.id.slider);
        ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), getListSlideFragments());
        viewSlider.setAdapter(mPagerAdapter);
        viewSlider.startAutoSlide(mPagerAdapter.getCount());

        if (parentActivity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) parentActivity;

            mainActivity.addIgnoredView(viewSlider);
            mainActivity.setPriorityView(viewSlider);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewSlider.isAutoSlide())
            viewSlider.stopAutoSlide();
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
        listView = (ObservableListView) parentActivity.findViewById(R.id.scroll);
        listView.setScrollViewCallbacks(this);
        setListView(mFlexibleSpaceImageHeight);
        return listView;
    }

    @Override
    protected void updateViews(int scrollY, boolean animated) {
        super.updateViews(scrollY, animated);

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, ViewHelper.getTranslationY(mHeader));
    }

    protected void setListView(int headerHeight) {

        View headerView = new View(parentActivity);

        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
        headerView.setMinimumHeight(headerHeight);
        // This is required to disable header's list selector effect
        headerView.setClickable(true);

        listView.addHeaderView(getHeaderView(headerHeight));
        listView.setAdapter(getAdapterForListView());
    }

    private ListAdapter getAdapterForListView() {
        return new ListImageTextAdapter(parentActivity, R.layout.article_item, getListFeed());
    }

    private View getHeaderView(int headerHeight) {

        View headerView = new View(parentActivity);

        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
        headerView.setMinimumHeight(headerHeight);
        // This is required to disable header's list selector effect
        headerView.setClickable(true);

        return headerView;

    }

    public static ArrayList<Article> getListFeed() {
        return ArticleApi.getFeed();
    }
}
