package com.spearheadinc.flashcards.omer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.apputil.DBManager;
import com.spearheadinc.flashcards.omer.notification.NotificationActivity;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;
import com.spearheadinc.flashcards.sunrisesunset.SunriseSunsetCalculator;
import com.spearheadinc.flashcards.sunrisesunset.dto.Location;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DeckView extends Activity implements LocationListener {
    private static DeckView screen;
    List<String> listSearchCardName = new ArrayList<String>();
    List<String> listSearchCardid = new ArrayList<String>();
    List<String> listSearchCardColor = new ArrayList<String>();
    TextView profvalueAllCard;
    TextView profvalueBookMark;
    TextView totNumbOfAllCard;
    TextView totNumbOfBookMarkCard;
    ImageView logoImage;
    private FCDBHelper mFCDbHelper;
    private String fromClassName;
    private LinearLayout mainDeckViewLinear;
    protected int MAX_CARD_NUMBER = 0;
    protected LocationManager locationManager;
    protected android.location.Location localLocation;
    private String mFrom;

    public static DeckView getScreen() {
        return screen;
    }

    public RelativeLayout createSeqUnderLine() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        RelativeLayout detailRow = (RelativeLayout) mInflater.inflate(R.layout.sequential_textview, null);
        return detailRow;
    }
    private LinearLayout CreateRowView(int totalCards, String declTitle, int bgImage, String deckColor, String deckProf, int totl, int i)// notesText, String strFilePath, LinearLayout seqOrderLinrOpt)
    {
        LayoutInflater mInflater = LayoutInflater.from(this);
        LinearLayout detailRow = (LinearLayout) mInflater.inflate(R.layout.deckviewcellrow, null);
        RelativeLayout relBgGastro = (RelativeLayout) detailRow.findViewById(R.id.deck_in_layout_eighth);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        relBgGastro.getLayoutParams().height = (193 * width) / 1000;
        relBgGastro.setBackgroundResource(bgImage);
        TextView deckTitleView = (TextView) detailRow.findViewById(R.id.drugs_title_eighth);
        TextView deckTitleView_noImage = (TextView) detailRow.findViewById(R.id.drugs_title_eighth_noImage);
        deckTitleView.setVisibility(View.GONE);
        deckTitleView_noImage.setVisibility(View.GONE);
        deckTitleView_noImage.setText(Html.fromHtml(declTitle));
        TextView totalCardsView = (TextView) detailRow.findViewById(R.id.drugs_cards_numb_eighth);
        totalCardsView.setText(totalCards + " cards");
        TextView profValueView = (TextView) detailRow.findViewById(R.id.drugs_proficiency_value_eighth);
        profValueView.setText(deckProf);
        listDeckPreferenceView.add(profValueView);
        System.out.println("CreateRowViewlistDeckPreferenceView  =  " + profValueView);
        TextView relCustomClicker = (TextView) detailRow.findViewById(R.id.drugs_eighth_clickable_view);
        {
            setDataTagsToView(i, totl, profValueView, relCustomClicker);
            relCustomClicker.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setViewStatus(v);
                }
            });
        }
        return detailRow;
    }

    @SuppressWarnings("unchecked")
    private void setDataTagsToView(int i, int totl, TextView strFilePath, TextView relCustomshapeQuizView) {
        List listRel = new ArrayList();

        listRel.add(strFilePath);
        listRel.add(totl);
        listRel.add(i);


        relCustomshapeQuizView.setTag(listRel);
    }

    @SuppressWarnings("unchecked")
    private void setViewStatus(View v) {

        List listRel = (List) v.getTag();
        total = (Integer) listRel.get(1);
        if (listRel != null && listRel.size() > 0) {
            TextView recordFilePath = (TextView) listRel.get(0);
            Intent i = new Intent(DeckView.this, ListCardName.class);
            int in = (Integer) listRel.get(2) + 1;
            i.putExtra("com.android.flashcard.screen.cardDetail", in);
            startActivity(i);
            Log.e("detailRow1", recordFilePath + "" + "");
        }
    }

    public List<String> getListCardsPreferenceName() {
        return listCardsPreferenceName;
    }

    public List<String> getListCardsPreferenceValue() {
        return listCardsPreferenceValue;
    }

    List<String> listCardsPreferenceName;
    List<String> listCardsPreferenceValue;

    List<String> listDeckPreferenceName;
    List<String> listDeckPreferenceValue;

    List<TextView> listDeckPreferenceView;

    public List<String> getListDeckPreferenceName() {
        return listDeckPreferenceName;
    }

    public List<String> getListDeckPreferenceValue() {
        return listDeckPreferenceValue;
    }

    public List<String> getListSearchCardName() {
        return listSearchCardName;
    }

    public List<String> getListSearchCardid() {
        return listSearchCardid;
    }


    public void onLocationChanged(Location location) {
        BigDecimal latlocation = location.getLatitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    /**
     * Called when the activity is first created.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = this;
        setContentView(R.layout.pharm);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            // First get location from Network Provider
            if (isNetworkEnabled) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromClassName = extras.getString("com.nclex.qa.app.packageName.nclecquizview.class.name");
            mFrom = extras.getString("from");
            if (mFrom != null && mFrom.equals("pushNotification")){
                callTodaysReading();
            }
        }


        logoImage = (ImageView) findViewById(R.id.companyname_title);
        mainDeckViewLinear = (LinearLayout) findViewById(R.id.deckview_lin_drugs_main_layout);

        profvalueAllCard = (TextView) findViewById(R.id.proficiency_value);
        profvalueBookMark = (TextView) findViewById(R.id.bookmarked_cards_proficiency_value);
        totNumbOfAllCard = (TextView) findViewById(R.id.drugs_cards_numb_all_cards);
        totNumbOfBookMarkCard = (TextView) findViewById(R.id.no_of_bookmarked_cards);
        mFCDbHelper = DBManager.getInstance(this).getMyFCDbHelper();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        logoImage.getLayoutParams().height = (73 * width) / 320;

        mFCDbHelper.openDataBase();
        Cursor cursorDeck = mFCDbHelper.getDeksInfoCursor();
        int arrayDeckBackgroundImage[] = new int[cursorDeck.getCount()];
        arrayDeckBackgroundImage[0] = R.drawable.week1;
        arrayDeckBackgroundImage[1] = R.drawable.week2;
        arrayDeckBackgroundImage[2] = R.drawable.week3;
        arrayDeckBackgroundImage[3] = R.drawable.week4;
        arrayDeckBackgroundImage[4] = R.drawable.week5;
        arrayDeckBackgroundImage[5] = R.drawable.week6;
        arrayDeckBackgroundImage[6] = R.drawable.week7;
        mFCDbHelper.close();
        SharedPreferences myPrefs = null;

        listDeckPreferenceName = new ArrayList<String>();
        listDeckPreferenceValue = new ArrayList<String>();
        listCardsPreferenceName = new ArrayList<String>();
        listCardsPreferenceValue = new ArrayList<String>();
        listDeckPreferenceView = new ArrayList<TextView>();

        ArrayList<ItemsBean> weakList = AppPreference.getInstance(this).getList();

        cursorDeck.moveToFirst();
        for (int i = 0; i < cursorDeck.getCount(); i++) {

            String strArr = cursorDeck.getString(cursorDeck.getColumnIndexOrThrow(FCDBHelper.DECKTITLE));
            String strDeckPrefName = strArr;
            String strDeckPrefValue = strArr;
            String strCardPrefsName = strArr;
            String strCardPrefsValue = strArr;

            strDeckPrefName = strDeckPrefName.substring(0, 4/*strDeckPrefName.indexOf(" ") - 1*/) + "CARDPREFS";
            strDeckPrefValue = strDeckPrefValue.substring(0, 4/*strDeckPrefValue.indexOf(" ") - 1*/) + "CARDPREFSVALUE";
            strCardPrefsName = strCardPrefsName.substring(0, 4/*strCardPrefsName.indexOf(" ") - 1*/) + "PREFSGENERICCD";
            strCardPrefsValue = "STRCLICK" + strCardPrefsValue.substring(0, 4/*strCardPrefsValue.indexOf(" ") - 1*/) + "CD";

            listDeckPreferenceName.add(strDeckPrefName);
            System.out.println("DOCstrDeckPrefName   =  " + strDeckPrefName);
            listDeckPreferenceValue.add(strDeckPrefValue);
            System.out.println("DOCstrDeckPrefValue  =  " + strDeckPrefValue);
            listCardsPreferenceName.add(strCardPrefsName);
            System.out.println("DOCstrCardPrefsName  =  " + strCardPrefsName);
            listCardsPreferenceValue.add(strCardPrefsValue);
            System.out.println("DOCstrCardPrefsValue  =  " + strCardPrefsValue);
            cursorDeck.moveToNext();
        }
        myPrefs = DeckView.this.getSharedPreferences("StrAllCardPrefs", MODE_PRIVATE);
        String strAllCard = myPrefs.getString("STRALLCARD", "0%");
        profvalueAllCard.setText(strAllCard);

        myPrefs = DeckView.this.getSharedPreferences("StrBookMarkPrefs", MODE_PRIVATE);
        String strBookMark = myPrefs.getString("STRBOOKMARK", "0.00%");
        profvalueBookMark.setText(strBookMark);

        myPrefs = DeckView.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_PRIVATE);
        int bookMarkProfcardStatus = myPrefs.getInt("BOOKMARKEDCARDS", 0);
        totNumbOfBookMarkCard.setText(bookMarkProfcardStatus + " cards");

        mFCDbHelper.openDataBase();
        MAX_CARD_NUMBER = mFCDbHelper.getDeksTotalNumOfCards("");
        mFCDbHelper.close();
        totNumbOfAllCard.setText(MAX_CARD_NUMBER + " cards");
        total = 1;

        cursorDeck.moveToFirst();
        for (int i = 0; i < cursorDeck.getCount(); i++) {
            mFCDbHelper.openDataBase();
            int totalCards = mFCDbHelper.getDeksTotalNumOfCards(i + "");
            mFCDbHelper.close();
            myPrefs = DeckView.this.getSharedPreferences(listDeckPreferenceName.get(i), MODE_PRIVATE);
            String prefVal = myPrefs.getString(listDeckPreferenceValue.get(i), "0%");

            LinearLayout detailRow = CreateRowView(totalCards, cursorDeck.getString(cursorDeck.getColumnIndexOrThrow(FCDBHelper.DECKTITLE)),
                    arrayDeckBackgroundImage[i], cursorDeck.getString(cursorDeck.getColumnIndexOrThrow(FCDBHelper.DECKCOLOR)), prefVal, total, i);

            if (i <= cursorDeck.getCount() + 1)
                mainDeckViewLinear.addView(createSeqUnderLine());
            mainDeckViewLinear.addView(detailRow);
            System.out.println("DOCTotalcards =  " + total);
            total = total + totalCards + 1;
            System.out.println("DOCTotalcardsafter =  " + total);
            cursorDeck.moveToNext();
        }

        RelativeLayout allcardRel = (RelativeLayout) findViewById(R.id.all_cards);
        allcardRel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeckView.this, ListCardName.class);
                Integer in = -1;
                i.putExtra("com.android.flashcard.screen.cardDetail", in);
                startActivity(i);
            }
        });
        RelativeLayout allsongsRel = (RelativeLayout) findViewById(R.id.all_songs);
        allsongsRel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeckView.this, ListCardName.class);
                Integer in = -3;
                i.putExtra("com.android.flashcard.screen.cardDetail", in);
                startActivity(i);
            }
        });
        RelativeLayout bookmarkcardRel = (RelativeLayout) findViewById(R.id.bookmarked_cards);
        bookmarkcardRel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFCDbHelper.openDataBase();
                List<String> listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
                mFCDbHelper.close();
                int lastCardNo = listpk_FlashCardId.size();
                if (lastCardNo < 1) {
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView.this);
                    alt_bld.setMessage("There are no bookmarkes and notes yet.").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alt_bld.create();
                    alert.setTitle("Information");
                    alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
                    alert.show();
                } else {
                    Intent i = new Intent(DeckView.this, ListCardName.class);
                    Integer in = -2;
                    i.putExtra("com.android.flashcard.screen.cardDetail", in);
                    startActivity(i);
                }
            }
        });

        Button searchbut = (Button) findViewById(R.id.search_footer);
        searchbut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)

            {
                startActivity(new Intent(DeckView.this, Search.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.hold);
            }
        });

        Button notification = (Button)findViewById(R.id.notificationBtn);
        notification.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DeckView.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        Button settingBut = (Button) findViewById(R.id.setting_footer);
        settingBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)

            {
                startActivity(new Intent(DeckView.this, Setting.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.hold);
            }
        });

        Button helpBut = (Button) findViewById(R.id.help_footer);
        helpBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeckView.this, Help.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.hold);
            }
        });
        final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        final RelativeLayout takePickPanal = (RelativeLayout) findViewById(R.id.sliderinfo_top_rel);
        Button infoBut = (Button) findViewById(R.id.info_footer);
        infoBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeckView.this, Info.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.hold);

            }
        });

        RelativeLayout introLayout = (RelativeLayout) findViewById(R.id.introduction);
        introLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeckView.this, Introduction.class);
                startActivity(i);
            }
        });

        RelativeLayout todayReading = (RelativeLayout) findViewById(R.id.todays_reading);
        todayReading.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                callTodaysReading();
            }

        });


        ImageButton infoSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_info);
        infoSliderBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePickPanal.clearAnimation();
                takePickPanal.setVisibility(View.GONE);
                startActivity(new Intent(DeckView.this, Info.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.hold);
                mainLayout.removeView(takePickPanal);
            }
        });


        ImageButton voiceSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_voicenotes);
        voiceSliderBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFCDbHelper.openDataBase();
                List<String> list = mFCDbHelper.getVoiceSearchResultsForIndividual();
                mFCDbHelper.close();
                takePickPanal.clearAnimation();
                if (list.size() == 0) {
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView.this);
                    alt_bld.setMessage("No Voice Notes Found!").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alt_bld.create();
                    alert.setTitle("Information");
                    alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
                    alert.show();
                } else {
                    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
                    slide.setDuration(700);
                    slide.setFillAfter(true);
                    takePickPanal.startAnimation(slide);
                    takePickPanal.setVisibility(View.GONE);
                    Intent i = new Intent(DeckView.this, CardNotesDetail.class);//VoiceNoteDetails
                    i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "VIEW");
                    startActivity(i);
                    overridePendingTransition(R.anim.push_up_in, R.anim.hold);
                }
                mainLayout.removeView(takePickPanal);
            }
        });

        ImageButton commentSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_comments);
        commentSliderBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFCDbHelper.openDataBase();
                List<String> list = mFCDbHelper.getCommentSearchResultsForIndividual();
                mFCDbHelper.close();
                if (list.size() == 0) {
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView.this);
                    alt_bld.setMessage("No Comments Found!").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alt_bld.create();
                    alert.setTitle("Information");
                    alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
                    alert.show();
                } else {
                    takePickPanal.clearAnimation();
                    takePickPanal.setVisibility(View.GONE);
                    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
                    slide.setDuration(700);
                    slide.setFillAfter(true);
                    takePickPanal.startAnimation(slide);
                    takePickPanal.setVisibility(View.GONE);
                    Intent i = new Intent(DeckView.this, MyComments.class);        //CommentDetails
                    i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "VIEW");
                    startActivity(i);
                    overridePendingTransition(R.anim.push_up_in, R.anim.hold);
                }
                mainLayout.removeView(takePickPanal);
            }
        });

        ImageButton facebookSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_facebook);
        facebookSliderBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePickPanal.clearAnimation();
                takePickPanal.setVisibility(View.GONE);
                TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
                slide.setDuration(700);
                slide.setFillAfter(true);
                takePickPanal.startAnimation(slide);
                takePickPanal.setVisibility(View.GONE);
                //postMessage();
                mainLayout.removeView(takePickPanal);
            }
        });

        ImageButton teitterSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_twitter);
        teitterSliderBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePickPanal.clearAnimation();
                takePickPanal.setVisibility(View.GONE);
                TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
                slide.setDuration(700);
                slide.setFillAfter(true);
                takePickPanal.startAnimation(slide);
                takePickPanal.setVisibility(View.GONE);
                String twitterUri = "http://m.twitter.com/?status=";
                String marketUri = "TESTING ";
                Intent shareOnTwitterIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(twitterUri + marketUri));
                startActivity(shareOnTwitterIntent);
                mainLayout.removeView(takePickPanal);
            }
        });

        ImageButton cancelSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_cancel);
        cancelSliderBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePickPanal.clearAnimation();
                takePickPanal.setVisibility(View.GONE);
                TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
                slide.setDuration(700);
                slide.setFillAfter(true);
                takePickPanal.startAnimation(slide);
                takePickPanal.setVisibility(View.GONE);
                mainLayout.removeView(takePickPanal);
            }
        });

        if (fromClassName != null && fromClassName.equals("PREPAREREQUESTTOKENACTIVITY")) {
            //startSendingMessage();
        }
    }

    static int total;

    public int Color2Hex(String[] args) {
        int cc = 0;
        if (args.length != 3) {
            String col = args[0];
            String r = col.substring(0, col.indexOf(","));
            String g = col.substring(col.indexOf(",") + 1, col.lastIndexOf(","));
            String b = col.substring(col.lastIndexOf(",") + 1, col.length());
            int red = Integer.parseInt(r);
            int green = Integer.parseInt(g);
            int blue = Integer.parseInt(b);
            cc = Color.argb(255, red, green, blue);

        } else {
            int red = Integer.parseInt(args[0]);
            int green = Integer.parseInt(args[1]);
            int blue = Integer.parseInt(args[2]);
            cc = Color.argb(255, red, green, blue);//rgb(red, green, blue);
        }
        return cc;
    }

    private void populateProficiencyValue(double dbl, TextView tv, String sharedPrefName, String sharedPrefParam) {

        String value = "0%";

        if (dbl > 0) {
            int ix = (int) (dbl * 10000.0);
            int dbl2 = (ix) / 100;
            value = dbl2 + "%";
        }

        tv.setText(value);
        SharedPreferences myPrefs = DeckView.this.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(sharedPrefParam, value);
        prefsEditor.commit();
    }

    public void deleteAllBookmarks() {
        profvalueBookMark.setText("0.00%");
        totNumbOfBookMarkCard.setText(0 + " cards");
        SharedPreferences myPrefs = DeckView.this.getSharedPreferences("StrBookMarkPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString("STRBOOKMARK", "0.00%");
        prefsEditor.commit();
        myPrefs = DeckView.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_PRIVATE);
        prefsEditor = myPrefs.edit();
        prefsEditor.putInt("BOOKMARKEDCARDS", 0);
        prefsEditor.commit();
    }

    public void setStrAllCard(int strAllCard) {
        Log.e("setStrAllCard", strAllCard + " : " + 49);
        double dbl = (double) strAllCard / 49;
        populateProficiencyValue(dbl, profvalueAllCard, "StrAllCardPrefs", "STRALLCARD");
    }

    public void setStrBookMark(int strBookMark, int total) {
        Log.e("setStrBookMark", strBookMark + " : " + total);
        double dbl = (double) strBookMark / total;
        populateProficiencyValue(dbl, profvalueBookMark, "StrBookMarkPrefs", "STRBOOKMARK");
        SharedPreferences myPrefs = DeckView.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt("BOOKMARKEDCARDS", total);
        prefsEditor.commit();
        totNumbOfBookMarkCard.setText(total + " cards");
    }

    public void setProfSelGastro(int strQZ, int totals, int i) {
        Log.e("setProfSelGastro", strQZ + " : " + totals);
        double dbl = (double) strQZ / totals;
        populateProficiencyValue(dbl, listDeckPreferenceView.get(i), listDeckPreferenceName.get(i), listDeckPreferenceValue.get(i));
    }

    public void deleteAllProficiency() {
        profvalueBookMark.setText("0%");
        populateProficiencyValue(0.00, profvalueAllCard, "StrAllCardPrefs", "STRALLCARD");
        for (int i = 0; i < listDeckPreferenceView.size(); i++) {
            populateProficiencyValue(0.00, listDeckPreferenceView.get(i), listDeckPreferenceName.get(i), listDeckPreferenceValue.get(i));
            System.out.println("deleteAllProficiency  =  " + i + " , " + listDeckPreferenceView.get(i) + " , " + listCardsPreferenceName.get(i) + " , " + listCardsPreferenceValue.get(i));

        }
        isdeleteAllProficiency = true;
        decrementProficiency();
    }

    public boolean isdeleteAllProficiency = false;

    private void decrementProficiency() {
        storeDataInPreferences(0, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
        storeDataInPreferences(0, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
        storeDataInPreferences(0, "StrbookMarkProfcardsStatusPrefsGenericcd", "BOOKMARKPROFCARDSTATUScd");

        for (int i = 0; i < listCardsPreferenceName.size(); i++) {
            storeDataInPreferences(0, i/*listCardsPreferenceName.get(i), listCardsPreferenceValue.get(i)*/);
        }
    }

    protected void storeDataInPreferences(int value, String sharedPrefName, String sharedPrefParam) {
        SharedPreferences myPrefs = DeckView.this.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt(sharedPrefParam, value);
        prefsEditor.commit();
        System.out.println("storeDataInPreferences  =  " + value + " , " + sharedPrefName + " , " + sharedPrefParam);
    }

    protected void storeDataInPreferences(int value, int i/*, String sharedPrefParam*/) {
        SharedPreferences myPrefs = DeckView.this.getSharedPreferences(listCardsPreferenceName.get(i), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt(listCardsPreferenceValue.get(i), value);
        System.out.println("storeDataInPreferencesvalue  =  " + i + " , " + value + " , " + listCardsPreferenceName.get(i) + " , " + listCardsPreferenceValue.get(i));
        prefsEditor.commit();
    }

    public void resetApplication() {
        decrementProficiency();
        deleteAllBookmarks();
        deleteAllProficiency();
    }

    private ProgressDialog mProgressDialog;

    @Override
    public void onLocationChanged(android.location.Location location) {
        // TODO Auto-generated method stub

        localLocation = location;
    }

    public void callTodaysReading() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//				// getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {


            ArrayList<ItemsBean> omarDates = AppPreference.getInstance(DeckView.this).getList();
            String lastDate = "";
            String statDate = "";
            int startYear = 0;
            int startMonth = 0;
            int startDay = 0;
            int lastYear = 0;
            int lastMonth = 0;
            int lastDay = 0;


            if (omarDates != null && omarDates.size() > 1) {
                statDate = omarDates.get(0).getDate();

                String parts[] = statDate.split("-");
                startYear = Integer.parseInt(parts[0]);
                startMonth = Integer.parseInt(parts[1]);
                startDay = Integer.parseInt(parts[2]);

                lastDate = omarDates.get(omarDates.size() - 1).getDate();
                String parts1[] = lastDate.split("-");
                lastYear = Integer.parseInt(parts1[0]);
                lastMonth = Integer.parseInt(parts1[1]);
                lastDay = Integer.parseInt(parts1[2]);


            }

            //    Frist date of omar in calander
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(Calendar.YEAR, startYear);
            beginTime.set(Calendar.MONTH, startMonth - 1);
            beginTime.set(Calendar.DAY_OF_MONTH, startDay - 1);
            beginTime.getTime();
            System.out.println("Frist of OmarDates  : " + dateFormat.format(beginTime.getTime()));

            //    Last date of omar in calander

            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar endTime = Calendar.getInstance();
            endTime.set(Calendar.YEAR, lastYear);
            endTime.set(Calendar.MONTH, lastMonth - 1);
            endTime.set(Calendar.DAY_OF_MONTH, lastDay - 1);
            endTime.getTime();
            System.out.println("Last of OmarDates  : " + dateFormat.format(endTime.getTime()));


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 10, DeckView.this);
        android.location.Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location location1 = new Location(AppPreference.getInstance(DeckView.this).getLatitude(), AppPreference.getInstance(DeckView.this).getLatitude());

        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location1, location.getProvider());
        Calendar beginSunset = calculator.getOfficialSunsetCalendarForDate(beginTime);
        Calendar endSunset = calculator.getOfficialSunsetCalendarForDate(endTime);


        if (Calendar.getInstance().before(beginSunset)) {
            Intent i = new Intent(DeckView.this, BeforeOmer.class);

            startActivity(i);
        } else if (Calendar.getInstance().after(endSunset)) {
            Intent i = new Intent(DeckView.this, AfterOmer.class);

            startActivity(i);
        } else {
            System.out.println("Calendar.getInstance().getTimeInMillis() == " + Calendar.getInstance().getTimeInMillis());
            System.out.println("beginSunset.getTimeInMillis() == " + beginSunset.getTimeInMillis());
            long dayDiff = (Calendar.getInstance().getTimeInMillis() - beginSunset.getTimeInMillis()) / (24 * 60 * 60 * 1000);
            mFCDbHelper = DBManager.getInstance(DeckView.this).getMyFCDbHelper();
            mFCDbHelper.openDataBase();
            List<String[]> list = mFCDbHelper.getTodaysReadingFlashCard((int) dayDiff + 1);
            mFCDbHelper.close();
            String[] strArr = list.get(0);
            listSearchCardid = new ArrayList<String>();
            listSearchCardName = new ArrayList<String>();
            listSearchCardColor = new ArrayList<String>();
            listSearchCardid.add(strArr[0]);
            listSearchCardName.add(strArr[1]);
            listSearchCardColor.add(strArr[2]);
            Intent i = new Intent(DeckView.this, CardDetails.class);
            i.putExtra("FROM", "TodaysReading");
            i.putExtra("POSITION", "" + 0);
            i.putExtra("SEARCHSTRING", "" + ""/*s*/);
            i.putExtra("TOTALCARDS", "" + listSearchCardid.size());
            i.putExtra("CLASSNAME", "LISTCARDNAME");
            i.putExtra("isBookmarked", "false");
            startActivity(i);

        }
    }
}

}