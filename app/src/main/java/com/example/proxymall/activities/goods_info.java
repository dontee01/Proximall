package com.example.proxymall.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.mykit_campus.Activities.AppUtilityClass;


import com.example.proxymall.Imageloader.ImageLoader;
import com.example.proxymall.R;
import com.example.proxymall.adapters.ProductsGridViewAdapter;
import com.example.proxymall.adapters.goods_imfo_CustomPagerAdapter;
import com.example.proxymall.app.AppConfig;
import com.example.proxymall.helper.ParseUtils;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.example.proxymall.helper.SessionManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.text.NumberFormat;
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

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Ravi on 29/07/15.
 */



public class goods_info extends Activity {

    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    String mallImageUrl;
    //private ViewPagerAdapter mAdapter;


    public static ArrayList<String> Imageurls;


    ProductsGridViewAdapter productsAdapter;

    TableLayout table;
    EditText coursefield;
    Button ask_question_button;
    String courseNum;
    int courseNo;
    JSONArray products = null;

    LinearLayout ImfoContainer;
    Button moreGoods,mallLinkButton;

    ImageLoader imageLoader = new ImageLoader(this);

//    private AdView mAdView;



  //  ProgressBar pDialog;
    // Creating JSON Parser object
//    JSONParser jParser = new JSONParser();




    ArrayList<HashMap<String, String>> advicesList;

    // url to get all products list
    private static String url_all_advices = "http://192.168.87.1/mykit_api/ask_advice.php";


    Button addCartButton,shareButton;
    ImageView cartButton;
    TextView cartTextView;
    static String TAG_AD_NAME = "name";
    static String TAG_AD_PRICE = "price";
    static String TAG_AD_DATE = "date";
    static String TAG_AD_SENDER_PHONE_NUMBER = "phone_number";
    static String TAG_AD_IMAGE = "image";
    static String TAG_AD_BRIEF_DESCRIPTION = "brief_description";
    static String TAG_AD_SENDER_LOCATION = "sender_location";
    static String TAG_AD_DATE_POSTED = "date_posted";
    static String TAG_AD_CATEGORY = "category";
    static String TAG_SENDER_PHONE_NUMBER = "sender_phone_number";
    String imageUrl;
 //   private static final String TAG_PRODUCTS = "products";



    // JSON Node names
    ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
     static String TAG_TITLE= "title";
    public static String TAG_NAME = "name";
    static String TAG_GOODS_ID = "id";
    static String TAG_IMAGE_NUMBERS = "imageNumber";
    static String TAG_IMAGE_NUMBERS_USE = "Image_numbers";
    static String TAG_IMAGE_NUMBERS_SIMILAR_GOODS = "image_numbers";
     static String TAG_DATE = "date";
    public static String TAG_PRICE = "price";
    public static String TAG_CATEGORY = "category";
    public static String TAG_CONDITION = "condition";
    static String TAG_DESCRIPTION = "description";
    private static final String TAG_PRODUCTS = "products";
    public static final String TAG_BRIEF_DESCRIPTION = "brief_description";
    public static final String TAG_DATE_POSTED = "Ad_date_posted";
    static String TAG_SENDER_LOCATION = "sender_location";
    static String TAG_IMAGEUSED = "imageused";
    static String TAG_FLAG = "flag";
    LinearLayout container;
    private ProgressBar AvailableGoodsProgress;
    ArrayList<HashMap<String, String>> productsList;

    Boolean ImageLoaded = false;

    ProgressBar SimilarGoodProgressBar;
    GridView SimilarGoodsGridView;

    private SessionManager session;





    public static final String TAG_ID = "id";
    public static String TAG_IMAGE="image";
    public static String TAG_AD_IMFO_IMAGE="image";

