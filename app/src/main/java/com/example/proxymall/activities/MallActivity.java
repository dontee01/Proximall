package com.example.proxymall.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proxymall.R;
import com.example.proxymall.adapters.ProductsGridViewAdapter;
import com.example.proxymall.app.AppConfig;
import com.example.proxymall.helper.ParseUtils;
import com.example.proxymall.helper.SessionManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.proxymall.activities.MainActivity.fetchCartUnits;
import static com.example.proxymall.app.AppConfig.TAG_CATEGORY;
import static com.example.proxymall.app.AppConfig.TAG_DISTANCE;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE_NUMBERS;
import static com.example.proxymall.app.AppConfig.TAG_MALL_ADDRESS;
import static com.example.proxymall.app.AppConfig.TAG_MALL_ID;
import static com.example.proxymall.app.AppConfig.TAG_MALL_IMAGE;
import static com.example.proxymall.app.AppConfig.TAG_MALL_NAME;
import static com.example.proxymall.app.AppConfig.TAG_NAME;
import static com.example.proxymall.app.AppConfig.TAG_PRICE;
import static com.example.proxymall.app.AppConfig.TAG_PRODUCT_ID;
import static com.example.proxymall.app.AppConfig.TAG_SPECIFICATION;

public class MallActivity extends Activity {

    LocationManager locationManager = null;
    boolean gps_enabled = false;
    boolean network_enabled = false;

    String name,image,mallId,mallImageNumber,imageUrl,mallImageUrl;
    GridView GridView;
    ImageView mallImage;
    ProgressBar ProgressBar;
    TextView nameTextView;
    Spinner filterSpinner;
    Button visitStore;
    TextView cartTextView;
    private SessionManager session;
    ImageView menuButton,cartButton,searchButton;
    ProductsGridViewAdapter productsAdapter;

    public static ArrayList<HashMap<String, String>> productsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_main_activities);

     //   getActionBar().hide();

        init();
        fetchCartUnitMethods();
        filterSpinnerMethod();
        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        name = intent.getStringExtra("name");
        mallId = intent.getStringExtra("mallId");
        mallImageNumber = intent.getStringExtra("imageNumber");

        Picasso.with(MallActivity.this)
                .load(image)
                .into(mallImage);

        nameTextView.setText(name);

        fetchGoods();
        spinnerAction();
        onClickListener();
    }
