package com.example.proxymall.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proxymall.R;

/**
 * Created by stephen on 5/14/2017.
 */

public class SearchActivity extends Activity{

    EditText searchEdittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        init();


        searchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    Intent intent = new Intent(SearchActivity.this,CategoryProductActivity.class);
                    intent.putExtra("category", searchEdittext.getText().toString());
                    startActivity(intent);
                   // loadSearchedGoodsMethods();
                    ((InputMethodManager)getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchEdittext.getWindowToken(), 0);

                    //Toast.makeText(HostelsSearch.this,"yes", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
    }
    void init(){
        searchEdittext = (EditText)findViewById(R.id.searchEdittext);
    }
}
