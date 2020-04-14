package com.spearheadinc.flashcards.omer;

//http://developer.android.com/guide/webapps/targeting.html

import java.io.File;
import java.util.ArrayList;

import com.location.callbacks.AppLocationListener;
import com.location.getaddress.AppLocationActivity;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.apputil.DBManager;
import com.spearheadinc.flashcards.omer.retrofit.ApiDataManager;
import com.spearheadinc.flashcards.omer.retrofit.CallbackContext;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import io.reactivex.disposables.CompositeDisposable;

public class FlashCards extends AppLocationActivity {
	private CompositeDisposable mDisposable = new CompositeDisposable();
	private String mLastSearchQuery = "MD";

	private static FlashCards screen;
	private boolean tagValue;
	private ArrayList<ItemsBean> arrayList;

	int REQUEST_FINE_LOCATION = 111;

	public static FlashCards getScreen() {
		return screen;
	}

	@Override
	protected int getLayoutResFile() {
		return R.layout.fcmain;
	}

	private GpsLocationReceiver mGpsLocationReceiver;


	private void getLocation() {

		if (mGpsLocationReceiver == null) {
			mGpsLocationReceiver = new GpsLocationReceiver();
			registerReceiver(mGpsLocationReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
		}

		getLocation(new AppLocationListener() {
			@Override
			public void locationReceived(String maxAddress, String pin, String state, String city,
										 String subCity, String countryCode, double latitude, double longitude) {
				Log.i("", "");
				AppPreference.getInstance(FlashCards.this).setLatitude(latitude);
				AppPreference.getInstance(FlashCards.this).setLongitude(longitude);
			}

			@Override
			public void locationFailed() {
				Log.i("", "");
			}
		});
	}

	private boolean checkPermissions() {
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			return true;
		} else if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
			return true;
		} else if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
			return true;
		} else {
			requestPermissions();
			return false;
		}
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.READ_CALENDAR,
						Manifest.permission.WRITE_CALENDAR},
				REQUEST_FINE_LOCATION);
	}

	private class GpsLocationReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
				getLocation();
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		// Make sure it's our original ACCESS_FINE_LOCATION request
		if (requestCode == REQUEST_FINE_LOCATION) {
			if (grantResults.length > 1 &&
					grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				getLocation();
				DBManager.getInstance(this).getMyFCDbHelper();
			} else {
				// false if user clicks Never Ask Again, otherwise true
				finish();
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	/**
	 * Called when the activity is first created.
	 */
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		screen = this;
		AppPreference.getInstance(FlashCards.this).getList();
		searchInWeb();
		setOmarCurrentDate();

		if (checkPermissions()) {
			getLocation();
			DBManager.getInstance(this).getMyFCDbHelper();

		}


		SharedPreferences booleanTag = screen.getSharedPreferences(screen.getString(R.string.CHECK_FOR_VERSION), 0);
		tagValue = booleanTag.getBoolean("bValue", tagValue);

		Button tv = (Button) findViewById(R.id.fc_splash_taper);
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(screen, DeckView.class));
				DecompressZip dz = new DecompressZip("", "/data/data/com.spearheadinc.flashcards.omer/", screen.getResources().openRawResource(R.raw.audio));


				if (!dz.doesDirExist("/data/data/com.spearheadinc.flashcards.omer/files")) {
					Log.i("CardDetails", "##Creating Dir as it is not found !");
					SharedPreferences booleanTag = screen.getSharedPreferences(screen.getString(R.string.CHECK_FOR_VERSION), 0); // 0 - for private mode
					Editor editor1 = booleanTag.edit();
					editor1.putBoolean("bValue", true);
					editor1.commit();
					dz.unzip(screen);
				} else {
					if (tagValue == false) {
						File f = new File("/data/data/com.spearheadinc.flashcards.omer/files");
						f.delete();
						System.out.println("Directory Deleted");
						SharedPreferences booleanTag = screen.getSharedPreferences(screen.getString(R.string.CHECK_FOR_VERSION), 0); // 0 - for private mode
						Editor editor1 = booleanTag.edit();
						editor1.putBoolean("bValue", true);
						editor1.commit();
						dz.unzip(screen);
					}

				}
			}
		});
	}


	@Override
	protected void onDestroy() {
		if (mGpsLocationReceiver != null) {
			unregisterReceiver(mGpsLocationReceiver);
		}
		super.onDestroy();
	}

	private void searchInWeb() {
		CallbackContext callbackContext = new CallbackContext<ArrayList<ItemsBean>>() {
			@Override
			public void onNext(ArrayList<ItemsBean> articleBeans) {
				Log.i("", "");

				AppPreference.getInstance(FlashCards.this).putList(articleBeans);
//				getDateFromResponseList();


			}


			@Override
			public void onError(Throwable t, String str) {
				Log.i("", "");

			}

			@Override
			public void onComplete(String str) {
				Log.i("", "");


			}
		};
		callbackContext.setContext(this);
		mDisposable.add(ApiDataManager.getMyThings(callbackContext, mLastSearchQuery));

	}


	@RequiresApi(api = Build.VERSION_CODES.O)
	private void setOmarCurrentDate() {
		ArrayList<ItemsBean> omarList = AppPreference.getInstance(FlashCards.this).getList();
		ArrayList<String> dateList = new ArrayList<>();
	}
}

