package com.spearheadinc.flashcards.omer;

//http://developer.android.com/guide/webapps/targeting.html

import java.io.File;

import com.location.callbacks.AppLocationListener;
import com.location.getaddress.AppLocationActivity;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.apputil.DBManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlashCards extends AppLocationActivity {
	private static FlashCards screen;
//	FCDBHelper mFCDbHelper;
	private boolean tagValue;

	int REQUEST_FINE_LOCATION = 111;
	

//	public FCDBHelper getMyFCDbHelper() {
//		return mFCDbHelper;
//	}
	
    public static FlashCards getScreen() 
    {
    	return screen;
	}

	@Override
	protected int getLayoutResFile() {
		return R.layout.fcmain;
	}

	private GpsLocationReceiver mGpsLocationReceiver;


	private void getLocation() {

		if(mGpsLocationReceiver == null) {
			mGpsLocationReceiver = new GpsLocationReceiver();
			registerReceiver(mGpsLocationReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
		}

		getLocation(new AppLocationListener() {
			@Override
			public void locationReceived(String maxAddress, String pin, String state, String city,
										 String subCity, String countryCode, double latitude, double longitude) {
				Log.i("","");
				AppPreference.getInstance(FlashCards.this).setLatitude(latitude);
				AppPreference.getInstance(FlashCards.this).setLongitude(longitude);
			}

			@Override
			public void locationFailed() {
				Log.i("","");
			}
		});
	}

	private boolean checkPermissions() {
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			return true;
		}
		else if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
			return true;
		}
		else if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
			return true;
		}
		else {
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
			if (grantResults.length == 1 &&
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

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		screen = this;
//		DatabaseProvider dbProvider = new DatabaseProvider();
//		dbProvider.createDB(this);
//		mFCDbHelper = dbProvider.getMyFCDbHelper();

		if(checkPermissions()) {
			getLocation();
			DBManager.getInstance(this).getMyFCDbHelper();

		}


	   
	    SharedPreferences booleanTag = screen.getSharedPreferences( screen.getString( R.string.CHECK_FOR_VERSION), 0 );
		tagValue = booleanTag.getBoolean("bValue", tagValue);
		
		Button tv = (Button) findViewById(R.id.fc_splash_taper);
		tv.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				startActivity(new Intent(screen, DeckView.class));
				DecompressZip dz = new DecompressZip( "", "/data/data/com.spearheadinc.flashcards.omer/", screen.getResources().openRawResource(R.raw.audio) );
				
			      
				if (!dz.doesDirExist("/data/data/com.spearheadinc.flashcards.omer/files") )
				{
						Log.i("CardDetails", "##Creating Dir as it is not found !");
						SharedPreferences booleanTag = screen.getSharedPreferences( screen.getString( R.string.CHECK_FOR_VERSION), 0 ); // 0 - for private mode
						Editor editor1 = booleanTag.edit();
					    editor1.putBoolean("bValue", true); 
					    editor1.commit();
						dz.unzip( screen );
				}
				else
				{
					if(tagValue == false)
					{
						File f = new File("/data/data/com.spearheadinc.flashcards.omer/files");
						f.delete();
						System.out.println("Directory Deleted");
						SharedPreferences booleanTag = screen.getSharedPreferences( screen.getString( R.string.CHECK_FOR_VERSION), 0 ); // 0 - for private mode
						Editor editor1 = booleanTag.edit();
					    editor1.putBoolean("bValue", true); 
					    editor1.commit();
						dz.unzip( screen );
					}
						
				}
			}
		});

//        mHandler = new Handler();
//
//        // Try to use more data here. ANDROID_ID is a single point of attack.
//        String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
//
//        // Library calls this when it's done.
//        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
//        // Construct the LicenseChecker with a policy.
//        mChecker = new LicenseChecker(
//            this, new ServerManagedPolicy(this,
//                new AESObfuscator(SALT, getPackageName(), deviceId)),
//            BASE64_PUBLIC_KEY);
//        doCheck();
    }

	@Override
	protected void onDestroy() {
		if (mGpsLocationReceiver != null) {
			unregisterReceiver(mGpsLocationReceiver);
		}
		super.onDestroy();
	}
    

	
