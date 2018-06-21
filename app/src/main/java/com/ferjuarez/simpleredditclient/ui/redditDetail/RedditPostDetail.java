package com.ferjuarez.simpleredditclient.ui.redditDetail;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.ferjuarez.simpleredditclient.R;
import com.ferjuarez.simpleredditclient.models.RedditElement;
import com.ferjuarez.simpleredditclient.models.RedditPost;
import com.ferjuarez.simpleredditclient.ui.CircleTransform;
import com.ferjuarez.simpleredditclient.ui.adapters.RedditPostAdapter;
import com.ferjuarez.simpleredditclient.ui.base.BaseCompatActivity;
import com.ferjuarez.simpleredditclient.ui.redditFeed.RedditFeedPresenter;
import com.ferjuarez.simpleredditclient.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedditPostDetail extends BaseCompatActivity {

    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textViewCommentsCount)
    TextView textViewCommentsCount;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.textSubreddit)
    TextView textSubreddit;

    private String POST_KEY = "post_key";
    private Parcelable mViewState;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initToolBar();
        enableBackInToolbar();

        bindViews();
        initialize(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mViewState != null) {
            mLayoutManager.onRestoreInstanceState(mViewState);
        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        mViewState = mLayoutManager.onSaveInstanceState();
        if(getIntent().getExtras() != null){
            RedditPost post = getRedditPostFromExtras();
            if(post != null) state.putParcelable(POST_KEY, post);
        }
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if(state != null){
            RedditPost post = state.getParcelable(POST_KEY);
            populatePost(post);
        }
    }

    private RedditPost getRedditPostFromExtras(){
        return getIntent().getExtras().getParcelable(POST_KEY);
    }

    private void populatePost(RedditPost post){
        setToolbarTitle(post.getTitle());
        title.setText(post.getTitle());
        String time = Util.getTimeAgo(post.getCreatedUtc(), this);
        author.setText(getString(R.string.label_author_time, post.getAuthor(), time));
        textViewCommentsCount.setText(getString(R.string.label_comments, post.getNumComments()));
        textSubreddit.setText(getString(R.string.label_posted) + post.getSubreddit());
        String url = post.getThumbnail();
        setImage(url);
        thumbnail.setVisibility(View.VISIBLE);
    }

    private void setImage(String url) {
        Glide.with(thumbnail.getContext())
                .load(url)
                .fitCenter()
                .crossFade()
                .placeholder(R.drawable.placeholder)
                .into(thumbnail);
    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected Toolbar getActivityToolbar() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.title_home);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(this);

        if(savedInstanceState == null){
            RedditPost post = getRedditPostFromExtras();
            if(post != null) populatePost(post);
        }
    }
}
