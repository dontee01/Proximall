package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proxymall.R;
import com.example.proxymall.helper.SQLiteHandler;
import com.example.proxymall.helper.SessionManager;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by stephen on 5/14/2017.
 */

public class Login extends Activity{

    EditText emailEdittext,passwordEdittext;
    private SQLiteHandler db;

    Button linkToRegisterWithEmailButton,loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        init();
       onClickListener();
    }


    void onClickListener(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        linkToRegisterWithEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,RegisterWithEmail.class);
                startActivity(intent);
                finish();
            }
        });
    }
    void init(){
        db = new SQLiteHandler(getApplicationContext());
        loginButton = (Button) findViewById(R.id.loginButton);
        emailEdittext = (EditText)findViewById(R.id.emailEdittext);
        passwordEdittext = (EditText)findViewById(R.id.passwordEdittext);
        linkToRegisterWithEmailButton = (Button)findViewById(R.id.linkToRegisterWithEmailButton);
    }



    void login(){
        final String email = emailEdittext.getText().toString();
        String passwordd = passwordEdittext.getText().toString();
        if (!email.isEmpty() && !passwordd.isEmpty() ) {


           // progressLayout.setVisibility(View.VISIBLE);
            loginButton.setText("Logging in...");
            emailEdittext.setEnabled(false);
            passwordEdittext.setEnabled(false);
            linkToRegisterWithEmailButton.setEnabled(false);
          //  forgot_password_button.setEnabled(false);

            ParseUser.logInInBackground(email, passwordd, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null ) {

                        ParseUser currentUser = ParseUser.getCurrentUser();

                        try {
                            ParseFile img = currentUser.getParseFile("profile_pic");
                            Bitmap profile_pic = BitmapFactory.decodeStream(img.getDataStream());



                            String namee=parseUser.getString("name");
                            String phoneNumberr=parseUser.getString("phone_number");
                            String emaill=parseUser.getEmail();
                            String userId = parseUser.getObjectId().toString();
                            String created_At=parseUser.getCreatedAt().toString();



                            SessionManager.setLogin(true);
                            db.addUser(namee, emaill, phoneNumberr, userId, created_At, AppUtilityClass.getBytes(profile_pic));
                            // Toast.makeText(Login.this,namee.toUpperCase()+"\n Your are welcome back",Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(UserLogin.this, HomeMain.class);

                            //intent.putExtra("fragmentSwitcher","main");
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                          //  startActivity(intent);
                            finish();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    } else {


                        CheckEmailAvailability(email);



                    }
                }
            });


        } else {
            if (email.isEmpty()) {
                emailEdittext.setError("this must be filled!");


            }
            if(!isValidEmail(email)){
                Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_LONG).show();
            }

            if (passwordd.isEmpty()) {
                passwordEdittext.setError("this must be filled!");

            }


        }
    }


    void CheckEmailAvailability(String emaill) {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", emaill);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        // search = "found";

                        Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                      //  progressLayout.setVisibility(View.INVISIBLE);
                        emailEdittext.setEnabled(true);
                        passwordEdittext.setEnabled(true);
                        linkToRegisterWithEmailButton.setEnabled(true);
                       // forgot_password_button.setEnabled(true);
                        loginButton.setText("Login");

                    } else if (objects.size() == 0) {
                        Toast.makeText(Login.this, "You are not yet a User, Please Register.", Toast.LENGTH_SHORT).show();

                        //progressLayout.setVisibility(View.INVISIBLE);
                        //loginButton.setText("Logging in...");
                        emailEdittext.setEnabled(true);
                        passwordEdittext.setEnabled(true);
                        linkToRegisterWithEmailButton.setEnabled(true);
                      //  forgot_password_button.setEnabled(true);
                        loginButton.setText("Login");
                    }
                } else {
                    Toast.makeText(Login.this, "Please try again", Toast.LENGTH_LONG).show();


                }

            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
