package com.ferjuarez.simpleredditclient.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/16/17.
 */

public class RedditElement implements Parcelable {

    @SerializedName("kind")
    private String kind;

    @SerializedName("data")
    private RedditPost post;

    public RedditElement() {
        // Empty constructor for gson
    }

    protected RedditElement(Parcel in) {
        kind = in.readString();
        post = in.readParcelable(RedditPost.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeParcelable(post, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedditElement> CREATOR = new Creator<RedditElement>() {
        @Override
        public RedditElement createFromParcel(Parcel in) {
            return new RedditElement(in);
        }

        @Override
        public RedditElement[] newArray(int size) {
            return new RedditElement[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public RedditPost getData() {
        return post;
    }
}
