package com.spearheadinc.flashcards.omer;

import com.spearheadinc.flashcards.apputil.DBManager;
import com.spearheadinc.flashcards.omer.notification.NotificationActivity;
import com.spearheadinc.flashcards.omer.retrofit.GettingResponse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Setting extends Activity {
	private static Setting screen;
    private	FCDBHelper mFCDbHelper;
    private RelativeLayout setting_backBtn;
    public static Setting getScreen() 
    {
    	return screen;
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) 
        {
			finish();
			overridePendingTransition(R.anim.hold, R.anim.push_up_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		screen = this;
		setContentView(R.layout.setting);
		
//        TextView tv = (TextView) findViewById(R.id.settingback);

		setting_backBtn = findViewById(R.id.settingback);
		setting_backBtn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		 mFCDbHelper = DBManager.getInstance(this).getMyFCDbHelper();

		final Button offBtn = (Button) findViewById(R.id.offBtn);
		final Button onBtn = (Button) findViewById(R.id.onBtn);
		offBtn.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				offBtn.setVisibility(View.GONE);
				onBtn.setVisibility(View.VISIBLE);
				mFCDbHelper.setRandomized(true);
				
			}
		});
		
		onBtn.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				offBtn.setVisibility(View.VISIBLE);
				onBtn.setVisibility(View.GONE);
				mFCDbHelper.setRandomized(false);
				
			}
		});
		
	  if(mFCDbHelper.getRandomized())
	  {
	        onBtn.setVisibility(View.VISIBLE);
	        offBtn.setVisibility(View.GONE);
	  }
	  else
	  {
		  onBtn.setVisibility(View.GONE);
	        offBtn.setVisibility(View.VISIBLE); 
	  }
//        RelativeLayout tv1 = (RelativeLayout) findViewById(R.id.setting_delete_bookmarks);
        RelativeLayout clearbook = (RelativeLayout) findViewById(R.id.setting_delete_bookmarks);
        clearbook.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all bookmarks ?");
				customizeDialog.show();
			}
		});
//        RelativeLayout tv2 = (RelativeLayout) findViewById(R.id.setting_delete_proficiency);
        RelativeLayout clearprof = (RelativeLayout) findViewById(R.id.setting_delete_proficiency);
        clearprof.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all proficiencies ?");
				customizeDialog.show();
			}
		});

        RelativeLayout clearcomment = (RelativeLayout) findViewById(R.id.setting_delete_comments);
        clearcomment.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all Text Notes ?");
				customizeDialog.show();
			}
		});
        RelativeLayout resetapp = (RelativeLayout) findViewById(R.id.resetApplication);
        resetapp.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset Application ?");
				customizeDialog.show();
			}
		});

    }
    
    private CustomizeDialog customizeDialog;
}
