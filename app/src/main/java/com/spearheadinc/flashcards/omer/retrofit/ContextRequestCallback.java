package com.spearheadinc.flashcards.omer.retrofit;

import android.content.Context;

interface ContextRequestCallback<T> extends RequestCallBack<T>{
    void setContext(Context context);
    Context getContext();
}
