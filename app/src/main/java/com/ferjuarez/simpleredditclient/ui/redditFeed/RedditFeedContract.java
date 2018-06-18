package com.ferjuarez.simpleredditclient.ui.redditFeed;

import com.ferjuarez.simpleredditclient.ui.base.ContractPresenter;
import com.ferjuarez.simpleredditclient.ui.base.ContractView;

public class RedditFeedContract {

    interface View extends ContractView {
        void showDetail();
    }

    interface Presenter extends ContractPresenter {
        void getTops();
        void getPaginatedTops(String nextPage);
        int getPageSize();
    }
}