    public static goods_imfo_CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    GridView AvailableGoodsListview;
    TextView txtname,txtprice, txtcategory,txtmallName,txtdescription,txtmallAddress;
    String TAG_NAME_display,TAG_MALL_ADDRESS_display,TAG_PRODUCT_ID_display,TAG_MALL_ID_display,TAG_MALL_IMAGE_display,TAG_IMAGE_NUMBER_display,TAG_MALL_NAME_display,TAG_PRICE_display,TAG_CATEGORY_display,TAG_CONDITION_display;

    String TAG_DESCRIPTION_display,TAG_IMAGE_NUMBERS_display,TAG_SENDER_PHONE_NUMBER_display,TAG_SENDER_LOCATION_display,TAG_AD_IMFO_IMAGE_display;


    ProgressBar SimilarGoodsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_info);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setTitle(Html.fromHtml("<font color=\"#b9cde8\">" + "Goods Imfo" + "</font>"));
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E095A")));


//        getActionBar().hide();
//        ImageButton backButton= (ImageButton)findViewById(R.id.backButton);
//        TextView tv = (TextView)findViewById(R.id.title);
//        tv.setText("Goods Imfo");
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//                overridePendingTransition(R.anim.hold_animmation,
//                        R.anim.moving_out_side);
//            }
//        });



        init();

        fetchCartUnitMethods();



//        container =(LinearLayout)findViewById(R.id.container);
//        advert_imageView =(ImageView)findViewById(R.id.advert_imageView);
//        advert_name_textView =(TextView)findViewById(R.id.advert_name);
//        advert_page_progressBar =(ProgressBar)findViewById(R.id.advert_page_progressBar);


     //   AppUtilityClass.fetchAdvert(advert_imageView, advert_name_textView, advert_page_progressBar);



        Intent i = getIntent();
        TAG_NAME_display = i.getStringExtra("name");
        TAG_PRODUCT_ID_display = i.getStringExtra("productId");
        TAG_MALL_ID_display = i.getStringExtra("mallId");
        TAG_MALL_ADDRESS_display = i.getStringExtra("mallAddress");
        TAG_MALL_IMAGE_display = i.getStringExtra("mallImageUrl");
        //TAG_IMAGE_NUMBERS = i.getStringExtra("image_numbers");
        TAG_IMAGE_NUMBERS_USE = i.getStringExtra("image_numbers");
        TAG_PRICE_display=i.getStringExtra("price");
        TAG_MALL_NAME_display=i.getStringExtra("mallName");
        TAG_CATEGORY_display=i.getStringExtra("category");
        TAG_DESCRIPTION_display=i.getStringExtra("description");


        fetch_ads_images();
        fetchSimilarGoods();
        onClickListener();



        //  fetchSimilarProducts();


//      mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        mAdView.setVisibility(View.VISIBLE);
//        mAdView.loadAd(adRequest);





        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < goods_info.Imageurls.size(); i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));



//                switch (position) {
//                    case 0:
//                        radioGroup.check(R.id.radioButton);
//                        break;
//                    case 1:
//                        radioGroup.check(R.id.radioButton2);
//                        break;
//                    case 2:
//                        radioGroup.check(R.id.radioButton3);
//                        break;
    //            }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


//        sellerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + TAG_SENDER_PHONE_NUMBER));
//                startActivity(intent);
//            }
//        });
//
//        messageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent((Intent.ACTION_VIEW));
//               // intent.setData(Uri.parse("smsto:" + TAG_SENDER_PHONE_NUMBER));
//                intent.putExtra("address", TAG_SENDER_PHONE_NUMBER);
//                intent.putExtra("sms_body", "I saw your Product("+TAG_NAME+") on Mykit and I am interested. So what's up?");
//                intent.setType("vnd.android-dir/mms-sms");
//                startActivity(intent);
//            }
//        });


        txtname.setText(TAG_NAME_display);
        txtprice.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(TAG_PRICE_display))+"NGN");
