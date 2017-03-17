package com.ferjuarez.simpleredditclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/15/17.
 */
@SuppressWarnings("unused")
public class RedditPost {

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("created")
    private String created;

    @SerializedName("created_utc")
    private String createdUtc;

    @SerializedName("num_comments")
    private int numComments;

    @SerializedName("url")
    private String url;

    @SerializedName("subreddit")
    private String subreddit;

    @SerializedName("thumbnail")
    private String thumbnail;


    public RedditPost() {
        // Empty constructor for gson
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated() {
        return created;
    }

    public String getCreatedUtc() {
        return createdUtc;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getUrl() {
        return url;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
