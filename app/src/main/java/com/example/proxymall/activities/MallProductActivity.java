//package com.example.proxymall.activities;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.GridView;
//import android.widget.ImageView;
//
//import com.example.proxymall.R;
//import com.example.proxymall.adapters.ProductsGridViewAdapter;
//import com.example.proxymall.app.AppConfig;
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import static com.example.proxymall.app.AppConfig.TAG_DISTANCE;
//import static com.example.proxymall.app.AppConfig.TAG_IMAGE;
//import static com.example.proxymall.app.AppConfig.TAG_NAME;
//
//public class MallProductActivity extends Activity {
//
//    public static ArrayList<HashMap<String, String>> productsList;
//    ImageView categories_imageView;
//    GridView categoryGridView;
//    ProductsGridViewAdapter productsAdapter;
//    String categoryString,imageUrl;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.categories_main_activities);
//
//        getActionBar().hide();
//        init();
//        Intent intent = getIntent();
//        categoryString = intent.getStringExtra("category");
//        categoriesImageDisplay();
//
//
//    }
//    void init(){
//        productsList = new ArrayList<HashMap<String, String>>();
//        categories_imageView=(ImageView)findViewById(R.id.categories_imageView);
//        categoryGridView=(GridView) findViewById(R.id.categoryGridView);
//    }
//    public void fetchGoodsInCategory() {
//        //Imageurls= new ArrayList<>();
//        //	imagess = new Str
//
//        ParseQuery query = ParseQuery.getQuery("productTable");
//        query.whereEqualTo("category",categoryString);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    productsList.clear();
//                    if (list.size() > 0) {
//                        for (int i = 0; i < list.size(); i++) {
//
//                            ParseObject po = list.get(i);
//                            String id = po.getObjectId().toString();
//                            String name = po.getString(TAG_NAME);
//                            String distance = po.getString(AppConfig.TAG_DISTANCE);
//
//
//                            //if(imageused.equals("yes")) {
////                            ParseFile image = po.getParseFile("image0");
////                            imageUrl = image.getUrl();
//                            //Imageurls.add(imageUrl);
//
//                            //}
//
//
//                            // creating new HashMap
//                            HashMap<String, String> map = new HashMap<String, String>();
//
//
//                            map.put(TAG_NAME, name);
//                            map.put(TAG_DISTANCE, distance);
//
//                            //map.put(TAG_IMAGE, imagePath);
//
//                            //if(imageused.equals("yes")) {
//                            map.put(TAG_IMAGE, imageUrl);
//
//                            //	}
//
//
//                            // adding HashList to ArrayList
//                            productsList.add(map);
//                        }
//                        //   showpage.setVisibility(getView().VISIBLE);
//                        //searchProducts.setVisibility(View.VISIBLE);
//
//                        productsAdapter = new ProductsGridViewAdapter(MallProductActivity.this, productsList);
//                        categoryGridView.setAdapter(productsAdapter);
////                        searchProducts.setVisibility(View.VISIBLE);
////                        lineCommerceShow.setVisibility(View.VISIBLE);
////                        pDialog.setVisibility(View.GONE);
//                    }
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
////                    internetLayout.setVisibility(View.VISIBLE);
////                    showpage.setVisibility(View.VISIBLE);
////                    pDialog.setVisibility(View.GONE);
//
//                }
//
//            }
//        });
//    }
//
//    void categoriesImageDisplay(){
////        if(categoryString.equalsIgnoreCase("phone")){
////
////            categories_imageView.setImageResource(R.drawable.phone);
////        }else if(categoryString.equalsIgnoreCase("groceries")){
////
////            categories_imageView.setImageResource(R.drawable.groceries);
////        }
////        else if(categoryString.equalsIgnoreCase("electronics")){
////
////            categories_imageView.setImageResource(R.drawable.electronics);
////        }
////        else if(categoryString.equalsIgnoreCase("fashion")){
////
////            categories_imageView.setImageResource(R.drawable.fashion);
////        }
////        else if(categoryString.equalsIgnoreCase("kitchen")){
////            categories_imageView.setImageResource(R.drawable.kitchen);
////
////        }
////        else if(categoryString.equalsIgnoreCase("services")){
////            categories_imageView.setImageResource(R.drawable.service);
////
////        }
//    }
//
//}
