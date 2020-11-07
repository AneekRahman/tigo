package com.tigo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

    private RelativeLayout mExoMainHolder;
    private ExpandableHeightGridView mGridView;
    private RelativeLayout mSearchHolder;

    // Profile grid posts list and adapter
    private ExploreGridPostsAdapter mAdapter;
    private List<ExploreGridPost> mPosts = new ArrayList<>();

    // Exo player references
    private ExoPlayer mExoPlayer;
    private PlayerView mExoPlayerView;


    public ExploreFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_explore, container, false);

        mExoMainHolder = (RelativeLayout) rootview.findViewById(R.id.player_main_holder);
        mGridView = (ExpandableHeightGridView) rootview.findViewById(R.id.grid_view);
        mSearchHolder = (RelativeLayout) rootview.findViewById(R.id.search_holder);

        mSearchHolder.setPadding(0, MyUtils.getStatusBarHeight(getContext()), 0, 0);

        setExoHolderHeight();

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

        ExploreGridPost post = new ExploreGridPost("aneek_rahman", 0, 312421, "https://media.giphy.com/media/l0HFjd18a3kVPQxPO/giphy.gif");
        mPosts.add(post);

        for (int i = 0; i < 20; i++){

            post = new ExploreGridPost("the_main_boi", 0, 312421, "https://media.giphy.com/media/l0HFjd18a3kVPQxPO/giphy.gif");
            mPosts.add(post);

        }

        mAdapter.notifyDataSetChanged();

    }

    // Initializing the posts exoplayer
    private void initializeExoPlayer(){

        if(mExoPlayer == null){

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);

            //prepareExoPlayer();
        }

    }

    // Preparing the exoplayer with the video address
    private void prepareExoPlayer(String vidAddress){

        Uri uri = Uri.parse(vidAddress);
        MediaSource mMediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mMediaSource, false, false);

    }

    // Building mediasource for exoplayer
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("ripple-exo")).createMediaSource(uri);
    }


    // Setting a fixed height for the root view
    private void setExoHolderHeight(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);

        int adjustedHeight = height - pixels ;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, adjustedHeight);
        mExoMainHolder.setLayoutParams(params);

    }


}
