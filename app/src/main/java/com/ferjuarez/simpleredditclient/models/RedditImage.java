package com.ferjuarez.simpleredditclient.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/17/17.
 */

public class RedditImage implements Parcelable {

    @SerializedName("source")
    private RedditPreviewSource source;

    @SerializedName("id")
    private String id;

    public RedditImage() {
        // Empty constructor for gson
    }

    protected RedditImage(Parcel in) {
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedditImage> CREATOR = new Creator<RedditImage>() {
        @Override
        public RedditImage createFromParcel(Parcel in) {
            return new RedditImage(in);
        }

        @Override
        public RedditImage[] newArray(int size) {
            return new RedditImage[size];
        }
    };

    public RedditPreviewSource getSource() {
        return source;
    }

    public String getId() {
        return id;
    }
}
