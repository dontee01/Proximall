package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proxymall.R;
import com.example.proxymall.adapters.MainActivityMallAdapter;
import com.example.proxymall.app.AppConfig;
import com.example.proxymall.helper.ParseUtils;
import com.example.proxymall.helper.SessionManager;
import com.example.proxymall.model.GetMalls;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.proxymall.app.AppConfig.TAG_DISTANCE;
import static com.example.proxymall.app.AppConfig.TAG_ID;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE;
import static com.example.proxymall.app.AppConfig.TAG_NAME;

//import com.example.proxymall.model.MoviesResponse;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "30";
    MainActivityMallAdapter mallsAdapter;
    ListView MallListView;
    String imageUrl;
    ProgressBar mallProgressBar;
    List<GetMalls> mallList;
    private SessionManager session;
    ImageView menuButton,cartButton,searchButton;
    TextView cartTextView;
    MainActivityMallAdapter mallsadapter;

    public static ArrayList<HashMap<String, String>> mallsList;
    Button freshFoodsButton,drinksAndbeveragesButton,beautyHealthToiletriesButton,frozenFoodsButton,foodCupboardButton;
    Button menWearsButton,menShoesButton,menAccessoriesButton,womenWearsButton,womenShoesButton,womenAccessoriesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        if (session.isLoggedIn()) {

            fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
//        ParseQuery query =new ParseQuery("User");
//        ParseGeoPoint point = new ParseGeoPoint(30.0, -20.0);
//        ParseGeoPoint x = ParseGeoPoint.getCurrentLocationInBackground();
        //distanceInKilometersTo(point);
        onClickListener();

        //Intent intent = getIntent();
        //mallsList = intent.getStringArrayListExtra("mallsListFromLocationSwitchPage");
        categoriesButtonOnClickImplementation();
        fetchInitialMalls();


      // fetchMalls();


    }

