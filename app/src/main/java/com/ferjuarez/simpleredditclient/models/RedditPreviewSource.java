package com.ferjuarez.simpleredditclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/17/17.
 */

public class RedditPreviewSource {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    public RedditPreviewSource() {
        // Empty constructor for gson
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