//        txtcategory.setText(TAG_CATEGORY_display);
      //  txtcondition.setText(TAG_CONDITION_display);
        txtdescription.setText(TAG_DESCRIPTION_display);
        txtmallName.setText(TAG_MALL_NAME_display);
        txtmallAddress.setText("("+TAG_MALL_ADDRESS_display+")");
//        imageLoader.DisplayImage(TAG_AD_IMFO_IMAGE, imgflag);


       // new LoadAvailableGoods().execute();
//        imgflag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserPostedGoodsEdittingPage.this, ImageDisplay.class);
//                intent.putExtra("flag", TAG_AD_IMFO_IMAGE);
//                intent.putExtra("name", TAG_NAME);
//                startActivity(intent);
//
//                //  Toast.makeText(UserPostedGoodsEdittingPage.this,"clicked",Toast.LENGTH_SHORT).show();
//
//            }
//        });






//        Animation scaleAnimation = AnimationUtils.loadAnimation(
//                getApplicationContext(), R.anim.alpha_animation2);
//        container.startAnimation(scaleAnimation);


//        moreGoods.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(goods_info.this, similar_goods_after_goods_imfo.class);
//                intent.putExtra("category", TAG_CATEGORY_display);
//                startActivity(intent);
//                overridePendingTransition(R.anim.abc_slide_in_bottom,
//                        R.anim.hold_animmation);
//            }
//        });

