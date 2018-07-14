package com.hassan.a.abubakr.pojecttemplate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hassan.a.abubakr.pojecttemplate.R;

public class Splash extends AppCompatActivity {

    /**
     * nothing fancy , just waving our logo to the user for a tiny bit of time while checking for
     * his/her credentials for previous login
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splash.this);

                int id = preferences.getInt(LoginActivity.id_key,-1);
                String token = preferences.getString(LoginActivity.token_key,"");
                Intent i;
                Log.e("idtoken",id+" : "+token);
                if (id==-1 || token.equals("")){
                    i = new Intent(Splash.this,LoginActivity.class);
                }else {
                    i = new Intent(Splash.this,MainActivity.class);
                }
                startActivity(i);
                finish();
            }
        },1000);
    }
}
