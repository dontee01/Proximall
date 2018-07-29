package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.example.proxymall.R;
import com.example.proxymall.helper.ParseUtils;
import com.example.proxymall.helper.SQLiteHandler;
import com.example.proxymall.helper.SessionManager;

/**
 * Created by stephen on 5/8/2017.
 */

public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 2500;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

      //  ParseUtils.registerParse(this);
     //   getActionBar().hide();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        // imageView.setLayoutParams(layoutParams);

//        load animation from resource file
//        Animation scaleAnimation = AnimationUtils.loadAnimation(
//                getApplicationContext(), R.anim.alpha_animation);
        //imageView.startAnimation(scaleAnimation);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing Splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {


                Intent i = new Intent(Splash.this, locationManagerStateDiscovery.class);
                startActivity(i);
                finish();

//                if (session.isLoggedIn()) {
//
//
//                    Intent i = new Intent(Splash.this, HomeMain.class);
//                    i.putExtra("fragmentSwitcher","main");
//                    startActivity(i);
//
//                    finish();
//                }
//                else if(!session.isLoggedIn()){
//
//                    Intent i = new Intent(Splash.this, UserLogin.class);
//                    startActivity(i);
//                    finish();
//
//                }


                // close this activity

            }
        }, SPLASH_TIME_OUT);

    }
}
