package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proxymall.R;
import com.example.proxymall.adapters.ProductsGridViewAdapter;
import com.example.proxymall.adapters.ProductsListViewAdapter;
import com.example.proxymall.app.AppConfig;
import com.example.proxymall.helper.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

public class CategoryProductActivity extends Activity {

    public static ArrayList<HashMap<String, String>> productsList;
    ImageView categories_imageView;
    GridView categoryGridView;
    ListView categoryListView;
    Spinner sortSpinner;
    ProductsGridViewAdapter productsGridViewAdapter;
    ProductsListViewAdapter productsListViewAdapter;
    String image,categoryString,from,imageUrl,mallImageUrl;
    Button groupButton;
    ImageView backButton,viewSwitch;
    ProgressBar categoryProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_main_activities);
//        getActionBar().hide();

        init();
        filterSpinnerMethod();
        Intent intent = getIntent();
        categoryString = intent.getStringExtra("category");
        image = intent.getStringExtra("image");
        from = intent.getStringExtra("from");
        groupButton.setText(categoryString);
        onClickListeners();
        spinnerAction();
      //  categoriesImageDisplay();
        fetchGoodsInCategory();


    }
    void onClickListeners(){
        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CategoryProductActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                implementViewSwitch();
            }
        });
    }

    void implementViewSwitch(){

        if(categoryGridView.isShown()){
            categoryGridView.setVisibility(View.GONE);
            categoryListView.setVisibility(View.VISIBLE);
            productsListViewAdapter = new ProductsListViewAdapter(CategoryProductActivity.this, productsList);
            categoryListView.setAdapter(productsListViewAdapter);
            // ParseUtils.setGridViewHeightBasedOnChildren(productsListViewAdapter);

        }
        else if(categoryListView.isShown()){

            categoryListView.setVisibility(View.GONE);
            categoryGridView.setVisibility(View.VISIBLE);
            productsGridViewAdapter = new ProductsGridViewAdapter(CategoryProductActivity.this, productsList);
            categoryGridView.setAdapter(productsGridViewAdapter);
            ParseUtils.setGridViewHeightBasedOnChildren(categoryGridView);
        }
    }

    void spinnerAction() {
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String spinnerString = sortSpinner.getSelectedItem().toString();

                if(spinnerString.equalsIgnoreCase("sort")){

                }
                else{
                    fetchGoodsInCategory();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    void init(){
        sortSpinner = (Spinner)findViewById(R.id.sortSpinner);
        backButton = (ImageView)findViewById(R.id.backButton);
        viewSwitch = (ImageView)findViewById(R.id.viewSwitch);
        groupButton = (Button)findViewById(R.id.groupButton);
        productsList = new ArrayList<HashMap<String, String>>();
        categories_imageView=(ImageView)findViewById(R.id.categories_imageView);
        categoryGridView=(GridView) findViewById(R.id.categoryGridView);
        categoryListView=(ListView) findViewById(R.id.categoryListView);
        categoryProgressBar=(ProgressBar) findViewById(R.id.categoryProgressBar);
    }
    public void fetchGoodsInCategory() {

        categoryProgressBar.setVisibility(View.VISIBLE);
        String spinnerString = sortSpinner.getSelectedItem().toString();

        ParseQuery query = ParseQuery.getQuery("ProductTable");

        if(spinnerString.equalsIgnoreCase("sort")){

        }
        else if(spinnerString.equalsIgnoreCase("price- accending")){
            query.addAscendingOrder("price");
        }
        else if(spinnerString.equalsIgnoreCase("price- decending")){
            query.addDescendingOrder("price");
        }


        query.include("mallId");
        query.whereEqualTo("category",categoryString);

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
                            String price = String.valueOf(po.getInt(TAG_PRICE));
                            String distance = po.getString(AppConfig.TAG_DISTANCE);
                            String specification = po.getString(TAG_SPECIFICATION);
                            String category = po.getString(TAG_CATEGORY);
                            String imageNumbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS));



                            //if(imageused.equals("yes")) {
                            ParseFile image = po.getParseFile("image1");
                            imageUrl = image.getUrl();
                          //  Imageurls.add(imageUrl);

                            //}


                            ParseObject queryy = po.getParseObject("mallId");// ParseObject("mallId");
                            String mallName = queryy.getString("name");
                            String mallAddress = queryy.getString("address");
                            ParseFile MallImage = queryy.getParseFile("image1");
                            String mallId = queryy.getObjectId().toString();
                            mallImageUrl = MallImage.getUrl();





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
                        categoryProgressBar.setVisibility(View.GONE);
                        categoryGridView.setVisibility(View.VISIBLE);
                        productsGridViewAdapter = new ProductsGridViewAdapter(CategoryProductActivity.this, productsList);
                        categoryGridView.setAdapter(productsGridViewAdapter);
                        ParseUtils.setGridViewHeightBasedOnChildren(categoryGridView);

                    }
                    else{
                        categoryProgressBar.setVisibility(View.GONE);
                        Toast.makeText(CategoryProductActivity.this,"No Item in this category",Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());

                }

            }
        });
    }

    private  void filterSpinnerMethod(){

       /* spinner.setOnItemSelectedListener(null);*/

        List<String> categories = new ArrayList<String>();
        categories.add("sort");
        categories.add("Price- accending");
        categories.add("price- decending");

        ArrayAdapter<String> dataAdapter= new ArrayAdapter<String>(CategoryProductActivity.this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(dataAdapter);
    }

    void categoriesImageDisplay(){


        if(categoryString.equalsIgnoreCase("beverages")){

            categories_imageView.setImageResource(R.drawable.beverages);
        }else if(categoryString.equalsIgnoreCase("dryAndBaking")){

            categories_imageView.setImageResource(R.drawable.dry_and_baking);
        }
        else if(categoryString.equalsIgnoreCase("frozen")){

            categories_imageView.setImageResource(R.drawable.frozen_foods);
        }
        else if(categoryString.equalsIgnoreCase("meat")){

            categories_imageView.setImageResource(R.drawable.meat);
        }
        else if(categoryString.equalsIgnoreCase("menCloths")){
            categories_imageView.setImageResource(R.drawable.men_cloth);

        }
        else if(categoryString.equalsIgnoreCase("menBags")){
            categories_imageView.setImageResource(R.drawable.women_bag);

        }


        else if(categoryString.equalsIgnoreCase("menShoes")){

            categories_imageView.setImageResource(R.drawable.men_shoe);
        }
        else if(categoryString.equalsIgnoreCase("womenCloths")){

            categories_imageView.setImageResource(R.drawable.women_cloth);
        }
        else if(categoryString.equalsIgnoreCase("womenBags")){

            categories_imageView.setImageResource(R.drawable.women_bag);
        }
        else if(categoryString.equalsIgnoreCase("womenShoes")){
            categories_imageView.setImageResource(R.drawable.women_shoe);

        }

        else {
            categories_imageView.setImageResource(R.drawable.women_shoe);

        }


    }

}
