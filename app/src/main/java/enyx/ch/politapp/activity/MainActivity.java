package enyx.ch.politapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ogaclejapan.arclayout.ArcLayout;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import enyx.ch.politapp.R;
import enyx.ch.politapp.fragment.AboutMeFragment;
import enyx.ch.politapp.fragment.CalendarFragment;
import enyx.ch.politapp.fragment.ContactFragment;
import enyx.ch.politapp.fragment.GalerieFragment;
import enyx.ch.politapp.fragment.HowToFragment;
import enyx.ch.politapp.fragment.NewsFragment;
import enyx.ch.politapp.fragment.QuizzFragment;
import enyx.ch.politapp.utils.AnimatorUtils;
import enyx.ch.politapp.utils.ShareUtils;
import enyx.ch.politapp.widget.ClipRevealFrame;

public class MainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private ResideMenu resideMenu;
    private ResideMenuItem itemAboutMe;
    private ResideMenuItem itemHowTo;
    private ResideMenuItem itemGalerie;
    private ResideMenuItem itemNews;
    private ResideMenuItem itemQuizz;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemContact;

    private View priorityView;

    View rootLayout;
    ClipRevealFrame menuLayout;
    ArcLayout arcLayout;
    View centerItem;
    private boolean shareLayoutOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpMenu();
        setUpShare();

        if (savedInstanceState == null) {
            changeFragment(new AboutMeFragment());
        }
    }

    public void setPriorityView(View priorityView) {
        this.priorityView = priorityView;
    }

    private void setUpShare() {

        rootLayout = findViewById(R.id.root);
        menuLayout = (ClipRevealFrame) findViewById(R.id.menu_layout);
        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        centerItem = findViewById(R.id.center_item);

        centerItem.setOnClickListener(this);

        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }

        findViewById(R.id.title_bar_right_share).setOnClickListener(this);

    }

    private void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemAboutMe = new ResideMenuItem(this, R.drawable.icon_uber_mich, R.string.profil_name);
        itemHowTo = new ResideMenuItem(this, R.drawable.icon_wie_wahlen, R.string.how_to);
        itemGalerie = new ResideMenuItem(this, R.drawable.icon_galerie, R.string.galerie);
        itemNews = new ResideMenuItem(this, R.drawable.icon_news, R.string.news);
        itemQuizz = new ResideMenuItem(this, R.drawable.icon_quizz, R.string.quizz_name);
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_kalendar, R.string.calendar);
        itemContact = new ResideMenuItem(this, R.drawable.icon_kontakt, R.string.contact);

        itemAboutMe.setOnClickListener(this);
        itemHowTo.setOnClickListener(this);
        itemGalerie.setOnClickListener(this);
        itemNews.setOnClickListener(this);
        itemQuizz.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemContact.setOnClickListener(this);

        resideMenu.addMenuItem(itemAboutMe, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHowTo, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemGalerie, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemNews, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemQuizz, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemContact, ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

    private void changeFragment(Fragment targetFragment) {
        clearView();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void clearView() {
        resideMenu.clearIgnoredViewList();
        priorityView = null;
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {

        if (priorityView != null && isInsidePriorityView(ev)) {
            return priorityView.dispatchTouchEvent(ev);
        } else {
            return resideMenu.dispatchTouchEvent(ev);
        }

//        return resideMenu.dispatchTouchEvent(ev);
    }

    private boolean isInsidePriorityView(MotionEvent ev) {

        Rect rect = new Rect();
        priorityView.getGlobalVisibleRect(rect);

        return rect.contains((int) ev.getX(), (int) ev.getY());
    }

    @Override
    public void onClick(View view) {

        if (view == itemAboutMe) {
            changeFragment(new AboutMeFragment());
        } else if (view == itemHowTo) {
            changeFragment(new HowToFragment());
        } else if (view == itemGalerie) {
            changeFragment(new GalerieFragment());
        } else if (view == itemNews) {
            changeFragment(new NewsFragment());
        } else if (view == itemCalendar) {
            changeFragment(new CalendarFragment());
        } else if (view == itemQuizz) {
            changeFragment(new QuizzFragment());
        } else if (view == itemContact) {
            changeFragment(new ContactFragment());
        }

        switch (view.getId()) {
            case R.id.title_bar_right_share:
                onShareButtonClick(view);
                return;
            case R.id.menu_layout:
                return;
        }

        if (view instanceof ImageButton) {
            share((ImageButton) view);
            return;
        }

        closeLayouts();
    }

    private void closeLayouts() {

        resideMenu.closeMenu();

        if (shareLayoutOpened) {
            onShareButtonClick(findViewById(R.id.title_bar_right_share));
        }

    }

    private void share(ImageButton button) {

        switch (button.getId()) {
            case R.id.share_email:
                ShareUtils.ShareWith(this, ShareUtils.KindShare.EMAIL);
                break;
            case R.id.share_message:
                ShareUtils.ShareWith(this, ShareUtils.KindShare.MESSAGE);
                break;
            case R.id.share_facebook:
                ShareUtils.ShareWith(this, ShareUtils.KindShare.FACEBOOK);
                break;
            case R.id.share_google:
                ShareUtils.ShareWith(this, ShareUtils.KindShare.GOOGLE);
                break;
            case R.id.share_twitter:
                ShareUtils.ShareWith(this, ShareUtils.KindShare.TWITTER);
                break;
        }
    }

    private void onShareButtonClick(View v) {

        int x = (v.getLeft() + v.getRight()) / 2;
        int y = (v.getTop() + v.getBottom()) / 2;
        float radiusOfFab = 1f * v.getWidth() / 2f;
        float radiusFromFabToRoot = (float) Math.hypot(
                Math.max(x, rootLayout.getWidth() - x),
                Math.max(y, rootLayout.getHeight() - y));

        if (v.isSelected()) {
            hideMenu(x, y, radiusFromFabToRoot, radiusOfFab);
        } else {
            showMenu(x, y, radiusOfFab, radiusFromFabToRoot);
        }
        v.setSelected(!v.isSelected());

    }

    private void showMenu(int cx, int cy, float startRadius, float endRadius) {

        menuLayout.setVisibility(View.VISIBLE);
        shareLayoutOpened = true;

        List<Animator> animList = new ArrayList<>();

        Animator revealAnim = createCircularReveal(menuLayout, cx, cy, startRadius, endRadius);
        revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        revealAnim.setDuration(200);

        animList.add(revealAnim);
        animList.add(createShowItemAnimator(centerItem));

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animList);
        animSet.start();
    }

    private void hideMenu(int cx, int cy, float startRadius, float endRadius) {

        shareLayoutOpened = false;
        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        animList.add(createHideItemAnimator(centerItem));

        Animator revealAnim = createCircularReveal(menuLayout, cx, cy, startRadius, endRadius);
        revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        revealAnim.setDuration(200);
        revealAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.GONE);
            }
        });

        animList.add(revealAnim);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animList);
        animSet.start();

    }

    private Animator createCircularReveal(final ClipRevealFrame view, int x, int y, float startRadius,
                                          float endRadius) {
        final Animator reveal;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            reveal = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
        } else {
            view.setClipOutLines(true);
            view.setClipCenter(x, y);
            reveal = ObjectAnimator.ofFloat(view, "ClipRadius", startRadius, endRadius);
            reveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setClipOutLines(false);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return reveal;
    }

    private Animator createShowItemAnimator(View item) {
        float dx = centerItem.getX() - item.getX();
        float dy = centerItem.getY() - item.getY();

        item.setScaleX(0f);
        item.setScaleY(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(0f, 1f),
                AnimatorUtils.scaleY(0f, 1f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(50);
        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        final float dx = centerItem.getX() - item.getX();
        final float dy = centerItem.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(1f, 0f),
                AnimatorUtils.scaleY(1f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.setInterpolator(new DecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        anim.setDuration(50);
        return anim;
    }

    public void addIgnoredView(View view) {
        resideMenu.addIgnoredView(view);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(this, Integer.toString(event.getAction()), Toast.LENGTH_SHORT);
        return false;
    }
}
