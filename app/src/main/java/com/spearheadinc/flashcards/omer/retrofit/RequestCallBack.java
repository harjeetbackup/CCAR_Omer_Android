package com.spearheadinc.flashcards.omer.retrofit;

interface RequestCallBack<T> {
    void onNext(T t);
    void onError(Throwable t, String str);
    void onComplete(String str);
}
