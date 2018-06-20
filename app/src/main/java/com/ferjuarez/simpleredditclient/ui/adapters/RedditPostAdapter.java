package com.ferjuarez.simpleredditclient.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class RedditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private List<RedditElement> mRedditElements;
    private RedditAdapterListener mRedditListener;

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    public RedditPostAdapter(List<RedditElement> redditElements, RedditAdapterListener mRedditListener) {
        this.mRedditElements = redditElements;
        this.mRedditListener = mRedditListener;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate( R.layout.reddit_post_item, parent, false));
            viewHolder.setIsRecyclable(true);
            mContext = parent.getContext();
            return viewHolder;
        } else if(viewType == TYPE_FOOTER){
            return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate( R.layout.reddit_list_footer, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindTo(mRedditElements.get(position).getData());
        } else if(holder instanceof FooterViewHolder){
            ((FooterViewHolder) holder).setOnClickListener(view -> {
                clear();
                mRedditListener.onDismissAll();
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindTo(RedditPost redditPost) {
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

            itemView.setOnClickListener(view -> {
                mRedditListener.onItemSelection(redditPost);
                redditPost.setReaded(true);
                notifyItemChanged(getAdapterPosition());
            });

            thumbnail.setOnClickListener(view -> {
                if(redditPost.getPreview() != null){
                    String url = redditPost.getThumbnail();
                    if(redditPost.getPreview().getImages().size() > 0){
                        url = redditPost.getPreview().getImages().get(0).getSource().getUrl().replace("&amp;","&");
                    }
                    mRedditListener.onThumbnailSelection(url);
                }
            });
        }

        private void setImage(String url) {
            Glide.with(thumbnail.getContext())
                    .load(url)
                    .crossFade()
                    .transform(new CircleTransform(thumbnail.getContext()))
                    .into(thumbnail);
        }
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buttonFooter)
        Button buttonFooter;

        FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setOnClickListener(View.OnClickListener listener){
            buttonFooter.setOnClickListener(listener);
        }
    }
}
