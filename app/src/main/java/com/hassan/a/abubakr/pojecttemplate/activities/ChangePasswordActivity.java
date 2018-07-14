package com.hassan.a.abubakr.pojecttemplate.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hassan.a.abubakr.pojecttemplate.API;
import com.hassan.a.abubakr.pojecttemplate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText newPassword;
    EditText retypePassword;
    Button updatePassword;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // binding the main views
        newPassword = (EditText) findViewById(R.id.new_password);
        retypePassword = (EditText) findViewById(R.id.retype_password);

        updatePassword = (Button) findViewById(R.id.update_password);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // just a quick check to validate the entered password
                if (newPassword.getText().length()<6 || retypePassword.getText().length()<6){
                    Toast.makeText(ChangePasswordActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }else if (newPassword.getText().toString().equals(retypePassword.getText().toString())) {
                    updatePassword(newPassword.getText().toString());
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     *
     * @param newPassword after validating the password ,
     *                   we use our token to authenticate
     *                    the operation of changing the password
     *
     */
    private void updatePassword(String newPassword) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ChangePasswordActivity.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        Log.e("idtoken",String.valueOf(id)+" : "+"Bearer "+token);

        service.updatePassword(newPassword,String.valueOf(id),"Bearer "+token).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body()==null){
                    // execution should not reach here

                    Log.e("errs",response.toString());
                    Toast.makeText(ChangePasswordActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                Log.e("resp", response.body().toString());
                Toast.makeText(ChangePasswordActivity.this, "success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("fail",call.toString() + " "+ t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
