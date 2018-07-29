package com.example.proxymall.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class RegisterWithEmail extends Activity {

    EditText nameEditext,phoneNumberEdittext,emailEditext,passwordEditext;
    String namee,phoneNumberr,emaill,passwordd;
    ParseFile imageFile;
    Button registerButton;
    byte[] image;
    private SQLiteHandler db;

    String currentDate,search;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_with_email);

        init();

        onClickListeners();


    }

    void onClickListeners(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }


    void init(){

        db = new SQLiteHandler(getApplicationContext());
        nameEditext = (EditText) findViewById(R.id.nameEdittext);
        phoneNumberEdittext = (EditText) findViewById(R.id.phoneNumberEdittext);
        registerButton = (Button) findViewById(R.id.registerButton);
        emailEditext = (EditText) findViewById(R.id.emailEdittext);
        passwordEditext = (EditText) findViewById(R.id.passwordEdittext);

    }


    void registration(){



        namee = nameEditext.getText().toString();
       phoneNumberr = phoneNumberEdittext.getText().toString();
        emaill = emailEditext.getText().toString();
        passwordd = passwordEditext.getText().toString();





        if (!namee.isEmpty() && !phoneNumberr.isEmpty() && !emaill.isEmpty() && !passwordd.isEmpty() ) {



            registerButton.setText("Registering ...");
            nameEditext.setEnabled(false);
            phoneNumberEdittext.setEnabled(false);
            emailEditext.setEnabled(false);
            passwordEditext.setEnabled(false);

            try {



                Drawable myDrawable = getResources().getDrawable(R.drawable.user_image_placeholder);
                Bitmap profile_pic =((BitmapDrawable)myDrawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                profile_pic.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                image = stream.toByteArray();
                imageFile = new ParseFile(System.currentTimeMillis() + ".jpg",  image);
            }catch (Exception e){
                e.printStackTrace();

            }

            final ParseUser user = new ParseUser();
            user.setUsername(emaill);
            user.put("phone_number", phoneNumberr);
            user.put("name", namee);
            user.setPassword(passwordd);
            user.setEmail(emaill);
            //   user.put("profile_pic", imageFile);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        user.put("profile_pic", imageFile);
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                String userId= user.getObjectId().toString();
                                String created_At = user.getCreatedAt().toString();
                                String userPhoneNumber = user.get("phone_number").toString();
                                String userEmail = user.getUsername().toString();
                                String name_of_user = user.get("name").toString();


                                SessionManager.setLogin(true);


                                db.addUser(name_of_user, userEmail, userPhoneNumber, userId, created_At, image);
//                                for(int i=0; i<Lautech_courses.length;i++) {
//                                    if(questionDb.addDefaultCoursesStatus(Lautech_courses[i], Lautech_courses_status[i])){
////                                        Toast.makeText(UserRegistration.this, "ywaa", Toast.LENGTH_SHORT).show();
//                                    }
//                                }

                                finish();
                            }
                        });

                    } else {

                        CheckEmailAvailability(emaill);

                    }
                }
            });

        } else {



            if (namee.isEmpty()) {
                nameEditext.setError("this must be filled!");


            }

            else  if (phoneNumberr.isEmpty()) {
                phoneNumberEdittext.setError("this must be filled!");


            }
            else  if (emaill.isEmpty()) {
                emailEditext.setError("this must be filled!");


            }

            else if (passwordd.isEmpty()) {
                passwordEditext.setError("this must be filled!");


            }


        }

    }

    void CheckEmailAvailability(String emaill){

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", emaill);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        search="found";

                        Toast.makeText(RegisterWithEmail.this, "Email is in use by a user!", Toast.LENGTH_SHORT).show();
                       // progressLayout.setVisibility(View.INVISIBLE);
                        registerButton.setText("Register");
                        nameEditext.setEnabled(true);
                        phoneNumberEdittext.setEnabled(true);
                        emailEditext.setEnabled(true);
                        passwordEditext.setEnabled(true);

                    }
                    else if (objects.size() == 0){

                        search="unfound";
                        Toast.makeText(RegisterWithEmail.this, "UnExpected Exception.", Toast.LENGTH_SHORT).show();

                       // progressLayout.setVisibility(View.INVISIBLE);
                        registerButton.setText("Register");
                        nameEditext.setEnabled(true);
                        phoneNumberEdittext.setEnabled(true);
                        emailEditext.setEnabled(true);
                        passwordEditext.setEnabled(true);
                    }
                } else {
                    // Something went wrong.
                }
            }
        });




    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(RegisterWithEmail.this,Login.class);
        startActivity(intent);
    }
}
