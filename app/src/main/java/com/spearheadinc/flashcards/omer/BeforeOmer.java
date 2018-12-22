package com.spearheadinc.flashcards.omer;

import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.internal.Util;

public class BeforeOmer extends Activity {
	private static BeforeOmer screen;
	private WebSettings localWebView;
	private RelativeLayout backBtn_before_omar;
    private String statDate = "";
    private String finalDateString;
    public static BeforeOmer getScreen()
    {
    	return screen;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = this;
        setContentView(R.layout.beforecard);
        backBtn_before_omar = findViewById(R.id.trade_title);
        backBtn_before_omar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView mWebView = (WebView) findViewById(R.id.webview);
        localWebView = mWebView.getSettings();
        localWebView.setBuiltInZoomControls(true);
        localWebView.setSupportZoom(true);
        localWebView.setLoadWithOverviewMode(true);
        localWebView.setUseWideViewPort(true);

        ItemsBean itemsBean = new ItemsBean();
        itemsBean.getDate();
        ArrayList<ItemsBean> omarDates = AppPreference.getInstance(BeforeOmer.this).getList();

        if (omarDates != null && omarDates.size() > 1) {

            statDate = omarDates.get(0).getDate();
            String strDate = statDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(strDate);
                SimpleDateFormat sdfnewformat = new SimpleDateFormat("MMM dd yyyy");
                finalDateString = sdfnewformat.format(convertedDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            mWebView.loadDataWithBaseURL("file:///android_asset/" + "beforecard.html", showBeforeStr(finalDateString), "text/html", "UTF-8", null);
        }

    }
    private String showBeforeStr(String date) {
        String str = "<html>\n" +
                "<head>\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width\"/>\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width\"/>\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width\"/>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"dCommon.css\"/>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"dBack.css\"/>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" media=\"all and (orientation:portrait)\" href=\"dBackP.css\" />\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" media=\"all and (orientation:landscape)\" href=\"dBackL.css\" />\n" +
                "\t<style type=\"text/css\">\n" +
                "\ttable.dContents td{\n" +
                "\t\ttext-align:left;\n" +
                "\t}\n" +
                "\n" +
                "\tp.item{\n" +
                "\t\tposition:relative;margin-left:4em;\n" +
                "\t}\n" +
                "\t\n" +
                "\tp.itemIndentLevel1{margin-left:6em;}\n" +
                "\t\n" +
                "\t\n" +
                "\tp.item img.itemIcon{\n" +
                "\t\tposition:absolute;left:-2.5em;width:2em;height:2em;\n" +
                "\t}\n" +
                "\t\n" +
                "\ttable{font-size:1em;}\n" +
                "\t\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body class=\"dWeek2A\" style=\"background:none;\">\n" +
                "    <div class=\"dOuterWrap\"></div>\n" +
                "\n" +
                "<div class=\"dOuter\" style=\"height:auto;\">\n" +
                "<div class=\"dInner\" style=\"height:auto;\">\n" +
                "<div class=\"dContentsBox\" style=\"height:auto;overflow-y:none;\">\n" +
                "<table class=\"dContents\" style=\"height:95%;\"><tr><td>\n" +
                "You’re early! The counting of the Omer has not yet begun.  Feel free to browse through the cards, " +
                "and check back after sundown on " +
                date +
                ".\n" +
                "\n" +
                "</td></tr></table>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n" +
                "\n";

        return str;
    }

}