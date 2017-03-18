package com.ferjuarez.simpleredditclient.utils.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    private Context mContext;
    private List<RedditElement> mRedditElements;

    public RedditPostAdapter(List<RedditElement> redditElements) {
        this.mRedditElements = redditElements;
    }

    public void addElements(List<RedditElement> redditElements) {
        this.mRedditElements.addAll(redditElements);
    }

    public List<RedditElement> getItems(){
        return this.mRedditElements;
    }

    public void updateItems(List<RedditElement> items){
        this.mRedditElements = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reddit_post_item, parent, false));
        viewHolder.setIsRecyclable(true);
        mContext = parent.getContext();
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
            thumbnail.setOnClickListener(view -> {
                if(redditPost.getPreview() != null){
                    String url = redditPost.getThumbnail();
                    if(redditPost.getPreview().getImages().size() > 0){
                        url = redditPost.getPreview().getImages().get(0).getSource().getUrl().replace("&amp;","&");
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    mContext.startActivity(i);
                }
            });
        }

        private void setImage(String url) {
            Glide.with(thumbnail.getContext())
                    .load(url)
                    //.centerCrop()
                    .crossFade()
                    .transform(new CircleTransform(thumbnail.getContext()))
                    .into(thumbnail);
        }
    }
}
