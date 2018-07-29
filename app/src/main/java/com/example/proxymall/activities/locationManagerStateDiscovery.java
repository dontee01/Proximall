package com.example.proxymall.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proxymall.R;
import com.example.proxymall.adapters.MainActivityMallAdapter;
import com.example.proxymall.app.AppConfig;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.proxymall.app.AppConfig.TAG_DISTANCE;
import static com.example.proxymall.app.AppConfig.TAG_ID;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE_NUMBERS;
import static com.example.proxymall.app.AppConfig.TAG_MALL_ID;
import static com.example.proxymall.app.AppConfig.TAG_NAME;

/**
 * Created by stephen on 5/8/2017.
 */

public class locationManagerStateDiscovery extends Activity {


    RippleBackground rippleBackground = null;
    LocationManager locationManager = null;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    String imageUrl;
    Switch locationSwitch;
    RelativeLayout locationSwitchLayout;
    int dialogControl = 0;
    TextView searchTextView;
    ProgressBar locationManagerProgressBar;
    public static ArrayList<HashMap<String, String>> mallsListInitial;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_manager_state_discovery);
        init();
//        locationManagerProgressBar.setVisibility(View.GONE);
        searchTextView.setVisibility(View.GONE);
        locationSwitchLayout.setVisibility(View.GONE);

            checkLocationStatus();
        locationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationSwitch.setChecked(true);
                onLocationManagerSwitchOn();
            }
        });

    }

    void init(){
        locationSwitch = (Switch) findViewById(R.id.locationSwitch);
        locationSwitchLayout = (RelativeLayout) findViewById(R.id.locationSwitchLayout);
     //   locationManagerProgressBar = (ProgressBar)findViewById(R.id.locationManagerProgressBar);
        searchTextView = (TextView)findViewById(R.id.searchTextView);
        mallsListInitial = new ArrayList<HashMap<String, String>>();
    }


    private void SwitchingOntheLocationManager() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(locationManagerStateDiscovery.this, "Please switch on your \nLocation Manager.", Toast.LENGTH_LONG).show();
    }

    private void checkLocationStatus() {

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (gps_enabled && network_enabled) {

           fetchMalls();

        } else {

            locationSwitchLayout.setVisibility(View.VISIBLE);
            locationSwitch.setVisibility(View.VISIBLE);
            locationSwitch.setChecked(false);
           // LocationManagerDialog();
        }
    }


    public void LocationManagerDialog() {

        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setMessage("You need to switch on your Location for ProxyMall to be able to search Stores around you.");


        al.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();


            }
        });
        al.setPositiveButton("Switch On", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialogControl=1;
                dialog.dismiss();
                onLocationManagerSwitchOn();

            }
        });
        AlertDialog alyes = al.create();
        alyes.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Window view = ((AlertDialog) dialog).getWindow();
                // view.setBackgroundDrawableResource(R.drawable.rounded_button);

                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setTypeface(Typeface.DEFAULT_BOLD);
                positiveButton.invalidate();

                Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setTypeface(Typeface.DEFAULT_BOLD);
                negativeButton.invalidate();
            }
        });
        alyes.show();


    }

    void animation(){

        rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.setVisibility(View.VISIBLE);
        rippleBackground.startRippleAnimation();
//        ImageView imageView=(ImageView)findViewById(R.id.centerImage);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rippleBackground.startRippleAnimation();
//            }
 //       });
    }

    public void fetchMalls(){
        animation();
        //locationManagerProgressBar.setVisibility(View.VISIBLE);
        searchTextView.setVisibility(View.VISIBLE);
        ParseQuery query= ParseQuery.getQuery("MallTable");

        // query.whereEqualTo("status","available");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    mallsListInitial.clear();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {


                            ParseObject po = list.get(i);
                            String id = po.getObjectId().toString();
                            String name = po.getString(TAG_NAME);
                            String distance = po.getString(AppConfig.TAG_DISTANCE);
                            String imageNumbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS));
                            String mallId = po.getObjectId().toString();



                            //if(imageused.equals("yes")) {
                            ParseFile image = po.getParseFile("image1");
                            imageUrl = image.getUrl();
                            //Imageurls.add(imageUrl);

                            //}




                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();


                            map.put(TAG_NAME, name);
                            map.put(TAG_DISTANCE, distance);
                            map.put(TAG_MALL_ID, mallId);
                            map.put(TAG_IMAGE_NUMBERS, imageNumbers);
                            map.put(TAG_ID, id);

                            //map.put(TAG_IMAGE, imagePath);

                            //if(imageused.equals("yes")) {
                            map.put(TAG_IMAGE, imageUrl);

                            //	}



                            // adding HashList to ArrayList
                            mallsListInitial.add(map);
                        }




                    }

                    rippleBackground.stopRippleAnimation();

                    Intent intent = new Intent(locationManagerStateDiscovery.this, MainActivity.class);
                  //  intent.putExtra("mallsListFromLocationSwitchPage",mallsListInitial);
                    startActivity(intent);
                    finish();

                } else {
                    Log.d("score", "Error: " + e.getMessage());


                }

            }
        });



    }
   void  onLocationManagerSwitchOn() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(locationManagerStateDiscovery.this,"Please switch on the \n   Location Manager.",Toast.LENGTH_LONG).show();

//        Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
//        intent.putExtra("enabled", true);
//        sendBroadcast(intent);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkLocationStatus();
    }
}
