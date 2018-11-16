package com.spearheadinc.flashcards.omer.retrofit;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ApiDataManager {

    public static final String TAG = ApiDataManager.class.getCanonicalName();
    public static final Disposable getMyThings(final CallbackContext callbackContext, String url){
        url = "https://www.hebcal.com/hebcal/?v=1&cfg=json&maj=off&min=off&mod=off&nx=off&year=2019&month=x&ss=off&mf=off&c=off&geo=none&m=0&s=off&o=on";

        return ServiceFactory.getServiceAPIs().getCards(url)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<CardsResponse, ArrayList<ItemsBean>>() {
                    @Override
                    public ArrayList<ItemsBean> apply(CardsResponse cardsResponse) throws Exception {
                        ArrayList<ItemsBean> beans =  cardsResponse.getItems();
                        return beans;
                    }
                }).observeOn(Schedulers.newThread())
                .subscribe(new Consumer<ArrayList<ItemsBean>>() {
                    @Override
                    public void accept(ArrayList<ItemsBean> o) throws Exception {
                        if (callbackContext != null) {
                            callbackContext.onNext(o);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (callbackContext!=null){
                            callbackContext.onError(throwable,"");
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (callbackContext!= null){
                            callbackContext.onComplete("");
                        }
                    }
                });


    }
}