//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Drawable mDrawable = imgflag.getDrawable();
//                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
//                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
//
//                Uri uri =Uri.parse(path);
//
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//                //sharingIntent.setType("text/plain");
//                sharingIntent.setType("image/jpeg");
//                sharingIntent.putExtra(Intent.EXTRA_STREAM,uri);
//               // sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Mykit");
//
//               sharingIntent.putExtra(Intent.EXTRA_TEXT,  TAG_NAME + " is up for sale at the rate of  N" + TAG_PRICE + " on Mykit.\n\nOpen Mykit App and search with" + TAG_NAME + ".\n \n Yet to Install? \n visit  www.palystore.com/mykit to Install for free.");
//
//                startActivity(Intent.createChooser(sharingIntent, "Share Image"));
//
//            }
//        });














    }

    void fetchCartUnitMethods(){
        if (session.isLoggedIn()) {

            fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
    }

    void onClickListener(){
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.isLoggedIn()) {

                    Intent intent = new Intent(goods_info.this,Cart.class);
                    startActivity(intent);


                }
                else if(!session.isLoggedIn()){


                    Toast.makeText(goods_info.this,"Please, Login.",Toast.LENGTH_LONG).show();
                }

            }
        });



        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.isLoggedIn()) {

                    addCart();


                }
                else if(!session.isLoggedIn()){

                    Toast.makeText(goods_info.this,"Please, you need to Login first.",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(goods_info.this, Login.class);
                    startActivity(i);

                }
            }
        });

        mallLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(goods_info.this, MallActivity.class);
                intent.putExtra("image",TAG_MALL_IMAGE_display);
                intent.putExtra("name",TAG_MALL_NAME_display);
                startActivity(intent);
                overridePendingTransition(R.anim.moving_in_side, R.anim.hold_animmation);
            }
        });
    }

    @Override
    public void onPause() {
        overridePendingTransition(R.anim.hold_animmation,
                R.anim.moving_out_side);

        if (session.isLoggedIn()) {

            MainActivity.fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (session.isLoggedIn()) {

            MainActivity.fetchCartUnits(cartTextView);


        }
        else if(!session.isLoggedIn()){


        }
    }

    @Override
    public void onDestroy() {


        overridePendingTransition(R.anim.hold_animmation,
                R.anim.moving_out_side);
        super.onDestroy();
    }

    void addCart(){
        pDialog = new ProgressDialog(goods_info.this);
        pDialog.setMessage("Adding Product to Cart...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        int unit= 2;


        ParseObject object = new ParseObject("CartTable");
        object.put("unit",unit);
        object.put("sender_id_string",ParseUser.getCurrentUser().getObjectId().toString());
        object.put("mall_id", ParseObject.createWithoutData("MallTable", TAG_MALL_ID_display));
        object.put("sender_id", ParseUser.getCurrentUser());
        object.put("product_id",ParseObject.createWithoutData("ProductTable", TAG_PRODUCT_ID_display));
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    pDialog.dismiss();
                    fetchCartUnitMethods();
                    Toast.makeText(goods_info.this,"Product Added to Cart",Toast.LENGTH_LONG).show();


                }
                else{

                    pDialog.dismiss();
                    Toast.makeText(goods_info.this,"Error",Toast.LENGTH_LONG).show();
                }

            }
        });



    }


    void init(){
        cartTextView = (TextView)findViewById(R.id.cartTextView);
        productsList = new ArrayList<HashMap<String, String>>();
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mallLinkButton = (Button) findViewById(R.id.mallLinkButton);
        addCartButton = (Button) findViewById(R.id.addCartButton);
        cartButton = (ImageView) findViewById(R.id.cartButton);
        mCustomPagerAdapter = new goods_imfo_CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        session = new SessionManager(getApplicationContext());

        advicesList = new ArrayList<HashMap<String, String>>();
        //  progressRelativeLayout=(RelativeLayout)findViewById(R.id.progressRelativeLayout);
//        pDialog=(ProgressBar)findViewById(R.id.more_progress);
        SimilarGoodProgressBar=(ProgressBar) findViewById(R.id.SimilarGoodProgressBar);
        SimilarGoodsGridView= (GridView)findViewById(R.id.SimillarGoodsGridView);

       // sellerButton = (Button)findViewById(R.id.sellerButton);
//        messageButton = (Button)findViewById(R.id.messageButton);
//        shareButton = (Button)findViewById(R.id.shareButton);
        moreGoods = (Button)findViewById(R.id.moreSimilarGoods);
        ImfoContainer = (LinearLayout)findViewById(R.id.imfoContainer);

        container =(LinearLayout)findViewById(R.id.goods_imfo_container);
         txtname = (TextView) findViewById(R.id.name);
         txtprice = (TextView) findViewById(R.id.price);
       //  txtcategory = (TextView) findViewById(R.id.category);
         txtmallName = (TextView) findViewById(R.id.mallName);
         txtdescription = (TextView) findViewById(R.id.brief_description);
          txtmallAddress = (TextView) findViewById(R.id.mallAddress);


//        imgflag = (ImageView) findViewById(R.id.flag);









    }



    public void fetch_ads_images(){
        Imageurls= new ArrayList<>();
        final int image_numbers= Integer.parseInt(String.valueOf(TAG_IMAGE_NUMBERS_USE));
        ParseQuery query= ParseQuery.getQuery("ProductTable");
        query.whereEqualTo("objectId", TAG_PRODUCT_ID_display);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {

                            ParseObject po = list.get(i);

                            for (int r = 1; r < image_numbers+1; r++) {
                                ParseFile image = po.getParseFile("image" +( r));
                                imageUrl = image.getUrl();
                                Imageurls.add(imageUrl);
                             //   Toast.makeText(goods_info.this, imageUrl, Toast.LENGTH_SHORT).show();

                            }

                            ImageLoaded = true;
                            mViewPager.setAdapter(mCustomPagerAdapter);
                            setUiPageViewController();
                          //  TAG_IMAGE_NUMBERS = "imageNumbers";
                          //  TAG_PRODUCT_ID_display = "id";


                        }

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
//					pDialog.setVisibility(View.GONE);

                }

            }
        });


        ImageLoaded=false;

    }

    public void fetchSimilarGoods() {

        SimilarGoodProgressBar.setVisibility(View.VISIBLE);
        //Imageurls= new ArrayList<>();
        //	imagess = new Str

        ParseQuery query = ParseQuery.getQuery("ProductTable");
        query.include("mallId");
        query.whereEqualTo("category",TAG_CATEGORY_display);
        query.whereNotEqualTo("name",TAG_NAME_display);

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
                            String specification = po.getString(AppConfig.TAG_SPECIFICATION);
                            String imageNumbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS));
                            String category = po.getString(AppConfig.TAG_CATEGORY);




                            ParseObject queryy = po.getParseObject("mallId");// ParseObject("mallId");
                            String mallName = queryy.getString("name");
                            String mallAddress = queryy.getString("address");
                            ParseFile MallImage = queryy.getParseFile("image1");
                            String mallId = queryy.getObjectId().toString();
                            mallImageUrl = MallImage.getUrl();

                            //if(imageused.equals("yes")) {
                            ParseFile image = po.getParseFile("image1");
                            imageUrl = image.getUrl();
                            //  Imageurls.add(imageUrl);

                            //}


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();


                            map.put(TAG_NAME, name);
                            map.put(TAG_IMAGE_NUMBERS, imageNumbers);
                            map.put(TAG_DISTANCE, distance);
                            map.put(TAG_MALL_NAME, mallName);
                            map.put(TAG_SPECIFICATION, specification);
                            map.put(TAG_CATEGORY, category);
                            map.put(TAG_PRICE, price);

                            map.put(TAG_MALL_ID, mallId);
                            map.put(TAG_MALL_ADDRESS, mallAddress);
                            map.put(TAG_MALL_IMAGE, mallImageUrl);
                            map.put(TAG_PRODUCT_ID, id);
                            map.put(TAG_MALL_ID, mallId);

                            //map.put(TAG_IMAGE, imagePath);

                            //if(imageused.equals("yes")) {
                            map.put(TAG_IMAGE, imageUrl);




                            // adding HashList to ArrayList
                            productsList.add(map);
                        }

                        SimilarGoodProgressBar.setVisibility(View.GONE);
                        SimilarGoodsGridView.setVisibility(View.VISIBLE);
                        productsAdapter = new ProductsGridViewAdapter(goods_info.this, productsList);
                        SimilarGoodsGridView.setAdapter(productsAdapter);
                        ParseUtils.setGridViewHeightBasedOnChildren(SimilarGoodsGridView);
