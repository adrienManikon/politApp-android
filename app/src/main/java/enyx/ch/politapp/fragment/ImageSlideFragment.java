package enyx.ch.politapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import enyx.ch.politapp.R;
import enyx.ch.politapp.utils.AnimatorUtils;

/**
 * Created by adrien.manikon on 03.07.15.
 */
public class ImageSlideFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    private static final String ARG_PAGE = "page";
    private int pageNumber;
    private RelativeLayout layout;
    private ImageView imageView;
    private TextView textHeader;

    private boolean animationDone = false;

    public ImageSlideFragment() {
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public static ImageSlideFragment create(int mPageNumber) {

        ImageSlideFragment fragment = new ImageSlideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, mPageNumber);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.image_header_slider, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.image_profile);
        layout = (RelativeLayout) rootView.findViewById(R.id.sub_layout_header);
        textHeader = (TextView) rootView.findViewById(R.id.text_sub_header);
        textHeader.setText("page " + String.valueOf(pageNumber));

//        rootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                rootView.dispatchTouchEvent(event);
//                return false;
//            }
//        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//
//        animImageHeader(imageView, layout, textHeader);
    }

    public void animImageHeader(ImageView imageView, RelativeLayout layout, final TextView textHeader) {

        if (!animationDone) {
            animationDone = true;
            AnimatorUtils.startAnimation(imageView, R.anim.slide_left_to_right);
            AnimatorUtils.startAnimation(layout, R.anim.slide_up_to_down, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    AnimatorUtils.startAnimation(textHeader, R.anim.shake);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        Log.v(String.valueOf(ImageSlideFragment.class), "On click");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v(String.valueOf(ImageSlideFragment.class), "On touch");
        return false;
    }

    public void resume() {
        animImageHeader(imageView, layout, textHeader);
    }

    public boolean isAnimationDone() {
        return animationDone;
    }

    public void setAnimationDone(boolean animationDone) {
        this.animationDone = animationDone;
    }

}