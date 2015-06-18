package enyx.ch.politapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import enyx.ch.politapp.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        setUpMenu();

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

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

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

        resideMenu.closeMenu();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
