package com.tigo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.tigo.Adapters.ExploreGridPostsAdapter;
import com.tigo.Classes.ExpandableHeightGridView;
import com.tigo.Classes.ExploreGridPost;
import com.tigo.Classes.MyUtils;

import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment {

    private RelativeLayout mSearchHolder;
    private TextView mSearchBar;
    private LinearLayout mGridHolder;
    private ExpandableHeightGridView mGridView;

    // Profile grid posts list and adapter
    private ExploreGridPostsAdapter mAdapter;
    private List<ExploreGridPost> mPosts = new ArrayList<>();


    public ExploreFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_explore, container, false);

        mSearchHolder = (RelativeLayout) rootview.findViewById(R.id.explore_search_holder);
        mSearchBar = (TextView) rootview.findViewById(R.id.search_bar);
        mGridHolder = (LinearLayout) rootview.findViewById(R.id.grid_holder);
        mGridView = (ExpandableHeightGridView) rootview.findViewById(R.id.grid_view);

        mSearchHolder.setPadding(0, MyUtils.getStatusBarHeight(getContext()), 0, 0);
        mGridHolder.setPadding(0, MyUtils.getStatusBarHeight(getContext()), 0, 0);

        // Setting up the Gridview and its adapter
        mAdapter = new ExploreGridPostsAdapter(getContext(), mPosts);
        mGridView.setAdapter(mAdapter);
        mGridView.setExpanded(true);
        mGridView.setFocusable(false);



        // TODO remove test posts
        insertPostsToAdapter();

        return rootview;

    }

    private void insertPostsToAdapter(){

        ExploreGridPost post = new ExploreGridPost("aneek_rahman", 0, 7957357366L, "https://media.giphy.com/media/9rns5t08XvXn8rYp35/giphy.gif");
        mPosts.add(post);

        for (int i = 0; i < 20; i++){

            post = new ExploreGridPost("the_main_boi", 0, 23523, "https://media.giphy.com/media/9rns5t08XvXn8rYp35/giphy.gif");
            mPosts.add(post);

        }

        mAdapter.notifyDataSetChanged();

    }

}
