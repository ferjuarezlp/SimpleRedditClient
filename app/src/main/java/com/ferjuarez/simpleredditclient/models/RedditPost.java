package com.ferjuarez.simpleredditclient.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ferjuarez on 3/15/17.
 */
@SuppressWarnings("unused")
public class RedditPost implements Parcelable {

    @SerializedName("id")
    private String id;

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

    private boolean isReaded;
    private static final String DEFAULT_THUMBNAIL = "default";

    public RedditPost() {
        // Empty constructor for gson
    }

    private RedditPost(Parcel in) {
        id = in.readString();
        author = in.readString();
        title = in.readString();
        created = in.readLong();
        createdUtc = in.readLong();
        numComments = in.readInt();
        url = in.readString();
        subreddit = in.readString();
        thumbnail = in.readString();
        preview = in.readParcelable(RedditPreview.class.getClassLoader());
        isReaded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeLong(created);
        dest.writeLong(createdUtc);
        dest.writeInt(numComments);
        dest.writeString(url);
        dest.writeString(subreddit);
        dest.writeString(thumbnail);
        dest.writeParcelable(preview, flags);
        dest.writeByte((byte) (isReaded ? 1 : 0));
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        if(thumbnail.equals(DEFAULT_THUMBNAIL)){
            if(preview!= null && !preview.getImages().isEmpty() && preview.getImages().get(0).getSource() != null)
                thumbnail = preview.getImages().get(0).getSource().getUrl();
        }
        return thumbnail;
    }

    public RedditPreview getPreview() {
        return preview;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

}
