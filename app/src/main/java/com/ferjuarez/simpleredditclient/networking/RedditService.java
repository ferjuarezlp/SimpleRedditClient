package com.ferjuarez.simpleredditclient.networking;

import com.ferjuarez.simpleredditclient.models.RedditRootElement;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ferjuarez on 3/15/17.
 */
public interface RedditService {

    @GET("/top.json")
    Observable<RedditRootElement> getTop();

    @GET("/top.json")
    Observable<RedditRootElement> getPaginatedTop(@Query("limit") int limit, @Query("after") String after);

    @GET("/top.json")
    Observable<RedditRootElement> getPaginatedTop(@Query("limit") int limit);

}
