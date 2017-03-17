package com.ferjuarez.simpleredditclient.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ferjuarez on 3/15/17.
 */
@SuppressWarnings("unused")
public class RedditData {

    @SerializedName("children")
    private List<RedditElement> childrens;

    @SerializedName("before")
    private String before;

    @SerializedName("after")
    private String after;

    public RedditData() {
        // Empty constructor for gson
    }

    public List<RedditElement> getChildren() {
        return childrens;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }
}