//    void getMallsFromServer(){
//
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//
//        Call<List<GetMalls>> call = apiService.getMalls(30);
//        call.enqueue(new Callback<List<GetMalls>>() {
//            @Override
//            public void onResponse(Call<List<GetMalls>> call, Response<List<GetMalls>> response) {
//                int statusCode = response.code();
//                 mallList = response.body();
//
//                for(GetMalls malls : mallList)
//                {
//                    Log.d("CAT", malls.getName());
//                }
//
//                mallsAdapter = new GetMallAdapter(MainActivity.this, mallList);
//                MallGridView.setAdapter(mallsAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<GetMalls>> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
//    }

    void onClickListener(){

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.isLoggedIn()) {

                    Intent intent = new Intent(MainActivity.this,Cart.class);
                    startActivity(intent);


                }
                else if(!session.isLoggedIn()){


                    Toast.makeText(MainActivity.this,"Please, Login.",Toast.LENGTH_LONG).show();
                }

            }
        });


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Log In")){

                            if (session.isLoggedIn()) {


                                Toast.makeText(MainActivity.this,"You are already Logged in",Toast.LENGTH_SHORT).show();

                            }
                            else if(!session.isLoggedIn()){

                                Intent i = new Intent(MainActivity.this, Login.class);
                                startActivity(i);

                            }
                        }

                        else  if(item.getTitle().toString().equalsIgnoreCase("My Cart")) {
                            Intent intent = new Intent(MainActivity.this, Cart.class);
                            startActivity(intent);

                        }
                        Toast.makeText(getApplication(),"Item Clicked:"+item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    void categoriesButtonOnClickImplementation(){

        freshFoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category", "Fresh Foods");
                intent.putExtra("from", "category");
                startActivity(intent);
            }

        });

        drinksAndbeveragesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Beverages");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });

        frozenFoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Frozen Foods");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });

        beautyHealthToiletriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Beauty,Health and Toiletries");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });

        foodCupboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Food Cupboard");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });



        womenWearsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Women's Wears");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });

        womenShoesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Women's Shoes");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });


        womenAccessoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Women's Accessories");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });


        menAccessoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Men's Accessories");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });

        menWearsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Men's Wears");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });

        menShoesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
                intent.putExtra("category","Men's Shoes");
                intent.putExtra("from","category");
                startActivity(intent);
            }

        });
    }

    public void fetchInitialMalls(){
        mallProgressBar.setVisibility(View.GONE);
        MallListView.setVisibility(View.VISIBLE);
        mallsAdapter = new MainActivityMallAdapter(MainActivity.this, locationManagerStateDiscovery.mallsListInitial );
        MallListView.setAdapter(mallsAdapter);
        ParseUtils.setListViewHeightBasedOnChildren(MallListView);
    }

    public void fetchMalls(){


        mallProgressBar.setVisibility(View.VISIBLE);
        ParseQuery query= ParseQuery.getQuery("MallTable");

        // query.whereEqualTo("status","available");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    mallsList.clear();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {


                            ParseObject po = list.get(i);
                            String id = po.getObjectId().toString();
                            String name = po.getString(TAG_NAME);
                            String distance = po.getString(AppConfig.TAG_DISTANCE);



                            //if(imageused.equals("yes")) {
                            ParseFile image = po.getParseFile("image1");
                            imageUrl = image.getUrl();
                            //Imageurls.add(imageUrl);

                            //}




                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();


                            map.put(TAG_NAME, name);
                            map.put(TAG_DISTANCE, distance);
                            map.put(TAG_ID, id);

                            //map.put(TAG_IMAGE, imagePath);

                            //if(imageused.equals("yes")) {
                            map.put(TAG_IMAGE, imageUrl);

                            //	}



                            // adding HashList to ArrayList
                            mallsList.add(map);
                        }


                        mallProgressBar.setVisibility(View.GONE);
                        MallListView.setVisibility(View.VISIBLE);
                        mallsAdapter = new MainActivityMallAdapter(MainActivity.this, mallsList );
                        MallListView.setAdapter(mallsAdapter);
                        ParseUtils.setListViewHeightBasedOnChildren(MallListView);

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());


                }

            }
        });



    }

    public static void fetchCartUnits(final TextView cartTextView){


        ParseQuery query= ParseQuery.getQuery("CartTable");

        query.whereEqualTo("sender_id_string", ParseUser.getCurrentUser().getObjectId().toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    cartTextView.setText(String.valueOf(list.size()));


                } else {
                    Log.d("score", "Error: " + e.getMessage());


                }

            }
        });



    }

    void init(){


        cartButton=(ImageView) findViewById(R.id.cartButton);
        searchButton=(ImageView) findViewById(R.id.searchButton);
        cartTextView=(TextView) findViewById(R.id.cartTextView);
        session = new SessionManager(getApplicationContext());
        menuButton = (ImageView) findViewById(R.id.menuButton);
        mallsList = new ArrayList<HashMap<String, String>>();
        MallListView=(ListView) findViewById(R.id.MallGridView);
        freshFoodsButton=(Button)findViewById(R.id.freshFoods);
        drinksAndbeveragesButton=(Button)findViewById(R.id.drinksAndBeverages);
        beautyHealthToiletriesButton=(Button)findViewById(R.id.beautyHealthToiletries);
        frozenFoodsButton=(Button)findViewById(R.id.frozenFoods);
        foodCupboardButton=(Button)findViewById(R.id.foodCupboard);
        menWearsButton=(Button)findViewById(R.id.menWears);
        menShoesButton=(Button)findViewById(R.id.menShoes);
        menAccessoriesButton=(Button)findViewById(R.id.menAccessories);
        womenShoesButton=(Button)findViewById(R.id.womenShoes);
        womenWearsButton=(Button)findViewById(R.id.womenWears);
        womenAccessoriesButton=(Button)findViewById(R.id.womenAccessories);
        mallProgressBar=(ProgressBar)findViewById(R.id.mallProgressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id == R.id.WishList) {

            Intent intent = new Intent(MainActivity.this,Wishlist.class);
            startActivity(intent);
            return true;
        }

        else if(id == R.id.logIn) {

            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