//                        AvailableGoodsProgress.setVisibility(View.GONE);
//                        moreGoods.setVisibility(View.VISIBLE);

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());

                }

            }
        });
    }


    public void fetchSimilarProducts(){
        //Imageurls= new ArrayList<>();
        //	imagess = new Str

        TAG_IMAGE_NUMBERS_USE = "Image_numbers";
        ParseQuery query= ParseQuery.getQuery("ProductTable");
      //  query.whereEqualTo("category", TAG_CATEGORY_display);
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
                            String description = po.getString(TAG_BRIEF_DESCRIPTION);
                            String price = po.getString(TAG_AD_PRICE);
                            String condition = po.getString(TAG_CONDITION);
                            String sender_phone_number = po.getString(TAG_SENDER_PHONE_NUMBER);
                            String date = po.getCreatedAt().toString();
                            String image_numbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS_USE));
                            String location = po.getString(TAG_SENDER_LOCATION);
                            String imagePath=po.getString(TAG_IMAGE);

                            String imageused = po.getString(TAG_IMAGEUSED);



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
                        AvailableGoodsListview.setVisibility(View.VISIBLE);

//                        AvailableGoodsadapter = new goods_imfo_similiar_goodsListViewAdapter(goods_info.this, productsList);
//                        AvailableGoodsListview.setAdapter(AvailableGoodsadapter);
                        ParseUtils.setGridViewHeightBasedOnChildren(AvailableGoodsListview);
                        AvailableGoodsProgress.setVisibility(View.GONE);
                        moreGoods.setVisibility(View.VISIBLE);

                    }else{
                        moreGoods.setVisibility(View.GONE);
                        Toast.makeText(goods_info.this, "No Similar Goods For now", Toast.LENGTH_LONG).show();
                        moreGoods.setVisibility(View.GONE);

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    Toast.makeText(goods_info.this, "Check your Internet Connection", Toast.LENGTH_LONG).show();
                    AvailableGoodsProgress.setVisibility(View.GONE);

                }

            }
        });



    }


