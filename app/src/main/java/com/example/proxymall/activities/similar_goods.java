package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proxymall.Imageloader.ImageLoader;
import com.example.proxymall.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;


public class similar_goods extends Activity {
    TableLayout table;
    EditText coursefield;
    Button ask_question_button;
    String courseNum;
    int courseNo;
    JSONArray products = null;

    LinearLayout ImfoContainer;
    Button moreGoods;

    ImageLoader imageLoader = new ImageLoader(this);
    public static final String TAG_IMAGE_NUMBERS = "Image_numbers";




    ProgressBar pDialog;
    // Creating JSON Parser object
  //  JSONParser jParser = new JSONParser();
    public static final String TAG_AD_CATEGORY = "category";




    ArrayList<HashMap<String, String>> advicesList;

    // url to get all products list
    private static String url_all_advices = "http://192.168.87.1/mykit_api/ask_advice.php";


    static String TAG_AD_NAME = "name";
    static String TAG_AD_PRICE = "price";
    static String TAG_AD_SENDER_PHONE_NUMBER = "sender_phone_number";
    static String TAG_AD_BRIEF_DESCRIPTION = "brief_description";
    static String TAG_AD_SENDER_LOCATION = "sender_location";
    static String TAG_AD_DATE_POSTED = "Ad_date_posted";
    static String TAG_SENDER_PHONE_NUMBER = "sender_phone_number";
 //   private static final String TAG_PRODUCTS = "products";

    static String TAG_CATEGORY ;
    static String category;



    // JSON Node names
    private static final String TAG_SUCCESS = "success";
     static String TAG_TITLE= "title";
    static String TAG_NAME = "name";
    static String TAG_GOODS_ID = "Ad_id";
     static String TAG_DATE = "date";
    static String TAG_PRICE = "price";
    static String TAG_DESCRIPTION = "description";
    private static final String TAG_PRODUCTS = "products";
    public static final String TAG_CONDITION = "condition";
    public static final String TAG_BRIEF_DESCRIPTION = "Ad_brief_description";
    public static final String TAG_DATE_POSTED = "Ad_date_posted";
    static String TAG_SENDER_LOCATION = "sender_location";
    static String TAG_IMAGE_NUMBERS_USE = "Image_numbers";
    static String TAG_FLAG = "flag";
    LinearLayout container;
    private ProgressBar AvailableGoodsProgress;
    ArrayList<HashMap<String, String>> productsList;
    static String TAG_IMAGEUSED = "imageused";
    String imageUrl;






    public static final String TAG_ID = "id";
    public static final String TAG_IMAGE="image";

    GridView AvailableGoodsGridview;


    private static String url_get_all_similar_goods_after_goods_imfo = "http://192.168.87.1/mykit_api/similar_goods_after_goods_imfo.php";




   // private static ArrayList<AvailableGoods_Data_Model> listArrayList;






    private static final String TAG_ADVICES = "advices";
    private static final String TAG_SUBJECT = "subject";
    private static final String TAG_USERNAME = "username";

    private static final String TAG_QUESTION = "question";
    private static final String TAG_ADVICE_ID = "advice_id";



    JSONArray advices = null;
    Button sellerButton, messageButton;


    ImageView imgflag;



    Button internetButton,post_your_intention,post_your_goods;
    TextView tv;
    ListView showpage,askAdviceListview;
    Button shareButton;

    LinearLayout internetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_content_page);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setTitle(Html.fromHtml("<font color=\"#b9cde8\">Similar Goods</font>"));
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E095A")));



        getActionBar().hide();
        ImageButton backButton= (ImageButton)findViewById(R.id.backButton);
        TextView tv = (TextView)findViewById(R.id.title);
        tv.setText("Similar Goods");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                overridePendingTransition(R.anim.hold_animmation,
                        R.anim.abc_slide_out_bottom);
            }
        });



        productsList = new ArrayList<HashMap<String, String>>();

        advicesList = new ArrayList<HashMap<String, String>>();
      //  progressRelativeLayout=(RelativeLayout)findViewById(R.id.progressRelativeLayout);
      //  pDialog=(ProgressBar)findViewById(R.id.more_progress);
        AvailableGoodsProgress=(ProgressBar) findViewById(R.id.more_progress);
        AvailableGoodsGridview= (GridView) findViewById(R.id.listView);

        container =(LinearLayout)findViewById(R.id.container);



        Intent i = getIntent();
        category = i.getStringExtra("category");

        fetchSimilarProducts();




      //  internetLayout = (LinearLayout)findViewById(R.id.internetLayout);
