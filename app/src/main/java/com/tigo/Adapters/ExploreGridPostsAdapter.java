package com.tigo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tigo.Classes.ExploreGridPost;
import com.tigo.Classes.MyUtils;
import com.tigo.R;

import java.util.List;

public class ExploreGridPostsAdapter extends BaseAdapter{

    // Given context, posts
    private Context mContext;
    private List<ExploreGridPost> mPosts;

    // Class instance constructor
    public ExploreGridPostsAdapter(Context c, List<ExploreGridPost> posts) {
        mContext = c;
        this.mPosts = posts;
    }

    // Needed method
    @Override
    public int getCount() {
        return mPosts.size();
    }
    // Needed method
    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }
    // Needed method
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflating the profile grid layout for each of the girds
        convertView = LayoutInflater.from(mContext).inflate(R.layout.explore_grid_post_layout, null);

        ExploreGridPost post = mPosts.get(position);

        // Connecting the grid layouts view to code
        RelativeLayout mainHolder = (RelativeLayout) convertView.findViewById(R.id.main_holder);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.username);
        TextView viewsCount = (TextView) convertView.findViewById(R.id.views_count);

        // Setting a fixed size to the gird
        setGridSize(mainHolder);

        // Showing the user post gif
        Glide.with(mContext)
                .load(post.getPostContentUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
        userNameTextView.setText("@" + post.getUserName());
        viewsCount.setText(String.valueOf(MyUtils.formatNumbers(post.getViewsCount())));

        return convertView;

    }

    // The method for setting a fixed size to the gird
    private void setGridSize(RelativeLayout v){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixelsFrom8Dp = (int) (30 * scale + 0.5f);

        float adjustedWidth = ( displayWidth - pixelsFrom8Dp ) / 2;
        int adjustedHeight = (int) (adjustedWidth * 1.5f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) adjustedWidth, adjustedHeight);
        v.setLayoutParams(params);

    }


}