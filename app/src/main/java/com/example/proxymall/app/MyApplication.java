package com.example.proxymall.app;

import android.app.Application;

import com.example.proxymall.helper.ParseUtils;

/**
 * Created by stephen on 11/20/2016.
 */
public class MyApplication extends Application{

        private static MyApplication mInstance;

        @Override
        public void onCreate() {
            super.onCreate();
            mInstance = this;

            // register with parse
            ParseUtils.registerParse(this);

//            new FlurryAgent.Builder()
//                    .withLogEnabled
//                    .build(this, "C4GKGGYVP6JT5KX9PCG3");
        }


        public static synchronized MyApplication getInstance() {
            return mInstance;
        }

}
