package com.tigo;

import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tigo.Adapters.RegularPostAdapter;
import com.tigo.Classes.MyUtils;
import com.tigo.Classes.RegularPost;
import com.tigo.Classes.RegularPostDivider;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // View references
    private RelativeLayout mMainHolder;
    private ImageView mHomeIcon, mEarthIcon, mMsgIcon, mProfileIcon;

    // Fragment stuff
    private FragmentManager mFragManager = getSupportFragmentManager();
    private HomeFragment mHomeFragment = new HomeFragment();
    private ExploreFragment mExploreFragment = new ExploreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connecting home post layout views to code
        mMainHolder = (RelativeLayout) findViewById(R.id.main_holder);
        mHomeIcon = (ImageView) findViewById(R.id.home_icon);
        mEarthIcon = (ImageView) findViewById(R.id.earth_icon);
        mMsgIcon = (ImageView) findViewById(R.id.msg_icon);
        mProfileIcon = (ImageView) findViewById(R.id.profile_icon);

        // Navbar space
        MyUtils.transpStatusBar(this);
        mMainHolder.setPadding(0, 0, 0, MyUtils.getNavBarHeight(this));

        if(mFragManager.findFragmentById(R.id.frag_holder) == null){

            FragmentTransaction ft = mFragManager.beginTransaction();
            ft.add(R.id.frag_holder, mHomeFragment, mHomeFragment.getClass().getSimpleName());
            ft.addToBackStack(mHomeFragment.getClass().getSimpleName());
            ft.commit();

        }

        mHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(mHomeFragment);

            }
        });
        mEarthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(mExploreFragment);

            }
        });
        mMsgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Scrolling to 1", Toast.LENGTH_SHORT).show();

            }
        });
        mProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

    private void replaceFragment(Fragment fragment){

        // Clears the home frags video cache bcz it gets messy after popping back
        //FileUtils.deleteQuietly(new File(getCacheDir(), getString(R.string.app_name) + "_exo_cache"));

        if(fragment.getClass().getName() == mFragManager.findFragmentById(R.id.frag_holder).getClass().getName()) return;

        mFragManager.beginTransaction()
                .replace(R.id.frag_holder, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed(){
        if (mFragManager.getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Checks file size and clears the home frags video cache
        File cacheDir = new File(getCacheDir(), getString(R.string.app_name) + "_exo_cache");
        if(FileUtils.sizeOf(cacheDir) > 150 * 1024 * 1024){ // 150 mb
            FileUtils.deleteQuietly(cacheDir);
        }
    }
}
