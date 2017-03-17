package com.ferjuarez.simpleredditclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/16/17.
 */

public class RedditRootElement {

    @SerializedName("kind")
    private String kind;

    @SerializedName("data")
    private RedditData data;

    public RedditRootElement() {
        // Empty constructor for gson
    }

    public String getKind() {
        return kind;
    }

    public RedditData getData() {
        return data;
    }
}
