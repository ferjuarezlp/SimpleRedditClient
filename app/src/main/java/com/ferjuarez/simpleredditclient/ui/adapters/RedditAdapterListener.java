package com.ferjuarez.simpleredditclient.ui.adapters;

import com.ferjuarez.simpleredditclient.models.RedditPost;

public interface RedditAdapterListener {
    void onDismissAll();
    void onThumbnailSelection(String url);
    void onItemSelection(RedditPost redditPost);
}