package com.ferjuarez.simpleredditclient.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/15/17.
 */
@SuppressWarnings("unused")
public class RedditPost implements Parcelable {

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("created")
    private long created;

    @SerializedName("created_utc")
    private long createdUtc;

    @SerializedName("num_comments")
    private int numComments;

    @SerializedName("url")
    private String url;

    @SerializedName("subreddit")
    private String subreddit;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("preview")
    private RedditPreview preview;

    public RedditPost() {
        // Empty constructor for gson
    }

    protected RedditPost(Parcel in) {
        author = in.readString();
        title = in.readString();
        created = in.readLong();
        createdUtc = in.readLong();
        numComments = in.readInt();
        url = in.readString();
        subreddit = in.readString();
        thumbnail = in.readString();
        preview = in.readParcelable(RedditPreview.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeLong(created);
        dest.writeLong(createdUtc);
        dest.writeInt(numComments);
        dest.writeString(url);
        dest.writeString(subreddit);
        dest.writeString(thumbnail);
        dest.writeParcelable(preview, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RedditPost> CREATOR = new Creator<RedditPost>() {
        @Override
        public RedditPost createFromParcel(Parcel in) {
            return new RedditPost(in);
        }

        @Override
        public RedditPost[] newArray(int size) {
            return new RedditPost[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public long getCreated() {
        return created;
    }

    public long getCreatedUtc() {
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

    public RedditPreview getPreview() {
        return preview;
    }
}
