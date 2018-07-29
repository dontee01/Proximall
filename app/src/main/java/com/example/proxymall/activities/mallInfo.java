package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.example.proxymall.Imageloader.ImageLoader;
import com.example.proxymall.R;
import com.example.proxymall.adapters.Mall_info_CustomPagerAdapter;
import com.example.proxymall.adapters.ProductsGridViewAdapter;
import com.example.proxymall.adapters.goods_imfo_CustomPagerAdapter;
import com.example.proxymall.app.AppConfig;
import com.example.proxymall.helper.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.proxymall.app.AppConfig.TAG_ABOUT_MALL;
import static com.example.proxymall.app.AppConfig.TAG_BRIEF_DESCRIPTION;
import static com.example.proxymall.app.AppConfig.TAG_DISTANCE;
import static com.example.proxymall.app.AppConfig.TAG_ID;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE;
import static com.example.proxymall.app.AppConfig.TAG_IMAGE_NUMBERS;
import static com.example.proxymall.app.AppConfig.TAG_MALL_ID;
import static com.example.proxymall.app.AppConfig.TAG_MALL_NAME;
import static com.example.proxymall.app.AppConfig.TAG_NAME;
import static com.example.proxymall.app.AppConfig.TAG_PHONE_NUMBER;
import static com.example.proxymall.app.AppConfig.TAG_SPECIFICATION;

//import com.example.mykit_campus.Activities.AppUtilityClass;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Ravi on 29/07/15.
 */



public class mallInfo extends Activity {

    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;

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
    String name,aboutMall,phoneNumber;

    ImageLoader imageLoader = new ImageLoader(this);

//    private AdView mAdView;



    ProgressBar pDialog;
    // Creating JSON Parser object
//    JSONParser jParser = new JSONParser();




    ArrayList<HashMap<String, String>> advicesList;

    // url to get all products list
    private static String url_all_advices = "http://192.168.87.1/mykit_api/ask_advice.php";


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
    public static String TAG_NAME = "name";
    static String TAG_IMAGE_NUMBERS_USE = "Image_numbers";
    LinearLayout container;
    private ProgressBar AvailableGoodsProgress;
    ArrayList<HashMap<String, String>> productsList;

    Boolean ImageLoaded = false;

    public static String TAG_IMAGE="image";

    public static Mall_info_CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    TextView mallNameText,aboutUsTextView,itemsSoldTextView;
    Button messageButton,callButton;

    String mallId,mallImageNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_info);
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




        Intent i = getIntent();
        mallId = i.getStringExtra("mallId");
        mallImageNumber= i.getStringExtra("mallImageNumber");

//        Toast.makeText(mallInfo.this,mallId,Toast.LENGTH_SHORT).show();

        fetch_mall_images();
        fetchMallDetails();






//        mallLinkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(mallInfo.this, MallActivity.class);
//                intent.putExtra("image",TAG_MALL_IMAGE_display);
//                intent.putExtra("name",TAG_MALL_NAME_display);
//                startActivity(intent);
//                overridePendingTransition(R.anim.moving_in_side,
//                        R.anim.hold_animmation);
//            }
//        });


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < mallInfo.Imageurls.size(); i++) {
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


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Intent.ACTION_VIEW));
               // intent.setData(Uri.parse("smsto:" + TAG_SENDER_PHONE_NUMBER));
                intent.putExtra("address", phoneNumber);
               // intent.putExtra("sms_body", "I saw your Product("+TAG_NAME+") on Mykit and I am interested. So what's up?");
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);
            }
        });

















    }


    @Override
    public void onPause() {
        overridePendingTransition(R.anim.hold_animmation,
                R.anim.moving_out_side);

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {


        overridePendingTransition(R.anim.hold_animmation,
                R.anim.moving_out_side);
        super.onDestroy();
    }



    void init(){
        productsList = new ArrayList<HashMap<String, String>>();
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mallLinkButton = (Button) findViewById(R.id.mallLinkButton);
        mCustomPagerAdapter = new Mall_info_CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        advicesList = new ArrayList<HashMap<String, String>>();

        messageButton = (Button)findViewById(R.id.messageButton);
        callButton = (Button)findViewById(R.id.callButton);
        ImfoContainer = (LinearLayout)findViewById(R.id.imfoContainer);

        container =(LinearLayout)findViewById(R.id.goods_imfo_container);
         mallNameText = (TextView) findViewById(R.id.mallName);
         aboutUsTextView = (TextView) findViewById(R.id.aboutUs);
        itemsSoldTextView = (TextView) findViewById(R.id.itemsSold);










    }



        public void fetchMallDetails(){

        ParseQuery query= ParseQuery.getQuery("MallTable");
        query.whereEqualTo("objectId",mallId);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    //  mallsListInitial.clear();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {


                            ParseObject po = list.get(i);
                            String id = po.getObjectId().toString();
                             name= po.getString(TAG_NAME);
                             aboutMall = po.getString(TAG_ABOUT_MALL);
                              phoneNumber = po.getString(TAG_PHONE_NUMBER);







                        }

                        mallNameText.setText(name);
                        aboutUsTextView.setText(aboutMall);





                    }




                } else {
                    Log.d("score", "Error: " + e.getMessage());


                }

            }
        });



    }


    public void fetch_mall_images(){
        Imageurls= new ArrayList<>();
        final int image_numbers= Integer.parseInt(String.valueOf(mallImageNumber));
        ParseQuery query= ParseQuery.getQuery("MallTable");
        query.whereEqualTo("objectId", mallId);
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
                            mallImageNumber = "imageNumbers";
                            mallId = "id";


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
