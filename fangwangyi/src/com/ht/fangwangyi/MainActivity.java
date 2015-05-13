package com.ht.fangwangyi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;

import java.lang.reflect.Method;

import static com.ht.fangwangyi.R.menu.main_menu;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getActionBar();
        Resources r = getResources();
        Drawable myDrawable = r.getDrawable(R.drawable.pink);
        actionBar.setBackgroundDrawable(myDrawable);
        //actionBar.setHomeAsUpIndicator(R.drawable.acicon);
        ChangeActionBarHomeUpDrawable(this, R.drawable.acicon);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                ChangeActionBarHomeUpDrawable(this, R.drawable.fanhui);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //改变返回按钮的图像
    public static void ChangeActionBarHomeUpDrawable(Activity activity, int rid) {
        Drawable homeUp = activity.getResources().getDrawable(rid);
        final View home = activity.findViewById(android.R.id.home);
        if (home == null) {
            // Action bar doesn't have a known configuration, an OEM messed with
            // things.
            return;
        }

        final ViewGroup parent = (ViewGroup) home.getParent();
        final int childCount = parent.getChildCount();
        if (childCount != 2) {
            // No idea which one will be the right one, an OEM messed with
            // things.
            return;
        }

        final View first = parent.getChildAt(0);
        final View second = parent.getChildAt(1);
        final View up = first.getId() == android.R.id.home ? second : first;

        if (up instanceof ImageView) {
            // Jackpot! (Probably...)
            ImageView upIndicatorView = (ImageView) up;
            upIndicatorView.setImageDrawable(homeUp);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //三点的图标
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
