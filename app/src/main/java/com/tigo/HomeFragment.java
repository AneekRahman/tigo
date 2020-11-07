package com.tigo;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.util.Util;
import com.tigo.Adapters.RegularPostAdapter;
import com.tigo.Classes.DepthPageTransformer;
import com.tigo.Classes.MyUtils;
import com.tigo.Classes.RegularPost;
import com.tigo.Classes.RegularPostDivider;
import com.tigo.SubActivityFragments.HomeMainPostFragment;
import com.tigo.SubActivityFragments.HomeSmallPostFragment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private ViewPager mViewPager;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        // Connecting view with code
        mViewPager = (ViewPager) rootview.findViewById(R.id.main_viewpager);

        setupViewPager(mViewPager);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setCurrentItem(1);

        return rootview;


    }

    // The viewpager class constructor
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

    // Viewpager setup method
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFrag(new HomeSmallPostFragment());
        viewPagerAdapter.addFrag(new HomeMainPostFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }


}
