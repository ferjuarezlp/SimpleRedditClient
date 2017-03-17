package com.ferjuarez.simpleredditclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/16/17.
 */

public class RedditElement {

    @SerializedName("kind")
    private String kind;

    @SerializedName("data")
    private RedditPost post;

    public RedditElement() {
        // Empty constructor for gson
    }

    public String getKind() {
        return kind;
    }

    public RedditPost getData() {
        return post;
    }
}
