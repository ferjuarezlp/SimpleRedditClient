package com.ferjuarez.simpleredditclient.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ferjuarez.simpleredditclient.R;
import com.ferjuarez.simpleredditclient.models.RedditElement;
import com.ferjuarez.simpleredditclient.models.RedditPost;
import com.ferjuarez.simpleredditclient.ui.CircleTransform;
import com.ferjuarez.simpleredditclient.utils.Util;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Juan on 30/8/16.
 */
public class RedditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private List<RedditElement> mRedditElements;
    private View.OnClickListener mOnClickListener;

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    public RedditPostAdapter(List<RedditElement> redditElements, View.OnClickListener onClickListener) {
        this.mRedditElements = redditElements;
        this.mOnClickListener = onClickListener;
    }

    public void addElements(List<RedditElement> redditElements) {
        this.mRedditElements.addAll(redditElements);
    }

    public List<RedditElement> getItems(){
        return this.mRedditElements;
    }

    public void clear(){
        mRedditElements.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate( R.layout.reddit_post_item, parent, false));
            viewHolder.setIsRecyclable(true);
            mContext = parent.getContext();
            return viewHolder;
        } else if(viewType == TYPE_FOOTER){
            FooterViewHolder footerHolder = new FooterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate( R.layout.reddit_list_footer, parent, false));
            return footerHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindTo(mRedditElements.get(position).getData());
        } else if(holder instanceof FooterViewHolder){
            ((FooterViewHolder) holder).setOnClickListener(view -> {
                clear();
                mOnClickListener.onClick(view);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRedditElements.size() + 1;
    }

    @Override
    public void onItemDismiss(int position) {
        mRedditElements.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mRedditElements.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
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
        @BindView(R.id.textSubreddit)
        TextView textSubreddit;
        @BindView(R.id.imageViewReaded)
        ImageView imageViewReaded;

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
            String time = Util.getTimeAgo(redditPost.getCreatedUtc(), mContext);
            author.setText(mContext.getString(R.string.label_author_time, redditPost.getAuthor(), time));
            textViewCommentsCount.setText(mContext.getString(R.string.label_comments, redditPost.getNumComments()));
            textSubreddit.setText(mContext.getString(R.string.label_posted) + redditPost.getSubreddit());
            if(redditPost.isReaded()){
                imageViewReaded.setImageResource(R.drawable.eye);
            } else {
                imageViewReaded.setImageResource(R.drawable.eye_gray);
            }
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

    protected class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buttonFooter)
        Button buttonFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnClickListener(View.OnClickListener listener){
            buttonFooter.setOnClickListener(listener);
        }
    }
}