//    void onClickListeners(){
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(MallActivity.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    void fetchCartUnitMethods(){
        if (session.isLoggedIn()) {

            fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
    }
    void onClickListener(){
        mallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MallActivity.this,mallInfo.class);
                intent.putExtra("mallId",mallId);
                intent.putExtra("mallImageNumber",mallImageNumber);
                startActivity(intent);
            }
        });


        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.isLoggedIn()) {

                    Intent intent = new Intent(MallActivity.this,Cart.class);
                    startActivity(intent);


                }
                else if(!session.isLoggedIn()){


                    Toast.makeText(MallActivity.this,"Please, Login.",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    void spinnerAction(){
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fetchGoods();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        visitStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( locationManager == null ) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                }
                try {
                    gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex){}
                try {
                    network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex){}
                if ( gps_enabled && network_enabled){

                    startMaps();

                }else{

                    checkGPSStatus();
                }
            }
        });

    }





    void startMaps (){

		double latitutude = 6.5965;
		double longitude = 3.3421;



        Double latitudee= Double.parseDouble(String.valueOf(latitutude));
        Double longitudee= Double.parseDouble(String.valueOf(longitude));


//        Double latitudee= Double.parseDouble(String.valueOf(TAG_LATITUDE));
//        Double longitudee= Double.parseDouble(String.valueOf(TAG_LONGITUDE));



        String urr= String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",latitudee,longitudee,name);
        //String uri = String.format("geo:%s,$s", 40.714728f,-73.998672f);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urr));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try{
            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urr));
            startActivity(unrestrictedIntent);
        }
        catch (ActivityNotFoundException innerEx){
            // Toast.makeText(hostel_imfo_main.this, "Please install maps application", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkGPSStatus() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(MallActivity.this,"Please switch on your \nLocation Manager.",Toast.LENGTH_LONG).show();

    }


    void init(){
     //   groupButton = (Button)findViewById(groupButton);

        cartButton=(ImageView) findViewById(R.id.cartButton);
        cartTextView=(TextView) findViewById(R.id.cartTextView);
        session = new SessionManager(getApplicationContext());
        visitStore = (Button)findViewById(R.id.visitStore);
        productsList = new ArrayList<HashMap<String, String>>();
        mallImage=(ImageView)findViewById(R.id.mallImage);
        GridView=(GridView) findViewById(R.id.GridView);
        nameTextView=(TextView) findViewById(R.id.name);
        filterSpinner = (Spinner) findViewById(R.id.filterSpinner);
        ProgressBar=(ProgressBar) findViewById(R.id.ProgressBar);
    }
    private  void filterSpinnerMethod(){

       /* spinner.setOnItemSelectedListener(null);*/

        List<String> categories = new ArrayList<String>();
        categories.add("FILTER");
        categories.add("FASHION");
        categories.add("GROCERIES");

        ArrayAdapter<String> dataAdapter= new ArrayAdapter<String>(MallActivity.this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(dataAdapter);
    }
    public void fetchGoods() {

        ProgressBar.setVisibility(View.VISIBLE);
        //Imageurls= new ArrayList<>();
        //	imagess = new Str

        ParseQuery query = ParseQuery.getQuery("ProductTable");
//
//        if(from.equalsIgnoreCase("category")){
//            query.whereEqualTo("category",categoryString);
//        }
//        else{
//            query.whereEqualTo("mallId",from);
//        }

        query.include("mallId");
        query.whereEqualTo("mallId_string",mallId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    productsList.clear();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {

                            ParseObject po = list.get(i);
                            String id = po.getObjectId().toString();
                            String name = po.getString(TAG_NAME);
                            String category = po.getString(TAG_CATEGORY);
                            String price = String.valueOf(po.getInt(TAG_PRICE));
                            String distance = po.getString(AppConfig.TAG_DISTANCE);
                            String imageNumbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS));
                            String specification = po.getString(TAG_SPECIFICATION);




                            ParseObject queryy = po.getParseObject("mallId");// ParseObject("mallId");
                            String mallName = queryy.getString("name");
                            String mallId = queryy.getObjectId().toString();
                            String mallAddress = queryy.getString("address");
                            ParseFile MallImage = queryy.getParseFile("image1");
                            mallImageUrl = MallImage.getUrl();


                            //if(imageused.equals("yes")) {
                            ParseFile image = po.getParseFile("image1");
                            imageUrl = image.getUrl();
                            //  Imageurls.add(imageUrl);

                            //}


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();


                            map.put(TAG_NAME, name);
                            map.put(TAG_DISTANCE, distance);
                            map.put(TAG_PRICE, price);
                            map.put(TAG_MALL_ID, mallId);
                            map.put(TAG_PRODUCT_ID, id);
                            map.put(TAG_CATEGORY, category);
                            map.put(TAG_MALL_NAME, mallName);
                            map.put(TAG_MALL_ADDRESS, mallAddress);
                            map.put(TAG_SPECIFICATION, specification);
                            map.put(TAG_IMAGE_NUMBERS, imageNumbers);
                            map.put(TAG_MALL_IMAGE, mallImageUrl);

                            //map.put(TAG_IMAGE, imagePath);

                            //if(imageused.equals("yes")) {
                            map.put(TAG_IMAGE, imageUrl);

                            //	}


                            // adding HashList to ArrayList
                            productsList.add(map);
                        }
                        ProgressBar.setVisibility(View.GONE);
                        GridView.setVisibility(View.VISIBLE);
                        productsAdapter = new ProductsGridViewAdapter(MallActivity.this, productsList);
                        GridView.setAdapter(productsAdapter);
                        ParseUtils.setGridViewHeightBasedOnChildren(GridView);

                    }
                    else{
                        ProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MallActivity.this,"No Product in the Mall at the moment",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());

                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (session.isLoggedIn()) {

            fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (session.isLoggedIn()) {

            fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
    }
}
