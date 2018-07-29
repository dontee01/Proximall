package com.example.proxymall.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proxymall.R;
import com.example.proxymall.adapters.CartProductsGridViewAdapter;
import com.example.proxymall.adapters.ProductsGridViewAdapter;
import com.example.proxymall.app.AppConfig;
import com.example.proxymall.helper.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

/**
 * Created by stephen on 5/14/2017.
 */

public class Cart extends AppCompatActivity {

    String imageUrl,mallImageUrl;
    ProgressBar cartProgressBar;
    ListView cartListView;
    Button checkOutButton;
    LinearLayout checkOutLayout;
    CartProductsGridViewAdapter cartProductsGridViewAdapter;
    public static ArrayList<HashMap<String, String>> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        init();
        fetchCart();

    }
    void init(){
        productsList = new ArrayList<HashMap<String, String>>();
        checkOutLayout = (LinearLayout)findViewById(R.id.checkOutLayout);
        cartProgressBar = (ProgressBar)findViewById(R.id.cartProgressBar);
        cartListView = (ListView)findViewById(R.id.cartListView);
        checkOutButton = (Button)findViewById(R.id.checkOutButton);

    }

    public void fetchCart() {

        //categoryProgressBar.setVisibility(View.VISIBLE);
        //String spinnerString = sortSpinner.getSelectedItem().toString();

        ParseQuery query = ParseQuery.getQuery("CartTable");

//        if(spinnerString.equalsIgnoreCase("sort")){
//
//        }
//        else if(spinnerString.equalsIgnoreCase("price- accending")){
//            query.addAscendingOrder("price");
//        }
//        else if(spinnerString.equalsIgnoreCase("price- decending")){
//            query.addDescendingOrder("price");
//        }


        query.include("mall_id");
        query.include("sender_id");
        query.include("product_id");
        query.whereEqualTo("sender_id_string", ParseUser.getCurrentUser().getObjectId().toString());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    productsList.clear();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {

                            ParseObject po = list.get(i);
                            String unit = po.getString("unit");





                            ParseObject productObject = po.getParseObject("product_id");
                            String name = productObject.getString(TAG_NAME);
                            String price = String.valueOf(productObject.getInt(TAG_PRICE));
                            String specification = productObject.getString(TAG_SPECIFICATION);
                            String category = productObject.getString(TAG_CATEGORY);
                            String id = productObject.getObjectId().toString();

                            String imageNumbers = String.valueOf(productObject.getInt(TAG_IMAGE_NUMBERS));
                            //if(imageused.equals("yes")) {
                            ParseFile image = productObject.getParseFile("image1");
                            imageUrl = image.getUrl();
                            //  Imageurls.add(imageUrl);

                            //}

                            ParseObject mallObject = po.getParseObject("mall_id");// ParseObject("mallId");
                            String mallName = mallObject.getString("name");
                            String mallAddress = mallObject.getString("address");
                            ParseFile MallImage = mallObject.getParseFile("image1");
                            String mallId = mallObject.getObjectId().toString();
                            mallImageUrl = MallImage.getUrl();

                            String distance = po.getString(AppConfig.TAG_DISTANCE);



                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();


                            map.put(TAG_NAME, name);
                            map.put(TAG_DISTANCE, distance);
                            map.put(TAG_PRICE, price);
                            map.put(TAG_CATEGORY, category);
                            map.put(TAG_MALL_NAME, mallName);
                            map.put(TAG_MALL_ADDRESS, mallAddress);
                            map.put(TAG_MALL_IMAGE, mallImageUrl);
                            map.put(TAG_PRODUCT_ID, id);
                            map.put(TAG_MALL_ID, mallId);
                            map.put(TAG_SPECIFICATION, specification);
                            map.put(TAG_IMAGE_NUMBERS, imageNumbers);

                            //map.put(TAG_IMAGE, imagePath);

                            //if(imageused.equals("yes")) {
                            map.put(TAG_IMAGE, imageUrl);

                            //	}


                            // adding HashList to ArrayList
                            productsList.add(map);
                        }
                        cartProgressBar.setVisibility(View.GONE);
                        cartListView.setVisibility(View.VISIBLE);
                        checkOutLayout.setVisibility(View.VISIBLE);
                        cartProductsGridViewAdapter = new CartProductsGridViewAdapter(Cart.this, productsList);
                        cartListView.setAdapter(cartProductsGridViewAdapter);
                       // ParseUtils.setGridViewHeightBasedOnChildren(cartListView);

                    }
                } else {
                    Toast.makeText(Cart.this,"Error", Toast.LENGTH_SHORT).show();
                    Log.d("score", "Error: " + e.getMessage());

                }

            }
        });
    }

}
