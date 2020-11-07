package com.tigo.SubActivityFragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.util.Util;
import com.tigo.Adapters.RegularPostAdapter;
import com.tigo.Classes.RegularPost;
import com.tigo.Classes.RegularPostDivider;
import com.tigo.R;

import java.util.ArrayList;
import java.util.List;

public class HomeMainPostFragment extends Fragment {

    // View references
    private RecyclerView mRecyclerView;

    // Home recyclerview posts arraylist and adapter
    private List<RegularPost> mPostsList = new ArrayList<>();;
    private RegularPostAdapter mRegularPostAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.SmoothScroller smoothScroller;

    // Variables
    private boolean mFragPaused = false;

    public HomeMainPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home_main_post, container, false);

        // Connecting view to code
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        // Setting up the recyclerview with adapter and layout manager
        mRegularPostAdapter = new RegularPostAdapter(mPostsList, getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RegularPostDivider(getContext()));
        mRecyclerView.setAdapter(mRegularPostAdapter);
        // Adding pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        // THIS FUCKER makes the exoplayers' adapter autoplay the player that is most visible (in percentage)
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mRecyclerView != null){
                    LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
                    mRegularPostAdapter.checkPercentageAndStartStopPlayer(getPercentage(layoutManager));

                }
            }
        });

        // TODO remove the test posts
        addTestPosts();

        smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        return rootview;


    }

    private void addTestPosts(){

        mPostsList.clear();
        mRegularPostAdapter.notifyDataSetChanged();

        RegularPost post = new RegularPost(0,
                getContext().getString(R.string.the_one_mv),
                0, "@aneek_rahman",
                "https://pbs.twimg.com/profile_images/953631791256137730/Mq-GwMYZ.jpg",
                "Hello, im a description" , "10:56PM", 6577568,325523626L, 315823, 76456);
        mPostsList.add(post);

        post = new RegularPost(0,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                0, "@someone_else",
                "https://pbs.twimg.com/profile_images/953631791256137730/Mq-GwMYZ.jpg",
                "Hello, im a description" , "2:42PM",7687536425642L,75658, 4123, 76);
        mPostsList.add(post);

        post = new RegularPost(0,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                0, "@ligma_balls",
                "https://pbs.twimg.com/profile_images/953631791256137730/Mq-GwMYZ.jpg",
                "Hello, im a description" , "6:24PM",78126381,56, 5463, 546747);
        mPostsList.add(post);

        post = new RegularPost(0,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                0, "@kochi_dab",
                "https://pbs.twimg.com/profile_images/953631791256137730/Mq-GwMYZ.jpg",
                "Hello, im a description" , "7:09PM",21217,435435, 6457235, 67874563);
        mPostsList.add(post);

        post = new RegularPost(0,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                0, "@vallage_naa",
                "https://pbs.twimg.com/profile_images/953631791256137730/Mq-GwMYZ.jpg",
                "Hello, im a description" ,"1:00PM", 381,6454, 6457, 856746);
        mPostsList.add(post);

        mRegularPostAdapter.notifyDataSetChanged();

    }

    // Get the percentage of visibility of a home post
    private ArrayList<Integer> getPercentage(LinearLayoutManager layoutManager){

        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        try {

            final int firstPosition = layoutManager.findFirstVisibleItemPosition();
            final int lastPosition = layoutManager.findLastVisibleItemPosition();

            Rect rvRect = new Rect();
            mRecyclerView.getGlobalVisibleRect(rvRect);

            for (int i = firstPosition; i <= lastPosition; i++) {
                Rect rowRect = new Rect();
                layoutManager.findViewByPosition(i).getGlobalVisibleRect(rowRect);

                int percentFirst;
                if (rowRect.bottom >= rvRect.bottom){
                    int visibleHeightFirst =rvRect.bottom - rowRect.top;
                    percentFirst = (visibleHeightFirst * 100) / layoutManager.findViewByPosition(i).getHeight();
                }else {
                    int visibleHeightFirst = rowRect.bottom - rvRect.top;
                    percentFirst = (visibleHeightFirst * 100) / layoutManager.findViewByPosition(i).getHeight();
                }

                if (percentFirst>100)
                    percentFirst = 100;


                arrayList.add(percentFirst);
            }

        }finally {

            return arrayList;

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if(mFragPaused)
                mRegularPostAdapter.initExoPlayerFromFragment();
            mFragPaused = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23)) {
            if(mFragPaused)
                mRegularPostAdapter.initExoPlayerFromFragment();
            mFragPaused = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            mRegularPostAdapter.releaseExoPlayerFromFragment();
            mFragPaused = true;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            mRegularPostAdapter.releaseExoPlayerFromFragment();
            mFragPaused = true;
        }
    }

}
