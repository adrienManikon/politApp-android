package enyx.ch.politapp.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.ogaclejapan.arclayout.ArcLayout;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import enyx.ch.politapp.R;
import enyx.ch.politapp.utils.AnimatorUtils;
import enyx.ch.politapp.widget.ClipRevealFrame;
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemAboutMe;
    private ResideMenuItem itemHowTo;
    private ResideMenuItem itemGalerie;
    private ResideMenuItem itemNews;
    private ResideMenuItem itemQuizz;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemContact;

    View rootLayout;
    ClipRevealFrame menuLayout;
    ArcLayout arcLayout;
    View centerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        setUpMenu();
        setUpShare();

    }

    private void setUpShare() {

        rootLayout = (View) findViewById(R.id.root);
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
        itemAboutMe = new ResideMenuItem(this, R.drawable.icon_uber_mich,     "Blerim Bunjaku");
        itemHowTo = new ResideMenuItem(this, R.drawable.icon_wie_wahlen,  "Wie wählen");
        itemGalerie = new ResideMenuItem(this, R.drawable.icon_galerie, "Galerie");
        itemNews = new ResideMenuItem(this, R.drawable.icon_news, "News");
        itemQuizz = new ResideMenuItem(this, R.drawable.icon_quizz, "Schweizermacher");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_kalendar, "Kalender");
        itemContact = new ResideMenuItem(this, R.drawable.icon_kontakt, "Kontact");

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
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

//        if (view == itemAboutMe){
//            changeFragment(new HomeFragment());
//        }else if (view == itemHowTo){
//            changeFragment(new ProfileFragment());
//        }else if (view == itemGalerie){
//            changeFragment(new CalendarFragment());
//        }else if (view == itemNews){
//            changeFragment(new SettingsFragment());
//        }

        switch (view.getId()) {
            case R.id.title_bar_right_share:
                onShareButtonClick(view);
                return;
        }

        if (view instanceof Button) {
            String text = "Clicked: " + ((Button) view).getText();
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }

        resideMenu.closeMenu();
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
                menuLayout.setVisibility(View.INVISIBLE);
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

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
