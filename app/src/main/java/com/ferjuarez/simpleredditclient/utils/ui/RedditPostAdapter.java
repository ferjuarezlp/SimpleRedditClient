package com.ferjuarez.simpleredditclient.utils.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ferjuarez.simpleredditclient.R;
import com.ferjuarez.simpleredditclient.models.RedditElement;
import com.ferjuarez.simpleredditclient.models.RedditPost;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Juan on 30/8/16.
 */
public class RedditPostAdapter extends RecyclerView.Adapter<RedditPostAdapter.ViewHolder> {

    private List<RedditElement> mRedditElements;

    public RedditPostAdapter(List<RedditElement> redditElements) {
        this.mRedditElements = redditElements;
    }

    public void addElements(List<RedditElement> redditElements) {
        this.mRedditElements.addAll(redditElements);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reddit_post_item, parent, false));
        viewHolder.setIsRecyclable(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo(mRedditElements.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return mRedditElements.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.textViewCommentsCount)
        TextView textViewCommentsCount;
        @BindView(R.id.author)
        TextView author;

        private RedditPost redditPost;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressWarnings("unused")
        @OnClick(R.id.card)
        public void onCardClick(View view) {

        }

        public void bindTo(RedditPost redditPost) {
            this.redditPost = redditPost;
            setImage(redditPost.getThumbnail());
            title.setText(redditPost.getTitle());
            author.setText(redditPost.getAuthor());
            textViewCommentsCount.setText(redditPost.getNumComments() + " Comments");
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("","");
                }
            });
        }

        private void setImage(String url) {
            Glide.with(thumbnail.getContext())
                    .load(url)
                    .placeholder(android.R.color.black)
                    //.centerCrop()
                    .crossFade()
                    .into(thumbnail);
        }
    }
}
