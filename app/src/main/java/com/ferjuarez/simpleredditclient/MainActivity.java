package com.ferjuarez.simpleredditclient;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.ferjuarez.simpleredditclient.models.RedditElement;
import com.ferjuarez.simpleredditclient.networking.RedditService;
import com.ferjuarez.simpleredditclient.networking.RetrofitManager;
import com.ferjuarez.simpleredditclient.utils.ui.RedditPostAdapter;
import com.ferjuarez.simpleredditclient.views.BaseCompatActivity;
import com.ferjuarez.simpleredditclient.views.BaseView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseCompatActivity implements BaseView {
    public static final int PAGE_SIZE = 10;

    private Subscription mRedditSubscription;
    private RedditService mRedditService;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private LinearLayoutManager mLayoutManager;
    private boolean isLastPage = false;
    private String mNextPage;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = getActivityToolbar();
        if (toolbar != null) {
            initToolBar(toolbar, getActivityTitle());
        }

        bindViews();
        initialize(savedInstanceState);
    }


    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
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
        mRedditService = RetrofitManager.getInstance().getRedditService();
        mRedditSubscription = mRedditService.getPaginatedTop(PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tops -> {
                            mNextPage = tops.getData().getAfter();
                            List<RedditElement> redditElements = tops.getData().getChildren();
                            if (redditElements.isEmpty()) {
                                // TODO show empty
                            } else {
                                mRecyclerView.setAdapter(new RedditPostAdapter(redditElements));
                            }
                        },
                        error -> {
                            Log.e("","");
                        }
                );
    }

    @Override
    public void onConnectionError(Throwable error) {

    }

    private void loadMoreItems() {
        mProgressBar.setVisibility(View.VISIBLE);
        showWaitDialog(this, "Cargando");
        isLoading = true;

        mRedditSubscription = mRedditService.getPaginatedTop(PAGE_SIZE, mNextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tops -> {
                            List<RedditElement> redditElements = tops.getData().getChildren();
                            mNextPage = tops.getData().getAfter();
                            if (redditElements.isEmpty()) {
                                // TODO show empty
                            } else {
                                ((RedditPostAdapter)mRecyclerView.getAdapter()).addElements(redditElements);
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                            isLoading = false;
                            mProgressBar.setVisibility(View.INVISIBLE);
                        },
                        error -> {
                            Log.e("","");
                        }
                );
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
                        && totalItemCount >= 10) {
                    loadMoreItems();
                }
            }
        }
    };
}
