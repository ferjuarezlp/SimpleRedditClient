package com.ferjuarez.simpleredditclient.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by ferjuarez on 3/17/17.
 */

public class RedditPreview implements Parcelable{

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("images")
    private List<RedditImage> images;

    public RedditPreview() {
        // Empty constructor for gson
    }

    protected RedditPreview(Parcel in) {
        enabled = in.readByte() != 0;
        images = in.createTypedArrayList(RedditImage.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeTypedList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedditPreview> CREATOR = new Creator<RedditPreview>() {
        @Override
        public RedditPreview createFromParcel(Parcel in) {
            return new RedditPreview(in);
        }

        @Override
        public RedditPreview[] newArray(int size) {
            return new RedditPreview[size];
        }
    };

    public boolean isEnabled() {
        return enabled;
    }

    public List<RedditImage> getImages() {
        return images;
    }
}
