package com.hassan.a.abubakr.pojecttemplate.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hassan.a.abubakr.pojecttemplate.API;
import com.hassan.a.abubakr.pojecttemplate.R;
import com.hassan.a.abubakr.pojecttemplate.models.Duty;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity that works as a dialogue, for this short endeavoring project it might be wise to divide
 * work as much as possible
 */
public class SendMessageDialogue extends AppCompatActivity {

    EditText title;
    EditText body;

    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_send_message_dialogue);

        title = (EditText) findViewById(R.id.message_title);
        body = (EditText) findViewById(R.id.message_body);
        send = (Button) findViewById(R.id.send_message_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().length()>0 && body.getText().length()>0){
                    sendMessage(title.getText().toString(),body.getText().toString());
                }else {
                    Toast.makeText(SendMessageDialogue.this, "Please enter a valid information", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /** User's complains are sent to admin for further process, an addition to this might be to allow
    * feedback from the admin or support to the user
     * a point to say it's not a good idea to allow direct chatting as this will cause great overwhelm
     * over the admin, just focus on the main points , delivering news and communication with minimum cost
     * */
    private void sendMessage(String title, String body) {
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SendMessageDialogue.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        service.reportMessage(title,body,id,"Bearer "+token).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body()==null){
                    Log.e("errs",token);
                    return;
                }
                Log.e("resp", response.body().toString());
                Toast.makeText(SendMessageDialogue.this, "success", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("fail",call.toString() + " "+ t.getMessage());
                Toast.makeText(SendMessageDialogue.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
