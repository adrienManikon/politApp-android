package enyx.ch.politapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import enyx.ch.politapp.R;
import enyx.ch.politapp.activity.BaseActivity;

/**
 * Created by adrien.manikon on 26.06.15.
 */
public abstract class FragmentListViewBase<S extends Scrollable> extends Fragment implements ObservableScrollViewCallbacks {

    protected View mHeader;
    protected int mFlexibleSpaceImageHeight;
    protected View mHeaderBar;
    protected View mListBackgroundView;
    protected int mActionBarSize;
    protected int mIntersectionHeight;

    private View mImage;
    private View mHeaderBackground;
    private int mPrevScrollY;
    private boolean mGapIsChanging;
    private boolean mGapHidden;
    private boolean mReady;
    protected BaseActivity parentActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parentActivity = (BaseActivity) getActivity();

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarSize = parentActivity.getActionBarSize();

        // Even when the top gap has began to change, header bar still can move
        // within mIntersectionHeight.
        mIntersectionHeight = getResources().getDimensionPixelSize(R.dimen.intersection_height);

        mImage = parentActivity.findViewById(R.id.slider);
        mHeader = parentActivity.findViewById(R.id.header);
        mHeaderBar = parentActivity.findViewById(R.id.header_bar);
        mHeaderBackground = parentActivity.findViewById(R.id.header_background);
        mListBackgroundView = parentActivity.findViewById(R.id.list_background);

        final S scrollable = createScrollable();

        ScrollUtils.addOnGlobalLayoutListener((View) scrollable, new Runnable() {
            @Override
            public void run() {
                mReady = true;
                updateViews(scrollable.getCurrentScrollY(), false);
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        updateViews(scrollY, true);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    protected abstract S createScrollable();

    protected void updateViews(int scrollY, boolean animated) {
        // If it's ListView, onScrollChanged is called before ListView is laid out (onGlobalLayout).
        // This causes weird animation when onRestoreInstanceState occurred,
        // so we check if it's laid out already.
        if (!mReady) {
            return;
        }
        // Translate image
        ViewHelper.setTranslationY(mImage, -scrollY / 2);

        // Translate header
        ViewHelper.setTranslationY(mHeader, getHeaderTranslationY(scrollY));

        // Show/hide gap
        final int headerHeight = mHeaderBar.getHeight();
        boolean scrollUp = mPrevScrollY < scrollY;
        if (scrollUp) {
            if (mFlexibleSpaceImageHeight - headerHeight - mActionBarSize <= scrollY) {
                changeHeaderBackgroundHeightAnimated(false, animated);
            }
        } else {
            if (scrollY <= mFlexibleSpaceImageHeight - headerHeight - mActionBarSize) {
                changeHeaderBackgroundHeightAnimated(true, animated);
            }
        }
        mPrevScrollY = scrollY;
    }

    protected float getHeaderTranslationY(int scrollY) {
        final int headerHeight = mHeaderBar.getHeight();
        int headerTranslationY = mActionBarSize - mIntersectionHeight;
        if (0 <= -scrollY + mFlexibleSpaceImageHeight - headerHeight - mActionBarSize + mIntersectionHeight) {
            headerTranslationY = -scrollY + mFlexibleSpaceImageHeight - headerHeight;
        }
        return headerTranslationY;
    }


    private void changeHeaderBackgroundHeightAnimated(boolean shouldShowGap, boolean animated) {

        if (mGapIsChanging) {
            return;
        }

        final int heightOnGapShown = mHeaderBar.getHeight();
        final int heightOnGapHidden = mHeaderBar.getHeight() + mActionBarSize;
        final float from = mHeaderBackground.getLayoutParams().height;
        final float to;

        if (shouldShowGap) {

            if (!mGapHidden) {
                // Already shown
                return;
            }
            to = heightOnGapShown;

        } else {

            if (mGapHidden) {
                // Already hidden
                return;
            }
            to = heightOnGapHidden;

        }

        if (animated) {

            ViewPropertyAnimator.animate(mHeaderBackground).cancel();
            ValueAnimator a = ValueAnimator.ofFloat(from, to);
            a.setDuration(100);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float height = (float) animation.getAnimatedValue();
                    changeHeaderBackgroundHeight(height, to, heightOnGapHidden);
                }
            });
            a.start();

        } else {
            changeHeaderBackgroundHeight(to, to, heightOnGapHidden);

        }
    }

    private void changeHeaderBackgroundHeight(float height, float to, float heightOnGapHidden) {

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mHeaderBackground.getLayoutParams();

        lp.height = (int) height;
        lp.topMargin = (int) (mHeaderBar.getHeight() - height);
        mHeaderBackground.requestLayout();
        mGapIsChanging = (height != to);

        if (!mGapIsChanging) {
            mGapHidden = (height == heightOnGapHidden);
        }
    }
}