//    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm/oLnwPIJChJOlxmfeKDybfWoqMudWoqPAK/anYSf59frYtt43Svq96KQyP4s/0KC7FMWhK5aLgfrJOSSIUOtBRdqTMyf34gAYhSr9tcpPWqN2nj07m9Sp3bwUmQ35/N47n7a4IRtwQ+pKM30mwtPKlWDy3xujWnUVBdFTMsbYBuOnIf6TbfusdXhyxJMAn3eUEvaIbl/UYIUlpY8g8d/NBexDfK8T0L+I0UjA2Fim8CbbyBhCnvXfH1TkviEoiVeBo2pw7GM5j3IwcyZU0w7YY+XDr7xXROQdnJDdAo2IkZdpbiLZnldSvn9NIzyjxt8RJhElh0bcHgApSaUkancQIDAQAB";
//
//    // Generate your own 20 random bytes, and put them here.
//    private static final byte[] SALT = new byte[] {
//        -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
//        89
//    };
//
////    private TextView mStatusText;
////    private Button mCheckLicenseButton;
//
//    private LicenseCheckerCallback mLicenseCheckerCallback;
//    private LicenseChecker mChecker;
//    // A handler on the UI thread.
//    private Handler mHandler;
//
//
//    protected Dialog onCreateDialog(int id) {
//        // We have only one dialog.
//        return new AlertDialog.Builder(this)
//            .setTitle(R.string.unlicensed_dialog_title)
//            .setMessage(R.string.unlicensed_dialog_body)
//            .setPositiveButton(R.string.buy_button, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
//                        "http://market.android.com/details?id=" + getPackageName()));
//                    startActivity(marketIntent);
//                }
//            })
//            .setNegativeButton(R.string.quit_button, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            })
//            .create();
//    }
//
//    private void doCheck() {
////        mCheckLicenseButton.setEnabled(false);
//        setProgressBarIndeterminateVisibility(true);
////        mStatusText.setText(R.string.checking_license);
//        mChecker.checkAccess(mLicenseCheckerCallback);
//    }
//
//    private void displayResult(final String result) {
//        mHandler.post(new Runnable() {
//            public void run() {
////                mStatusText.setText(result);
//                setProgressBarIndeterminateVisibility(false);
////                mCheckLicenseButton.setEnabled(true);
//            }
//        });
//    }
//
//    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
//        public void allow() {
//            if (isFinishing()) {
//                // Don't update UI if Activity is finishing.
//                return;
//            }
//            // Should allow user access.
//            displayResult(getString(R.string.allow));
//        }
//
//        public void dontAllow() {
//            if (isFinishing()) {
//                // Don't update UI if Activity is finishing.
//                return;
//            }
//            displayResult(getString(R.string.dont_allow));
//            // Should not allow access. In most cases, the app should assume
//            // the user has access unless it encounters this. If it does,
//            // the app should inform the user of their unlicensed ways
//            // and then either shut down the app or limit the user to a
//            // restricted set of features.
//            // In this example, we show a dialog that takes the user to Market.
//            showDialog(0);
//        }
//
//        public void applicationError(ApplicationErrorCode errorCode) {
//            if (isFinishing()) {
//                // Don't update UI if Activity is finishing.
//                return;
//            }
//            // This is a polite way of saying the developer made a mistake
//            // while setting up or calling the license checker library.
//            // Please examine the error code and fix the error.
//            String result = String.format(getString(R.string.application_error), errorCode);
//            displayResult(result);
//        }
//    }
}

//This tip shows how to convert a RGB values of a color into hexadecimal version.
//import java.awt.*;
//
//public class Color2Hex {
//  public static void main( String[] args ) {
//    if (args.length != 3) {
//      System.out.println("Color2Hex  r g b");
//      }
//   else {
//      int i = Integer.parseInt(args[0]);
//      int j = Integer.parseInt(args[1]);
//      int k = Integer.parseInt(args[2]);
//    
//      Color c = new Color(i,j,k);
//      System.out.println
//        ( "hex: " + Integer.toHexString( c.getRGB() & 0x00ffffff ) ); 
//      }
//   }
//}

//http://www.shodor.org/stella2java/rgbint.html
//http://stackoverflow.com/questions/4129666/how-to-convert-hex-to-rgb
//http://www.go4expert.com/forums/showthread.php?t=5846