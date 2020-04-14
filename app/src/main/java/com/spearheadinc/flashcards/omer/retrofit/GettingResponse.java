package com.spearheadinc.flashcards.omer.retrofit;

import androidx.appcompat.app.AppCompatActivity;

public class GettingResponse extends AppCompatActivity {
//    private CompositeDisposable mDisposable = new CompositeDisposable();
//    private String mLastSearchQuery = "Modi";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        AppPreference.getInstance(GettingResponse.this).getList();
//        searchInWeb();
//    }
//
//    private void searchInWeb() {
//        CallbackContext callbackContext = new CallbackContext<ArrayList<ItemsBean>>() {
//            @Override
//            public void onNext(ArrayList<ItemsBean> articleBeans) {
//                Log.i("", "");
//
//                AppPreference.getInstance(GettingResponse.this).putList(articleBeans);
//                getDateResponseList();
//
//
//
//            }
//
//
//
//            @Override
//            public void onError(Throwable t, String str) {
//                Log.i("", "");
//
//            }
//
//            @Override
//            public void onComplete(String str) {
//                Log.i("", "");
//
//
//
//            }
//        };
//        callbackContext.setContext(this);
//        mDisposable.add(ApiDataManager.getMyThings(callbackContext, mLastSearchQuery));
//
//    }
//    private void getDateResponseList() {
//        ArrayList<ItemsBean> dateList = AppPreference.getInstance(GettingResponse.this).getList();
//        for (int i = 0; i>=dateList.size();i++){
//
//            double dateResponse = Double.parseDouble(dateList.get(i).getOmarDate());
//            AppPreference.getInstance(GettingResponse.this).setOmarDate(dateResponse);
//        }
//    }
}

