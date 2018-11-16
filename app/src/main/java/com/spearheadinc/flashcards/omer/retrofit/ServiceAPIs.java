package com.spearheadinc.flashcards.omer.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ServiceAPIs {
    @GET
    Observable<CardsResponse> getCards(@Url String url);
}
