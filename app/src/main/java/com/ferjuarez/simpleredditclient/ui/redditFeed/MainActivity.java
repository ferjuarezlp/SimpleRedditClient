package com.ferjuarez.simpleredditclient.ui.redditFeed;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ferjuarez.simpleredditclient.R;
import com.ferjuarez.simpleredditclient.models.RedditData;
import com.ferjuarez.simpleredditclient.models.RedditElement;
import com.ferjuarez.simpleredditclient.ui.base.BaseCompatActivity;
import com.ferjuarez.simpleredditclient.ui.RedditPostAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseCompatActivity implements RedditFeedContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textViewEmpty)
    TextView textViewEmpty;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private RedditFeedPresenter mRedditFeedPresenter;
    private LinearLayoutManager mLayoutManager;

    private boolean isLastPage = false;
    private String mNextPage;
    private boolean isLoading = false;

    private Parcelable mViewState;

    private String NEXT_PAGE_KEY = "next_page_key";
    private String IS_LOADING_KEY = "is_loading_key";
    private String IS_LAST_PAGE_KEY = "is_last_page_key";
    private String ITEMS_KEY = "items_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar(getActivityTitle());

        bindViews();
        initialize(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        mViewState = mLayoutManager.onSaveInstanceState();
        state.putString(NEXT_PAGE_KEY, mNextPage);
        state.putBoolean(IS_LOADING_KEY, isLoading);
        state.putBoolean(IS_LAST_PAGE_KEY, isLastPage);
        if(mRecyclerView.getAdapter() != null)
            state.putParcelableArrayList(ITEMS_KEY, new ArrayList<RedditElement>(((RedditPostAdapter)mRecyclerView.getAdapter()).getItems()));
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if(state != null){
            mNextPage = state.getString(NEXT_PAGE_KEY);
            isLoading = state.getBoolean(IS_LOADING_KEY);
            isLastPage = state.getBoolean(IS_LAST_PAGE_KEY);
            List<RedditElement> items = state.getParcelableArrayList(ITEMS_KEY);
            mRecyclerView.setAdapter(new RedditPostAdapter(items));
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mViewState != null) {
            mLayoutManager.onRestoreInstanceState(mViewState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
        mRedditFeedPresenter = RedditFeedPresenter.getInstance();
        mRedditFeedPresenter.attachView(this);
    }

    @Override
    protected Toolbar getActivityToolbar() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.title_home);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        if(savedInstanceState == null){
            progressBar.setVisibility(View.VISIBLE);
            mRedditFeedPresenter.getTops();
        }

    }

    @Override
    public void onSuccess(Object result) {
        if(result != null && result instanceof RedditData){
            RedditData dataResult = (RedditData) result;
            if(dataResult.getAfter() != null){
                mNextPage = dataResult.getAfter();
            } else isLastPage = true;

            List<RedditElement> redditElements = dataResult.getChildren();
            if (redditElements.isEmpty()) {
                textViewEmpty.setVisibility(View.VISIBLE);
            } else {
                textViewEmpty.setVisibility(View.INVISIBLE);
                if(mRecyclerView.getAdapter() == null){
                    progressBar.setVisibility(View.INVISIBLE);
                    mRecyclerView.setAdapter(new RedditPostAdapter(redditElements));
                } else {
                    ((RedditPostAdapter)mRecyclerView.getAdapter()).addElements(redditElements);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        }
        isLoading = false;
        showLoading(false);
        //dismissWaitDialog();
    }

    @Override
    public void showLoading(boolean visibility) {
        if(visibility) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onError(Throwable error) {
        showInfoDialog(error.getMessage());
        showLoading(false);
        textViewEmpty.setVisibility(View.VISIBLE);
    }

    private void loadMoreItems() {
        //showWaitDialog(this, getString(R.string.title_loading_more));
        showLoading(true);
        isLoading = true;
        mRedditFeedPresenter.getPaginatedTops(mNextPage);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= mRedditFeedPresenter.getPageSize()) {
                    loadMoreItems();
                }
            }
        }
    };

    @Override
    public void showDetail() {

    }
}