//        Button internetTryAgainButton = (Button)findViewById(R.id.internetTryAgainButton);
//        internetTryAgainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                internetLayout.setVisibility(View.GONE);
//                AvailableGoodsProgress.setVisibility(View.VISIBLE);
//                fetchSimilarRequest();
//
//            }
//        });











    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.hold_animmation,
                R.anim.abc_slide_out_bottom);
    }


    public void fetchSimilarProducts(){
        //Imageurls= new ArrayList<>();
        //	imagess = new Str

//        TAG_IMAGE_NUMBERS_USE = "Image_numbers";
        ParseQuery query= ParseQuery.getQuery("Lautech_eCommerce_shopp");
        query.whereEqualTo("category", category);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    productsList.clear();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {

                            ParseObject po = list.get(i);
                            String id = po.getObjectId().toString();
                            String name = po.getString(TAG_AD_NAME);
                            String category = po.getString(TAG_AD_CATEGORY);
                            String description = po.getString(TAG_DESCRIPTION);
                            String price = po.getString(TAG_AD_PRICE);
                            String condition = po.getString(TAG_CONDITION);
                            String sender_phone_number = po.getString(TAG_SENDER_PHONE_NUMBER);
                            String date = po.getCreatedAt().toString();
                            String image_numbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS_USE));
                            String location = po.getString(TAG_SENDER_LOCATION);




                            //if(imageused.equals("yes")) {
                            ParseFile image = po.getParseFile("image0");
                            imageUrl = image.getUrl();
                            //Imageurls.add(imageUrl);

                            //}




                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put(TAG_ID,id);
                            map.put(TAG_NAME, name);
                            map.put(TAG_IMAGE_NUMBERS_USE, image_numbers);
                            map.put(TAG_AD_CATEGORY, category);
                            map.put(TAG_BRIEF_DESCRIPTION, description);
                            map.put(TAG_SENDER_PHONE_NUMBER, sender_phone_number);
                            map.put(TAG_PRICE, price);
                            map.put(TAG_CONDITION, condition);
                            map.put(TAG_DATE, date);
                            map.put(TAG_SENDER_LOCATION, location);
                            //map.put(TAG_IMAGE, imagePath);

                            //if(imageused.equals("yes")) {
                            map.put(TAG_IMAGE, imageUrl);

                            //	}



                            // adding HashList to ArrayList
                            productsList.add(map);
                        }
                        AvailableGoodsGridview.setVisibility(View.VISIBLE);
