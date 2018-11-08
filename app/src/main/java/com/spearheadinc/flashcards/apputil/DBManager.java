package com.spearheadinc.flashcards.apputil;

import android.content.Context;

import com.spearheadinc.flashcards.omer.DatabaseProvider;
import com.spearheadinc.flashcards.omer.FCDBHelper;

public class DBManager {

    private static DBManager sDBMANAGER;
    private FCDBHelper mFCDbHelper;

    private DBManager(Context context) {
        DatabaseProvider dbProvider = new DatabaseProvider();
		dbProvider.createDB(context);
		mFCDbHelper = dbProvider.getMyFCDbHelper();
    }

    public static DBManager getInstance(Context context) {
        if(sDBMANAGER == null) {
            sDBMANAGER = new DBManager(context);
        }

        return sDBMANAGER;
    }

    public FCDBHelper getMyFCDbHelper() {
		return mFCDbHelper;
	}
}
