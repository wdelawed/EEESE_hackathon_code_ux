package com.hassan.a.abubakr.pojecttemplate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hassan.a.abubakr.pojecttemplate.API;
import com.hassan.a.abubakr.pojecttemplate.R;
import com.hassan.a.abubakr.pojecttemplate.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText userId;
    EditText password;
    Button login ;

    ProgressBar progressBar;
    /**
     * Important point to notice
     * We store globally share variables such as keys and addresses from here
     */
    public static final String SERVERADD = "http://192.168.43.168:8000/";
    public static final String id_key = "id_key";
    public static final String token_key = "token_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = (EditText)  findViewById(R.id.user_id);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login_button);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEntries()){
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit client = API.getClient(SERVERADD);
                    API.APIService service = client.create(API.APIService.class);
                    service.login(userId.getText().toString(),password.getText().toString()).enqueue(new Callback<User>() {

                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.body()==null){
                                Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }
                            // after successful login , token and user id are used further for
                            // authentication through all operation
                            progressBar.setVisibility(View.GONE);

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            // store the newly received token and id in shared preferences
                            editor.putInt(id_key,response.body().getId());
                            editor.putString(token_key,response.body().getToken());
                            editor.apply();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            // all issues that leads to this point have relation to network
                            // connectivity
                            Log.e("err",t.getMessage());
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });


    }

    /**
     * Although not tough but quite valid way for ensuring that the data go safe to the server
     * @return boolean true if the entries pass the test, false otherwise
     */
    private boolean validateEntries() {
        Editable user_id = userId.getText();
        Editable password_str = password.getText();

        if (user_id==null || user_id.length()<6){
            userId.setError("incorrect format");
            return false;
        }
        if (password_str==null || password_str.length()<6){
            password.setError("incorrect password format");
            return false;
        }

        return true;
    }
}