//                        AvailableGoodsadapter = new similar_goods_after_goods_imfoListViewAdapter(similar_goods_after_goods_imfo.this, productsList);
//                        AvailableGoodsGridview.setAdapter(AvailableGoodsadapter);
                        AvailableGoodsProgress.setVisibility(View.GONE);
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    Toast.makeText(similar_goods.this, "Check your Internet Connection", Toast.LENGTH_LONG).show();
                    AvailableGoodsProgress.setVisibility(View.GONE);

                }

            }
        });



    }


    public void fetchSimilarRequest(){

        ParseQuery query= ParseQuery.getQuery("Lautech_eCommerce_shopp");
        query.whereEqualTo("category",category);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        //	Toast.makeText(getActivity(),"request done",Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < list.size(); i++) {


                            ParseObject po = list.get(i);
                           // String id = po.getString(TAG_ID);
                            String name = po.getString(TAG_AD_NAME);
                            String price = String.valueOf(po.getString(TAG_AD_PRICE));
                            String categoryy = po.getString(TAG_AD_CATEGORY);
                            String date = po.getCreatedAt().toString();
                            String location = po.getString(TAG_AD_SENDER_LOCATION);
                           // String imagePath = po.getString(TAG_IMAGE);
                            String id = po.getObjectId().toString();
                            String image_numbers = po.getString(TAG_IMAGE_NUMBERS);
                            String brief_description = po.getString(TAG_AD_BRIEF_DESCRIPTION);
                            String sender_phone_Number = po.getString(TAG_AD_SENDER_PHONE_NUMBER);
                            String imageused = po.getString(TAG_IMAGEUSED);

                          //  if(imageused.equals("yes")) {
                                ParseFile image = po.getParseFile("image0");
                                imageUrl = image.getUrl();

                            //}


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                           // map.put(TAG_ID, id);
                            map.put(TAG_AD_NAME, name);
                            map.put(TAG_AD_PRICE, price);
                            map.put(TAG_AD_DATE_POSTED, date);
                            map.put(TAG_AD_SENDER_LOCATION, location);
                            map.put(TAG_AD_CATEGORY, categoryy);
                            map.put(TAG_AD_BRIEF_DESCRIPTION, brief_description);
                            map.put(TAG_AD_SENDER_PHONE_NUMBER, sender_phone_Number);
                            map.put(TAG_ID,id);
                            map.put(TAG_IMAGE_NUMBERS, image_numbers);
                          //  if(imageused.equals("yes")) {
                                map.put(TAG_IMAGE, imageUrl);

                           // }


                            // adding HashList to ArrayList
                            productsList.add(map);
                        }

                        AvailableGoodsGridview.setVisibility(View.VISIBLE);
//                        AvailableGoodsadapter = new similar_goods_after_goods_imfoListViewAdapter(similar_goods_after_goods_imfo.this, productsList);
//                        AvailableGoodsGridview.setAdapter(AvailableGoodsadapter);
                        AvailableGoodsProgress.setVisibility(View.GONE);
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    AvailableGoodsProgress.setVisibility(View.GONE);
                    AvailableGoodsGridview.setVisibility(View.GONE);
                    internetLayout.setVisibility(View.VISIBLE);

                }

            }
        });



    }
    class LoadSimilarGoods extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pDialog.setVisibility(View.VISIBLE);
        }


        /**
         * getting All
         * products from url
         * */
        protected String doInBackground(String... args) {



            // Loading products in Background Thread
//            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            // getting JSON string from URL
//            params.add(new BasicNameValuePair("category", TAG_CATEGORY));
//            JSONObject json = jParser.makeHttpRequest(url_get_all_similar_goods_after_goods_imfo, "GET", params);
//
//
//            // Check your log cat for JSON reponse
//            Log.d("All Products: ", json.toString());
//
//            try {
//                // Checking for SUCCESS TAG
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//                    // products found
//                    // Getting Array of Products
//                    products = json.getJSONArray(TAG_PRODUCTS);
//
//                    // looping through All Products
//                    for (int i = 0; i < products.length(); i++) {
//                        JSONObject c = products.getJSONObject(i);
//
//
//                        // Storing each json item in variable
//                        String id = c.getString(TAG_ID);
//                        String name = c.getString(TAG_AD_NAME);
//                        String price = c.getString(TAG_AD_PRICE);
//                        String category = c.getString(TAG_AD_CATEGORY);
//                      //  String date = c.getString(TAG_AD_DATE_POSTED);
//                        String location = c.getString(TAG_AD_SENDER_LOCATION);
//                        String imagePath=c.getString(TAG_IMAGE);
//                        String brief_description= c.getString(TAG_AD_BRIEF_DESCRIPTION);
//                        String sender_phone_Number=c.getString(TAG_AD_SENDER_PHONE_NUMBER);
//
//
//                        // creating new HashMap
//                        HashMap<String, String> map = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        map.put(TAG_ID, id);
//                        map.put(TAG_AD_NAME, name);
//                        map.put(TAG_AD_PRICE, price);
//                        //map.put(TAG_AD_DATE_POSTED, date);
//                        map.put(TAG_AD_SENDER_LOCATION, location);
//                        map.put(TAG_AD_CATEGORY, category);
//                        map.put(TAG_AD_BRIEF_DESCRIPTION, brief_description);
//                        map.put(TAG_AD_SENDER_PHONE_NUMBER, sender_phone_Number);
//                        map.put(TAG_IMAGE, imagePath);
//
//
//                        // adding HashList to ArrayList
//                        productsList.add(map);
//
//                    }
//                } else {
//                    // no products found
//                    // Launch Add New product Activity
//                    //Intent i = new Intent(getApplicationContext(),
//                    //		NewProductActivity.class);
//                    // Closing all previous activities
//                    //	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    //	startActivity(i);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//


            return null;
        }




        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            AvailableGoodsGridview.setVisibility(View.VISIBLE);

            similar_goods.this.runOnUiThread(new Runnable() {
                public void run() {


//                    AvailableGoodsGridview.setVisibility(View.VISIBLE);
//                    AvailableGoodsadapter = new similar_goods_after_goods_imfoListViewAdapter(similar_goods_after_goods_imfo.this, productsList);
//                    // Set the adapter to the ListView
//                    AvailableGoodsGridview.setAdapter(AvailableGoodsadapter);
                    AvailableGoodsProgress.setVisibility(View.GONE);
                   // moreGoods.setVisibility(View.VISIBLE);

                }
            });

        }



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }












