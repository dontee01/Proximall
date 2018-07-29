package com.example.proxymall.helper;

/**
 * Created by stephen on 11/8/2016.
 */


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proxymall.app.AppConfig;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import com.example.proxymall.app.AppConfig;

/**
 * Created by Ravi on 01/06/15.
 */
public class ParseUtils {

    private static String TAG = ParseUtils.class.getSimpleName();

    public static void verifyParseConfiguration(Context context) {
        if (TextUtils.isEmpty(AppConfig.PARSE_APPLICATION_ID) || TextUtils.isEmpty(AppConfig.PARSE_CLIENT_KEY)) {
            Toast.makeText(context, "Please configure your Parse Application ID and Client Key in AppConfig.java", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }


    public static void registerParse(Context context) {
        // initializing parse library

        Parse.initialize(new Parse.Configuration.Builder(context)
                        .applicationId( AppConfig.PARSE_APPLICATION_ID)
                        .clientKey( AppConfig.PARSE_CLIENT_KEY)
                        .enableLocalDataStore()
                        .server("https://parseapi.back4app.com/").build());
                // Parse.initialize(context, AppConfig.PARSE_APPLICATION_ID, AppConfig.PARSE_CLIENT_KEY);
                ParseInstallation.getCurrentInstallation().saveInBackground();

//        ParsePush.subscribeInBackground(AppConfig.PARSE_CHANNEL, new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                Log.e(TAG, "Successfully subscribed to Parse!");
//            }
//        });
    }

    public static final void showKeyBoard(Activity ctx){
        if(ctx.getCurrentFocus() != null){
            InputMethodManager im =(InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(),0);
        }
    }

    public static final void hideKeyBoard(Activity ctx,View v){
        try {

            InputMethodManager im = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int listWidth = listView.getMeasuredWidth();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(listWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED ));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;


            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }



        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;// + (gridView.getHeight() * (listAdapter.getCount() - 1));
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }


    public static void subscribeWithEmail(String email) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        installation.put("email", email);

        installation.saveInBackground();
    }
}