//    public void fetchSimilarProductssss(){
//
//        ParseQuery query= ParseQuery.getQuery("Lautech_eCommerce_shopp");
//         query.whereEqualTo("category", TAG_CATEGORY);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    if (list.size() > 0) {
//                        for (int i = 0; i < list.size(); i++) {
//
//                            ParseObject po = list.get(i);
//                            String id = po.getObjectId().toString();
//                            String name = po.getString(TAG_AD_NAME);
//                            String image_number_similar_listview = po.getString(TAG_IMAGE_NUMBERS_SIMILAR_LISTVIEW);
//                            String category = po.getString(TAG_AD_CATEGORY);
//                            String description = po.getString(TAG_AD_BRIEF_DESCRIPTION);
//                            String price = String.valueOf(po.getInt(TAG_AD_PRICE));
//                            String image_numbers = String.valueOf(po.getInt(TAG_IMAGE_NUMBERS));
//                            //     String sender_phone_number = po.getString(TAG_AD_SENDER_PHONE_NUMBER);
//                            String date = po.getCreatedAt().toString();
//                            String location = po.getString(TAG_AD_SENDER_LOCATION);
//                            String imagePath = po.getString(TAG_AD_IMAGE);
//
//                            String imageused = po.getString(TAG_IMAGEUSED);
//
//
//                            //   if(imageused.equals("yes")) {
//                            ParseFile image = po.getParseFile("image0");
//                            imageUrl = image.getUrl();
//
//                            //  }
//
//                            //     Toast.makeText(UserPostedGoodsEdittingPage.this,id+"/"+image_number_similar_listview,Toast.LENGTH_LONG).show();
//
//
//                            // creating new HashMap
//                            HashMap<String, String> map = new HashMap<String, String>();
//
//                            map.put(TAG_AD_NAME, name);
//                            map.put(TAG_ID, id);
//                            map.put(TAG_IMAGE_NUMBERS_SIMILAR_LISTVIEW, image_number_similar_listview);
//                            map.put(TAG_AD_CATEGORY, category);
//                            map.put(TAG_AD_BRIEF_DESCRIPTION, description);
//                            //  map.put(TAG_AD_SENDER_PHONE_NUMBER, sender_phone_number);
//                            map.put(TAG_AD_PRICE, price);
//                            map.put(TAG_AD_DATE, date);
//                            map.put(TAG_IMAGE_NUMBERS, image_numbers);
//                            map.put(TAG_AD_SENDER_LOCATION, location);
//                            //map.put(TAG_IMAGE, imagePath);
//
//                            //  if(imageused.equals("yes")) {
//                            map.put(TAG_AD_IMAGE, imageUrl);
//
//                            // }
//
//
//                            // adding HashList to ArrayList
//                            productsList.add(map);
//                        }
//                        AvailableGoodsListview.setVisibility(View.VISIBLE);
//
//                        AvailableGoodsadapter = new goods_imfo_similiar_goodsListViewAdapter(goods_info.this, productsList);
//                        AvailableGoodsListview.setAdapter(AvailableGoodsadapter);
//                        ParseUtils.setGridViewHeightBasedOnChildren(AvailableGoodsListview);
//                        AvailableGoodsProgress.setVisibility(View.GONE);
//                        moreGoods.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                    Toast.makeText(goods_info.this, "Check your Internet Connection", Toast.LENGTH_LONG).show();
//                    AvailableGoodsProgress.setVisibility(View.GONE);
//
//                }
//
//            }
//        });
//
//
//
//    }








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














    private void setUiPageViewController() {

        dotsCount = mCustomPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

      dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }





}
