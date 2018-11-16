package com.spearheadinc.flashcards.omer.retrofit;

import android.content.Context;

public abstract class CallbackContext <T> implements ContextRequestCallback<T> {
    private Context context;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