//    class Load_All_Advices extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         * */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            //pDialog.setVisibility(View.VISIBLE);
//        }
//
//
//        /**
//         * getting All products from url
//         * */
//        protected String doInBackground(String... args) {
//
//
//
//            // Loading products in Background Thread
//            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            // getting JSON string from URL
//            JSONObject json = jParser.makeHttpRequest(url_all_advices, "GET", params);
//
//            // Check your log cat for JSON reponse
//            Log.d("All intentions ", json.toString());
//
//            try {
//                // Checking for SUCCESS TAG
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//
//
//                    // products found
//                    // Getting Array of intentions
//                    advices = json.getJSONArray(TAG_ADVICES);
//
//                    // looping through All Products
//                    for (int i = 0; i < advices.length(); i++) {
//                        JSONObject c = advices.getJSONObject(i);
//
//                        // Storing each json item in variable
//                        String title = c.getString(TAG_TITLE);
//                        String question = c.getString(TAG_QUESTION);
//                        String advice_id = c.getString(TAG_ADVICE_ID);
//                        String date = c.getString(TAG_DATE);
//                     //   String flag = c.getString(TAG_FLAG);
//
//
//
//
//                        // creating new HashMap
//                        HashMap<String, String> maps = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        maps.put(TAG_TITLE, title);
//                        maps.put(TAG_QUESTION, question);
//                        maps.put(TAG_DATE, date);
//                        maps.put(TAG_ADVICE_ID, advice_id);
//                        maps.put(TAG_FLAG, "http://192.168.6.1/mykit_api/uploads/yes.jpg");
//                     //   maps.put(TAG_LOCATION, location);
//
//
//                        // adding HashList to ArrayList
//                        advicesList.add(maps);
//                    }
//                } else {
//                    // no products found
//                    // Launch Add New product Activity
//                    //Intent i = new Intent(getApplicationContext(),
//                    //		NewProductActivity.class);
//                    // Closing all previous activities
//                    //	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    //	startActivity(i);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//
//
//            return null;
//        }
//
//
//
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         * **/
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog after getting all products
//        //    pDialog.setVisibility(View.GONE);
//            ask_question_button.setVisibility(View.VISIBLE);
//            askAdviceListview.setVisibility(View.VISIBLE);
//
//            // updating UI from Background Thread
//            UserPostedGoodsEdittingPage.this.runOnUiThread(new Runnable() {
//                public void run() {
//                    /**
//                     * Updating parsed JSON data into ListView
//                     * */
//
//
//
//                    adapter = new AskAdviceMainListViewAdapter(UserPostedGoodsEdittingPage.this, advicesList);
//                    // Set the adapter to the ListView
//                    askAdviceListview.setAdapter(adapter);
//                    // Close the progressdialog
//                    pDialog.setVisibility(View.GONE);
//
//                    //		adapter = new stephen.mytestapp.com.mrmath.Activities.AccommodationFile.adapter.ListViewAdapter(getActivity(), listArrayList);
//
//                    // set adapter over recyclerview
//                    //		AllHostels.setAdapter(adapter);
//                    //		adapter.notifyDataSetChanged();
//                }
//            });
//
//        }
//
//
//
//    }
}
