package com.hassan.a.abubakr.pojecttemplate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hassan.a.abubakr.pojecttemplate.API;
import com.hassan.a.abubakr.pojecttemplate.R;
import com.hassan.a.abubakr.pojecttemplate.adapters.NewsAdapter;
import com.hassan.a.abubakr.pojecttemplate.adapters.ResultsAdapter;
import com.hassan.a.abubakr.pojecttemplate.models.Announcement;
import com.hassan.a.abubakr.pojecttemplate.models.Duty;
import com.hassan.a.abubakr.pojecttemplate.models.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This view holds the core components of the system
 * 1- the announcements viewer
 * 2- the results viewer and to view the schedual of the students/professors
 * 3- it represents the center from which the user can perform all of his tasks
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    Button resultsTab;
    Button newsTab;
    Button lessonsTab;

    ViewGroup resultsParent;
    ViewGroup newsParent;
    ViewGroup lessonsParent;

    Button mCurrentButton;

    RecyclerView resultsRecycler;
    RecyclerView newsRecycler;

    DrawerLayout mDrawerLayout;

    private List<Announcement> mAnnouncements = new ArrayList<>();
    NewsAdapter newsAdapter;
    private ArrayList<Result> mResults = new ArrayList<>();
    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        bindViews();
        mCurrentButton=newsTab;
        manageTabs(mCurrentButton);

        resultsTab.setOnClickListener(this);
        newsTab.setOnClickListener(this);
        lessonsTab.setOnClickListener(this);

        resultsAdapter = new ResultsAdapter(MainActivity.this, mResults, resultsRecycler);
        resultsRecycler.setAdapter(resultsAdapter);
        resultsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        newsAdapter = new NewsAdapter(MainActivity.this,mAnnouncements, resultsRecycler);
        newsRecycler.setAdapter(newsAdapter);
        newsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getPersonalAnnouncements();
        getResults();
        getDuty();

    }

    private void getDuty() {
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        service.getDuty(id,"Bearer "+token).enqueue(new Callback<Duty>() {
            @Override
            public void onResponse(Call<Duty> call, Response<Duty> response) {
                if (response.body()==null){
                    Log.e("errs",token);
                    return;
                }
                // TODO set duty image here

            }

            @Override
            public void onFailure(Call<Duty> call, Throwable t) {
                // all issues that lead to this point are related to connectivity problems
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }


    /**
     * user's most related posts should be viewed first, that's why we don't initiate the getAnnouncements
     * until getPersonalAnnouncements is completed.
     */
    private void getPersonalAnnouncements() {
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        service.getPersonalAnnouncements(id,"Bearer "+token).enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if (response.body()==null){
                    Log.e("errs",token);
                    return;
                }
                // after receiving the announcement , trigger the adapter to change the layout
                mAnnouncements .addAll(response.body());
                newsAdapter.notifyDataSetChanged();
                getAnnouncements();

            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                // all issues that lead to this point are related to connectivity problems
                Log.e("errorrs",t.getMessage());
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * similar to the above function with just the method name is different
     */
    private void getAnnouncements() {
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        service.getAnnouncements(id,"Bearer "+token).enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if (response.body()==null){
                    Log.e("errs",token);
                    return;
                }
                mAnnouncements .addAll(response.body());
                newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                // all issues that lead to this point are related to connectivity problems
                Log.e("errorrs",t.getMessage());
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getResults() {
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        service.getResults(id,"Bearer "+token).enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                if (response.body()==null){
                    Log.e("errs",token);
                    return;
                }
                mResults .addAll(response.body());
                resultsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                // all issues that lead to this point are related to connectivity problems

                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // a way to simplify the code by separating the binding of the views from the main function
    private void bindViews() {
        resultsTab = (Button)findViewById(R.id.results_tab);
        newsTab = (Button)findViewById(R.id.news_tab);
        lessonsTab = (Button)findViewById(R.id.lessons_tab);

        resultsParent = (ViewGroup) findViewById(R.id.results_parent);
        newsParent = (ViewGroup) findViewById(R.id.news_parent);
        lessonsParent = (ViewGroup) findViewById(R.id.lessons_parent);

        resultsRecycler = (RecyclerView) findViewById(R.id.results_recycler);
        newsRecycler = (RecyclerView) findViewById(R.id.news_recycler);
    }

    @Override
    public void onClick(View view){

        manageTabs(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_message:
                Intent i = new Intent(MainActivity.this,SendMessageDialogue.class);
                startActivity(i);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    // as this is a custom tab layout (almost from scratch), it takes the latest clicked tab and focus
    // the view to it and shows the corresponding UI
    private void manageTabs(View toFocus) {
        switch (toFocus.getId()){
            case R.id.news_tab:
                newsParent.setVisibility(View.VISIBLE);
                resultsParent.setVisibility(View.GONE);
                lessonsParent.setVisibility(View.GONE);
                focusTab(newsTab);

                unFocusTab(resultsTab);
                unFocusTab(lessonsTab);

                setDrawable(newsTab,R.drawable.news_white_icon);
                setDrawable(lessonsTab,R.drawable.calendar_black_icon);
                setDrawable(resultsTab,R.drawable.result_black_icon);

                break;
            case R.id.results_tab:
                newsParent.setVisibility(View.GONE);
                resultsParent.setVisibility(View.VISIBLE);
                lessonsParent .setVisibility(View.GONE);
                unFocusTab(newsTab);
                focusTab(resultsTab);
                unFocusTab(lessonsTab);
                setDrawable(newsTab,R.drawable.news_black);
                setDrawable(lessonsTab,R.drawable.calendar_black_icon);
                setDrawable(resultsTab,R.drawable.result_white);
                break;
            case R.id.lessons_tab:
                newsParent.setVisibility(View.GONE);
                resultsParent.setVisibility(View.GONE);
                lessonsParent.setVisibility(View.VISIBLE);
                unFocusTab(newsTab);
                unFocusTab(resultsTab);
                focusTab(lessonsTab);
                setDrawable(newsTab,R.drawable.news_black);
                setDrawable(lessonsTab,R.drawable.calender_white);
                setDrawable(resultsTab,R.drawable.result_black_icon);
                break;
        }
    }

    void setDrawable(Button button,int resId){
        Drawable img = getResources().getDrawable(resId );
        img.setBounds( 0, 0, 60, 60 );
        button.setCompoundDrawables( null, img, null, null );
    }
    /**
     * as the buttons background is set to a drawable resource I had to do some dirty work here
     * by getting the drawable defined in the drawable file and alter it's property directly
     * @param button the view to which will be focus on
     */
    private void focusTab(Button button) {
        button.setPressed(true);
        button.setTextColor(getResources().getColor(R.color.white));

        LayerDrawable drawable = (LayerDrawable) button.getBackground();

        GradientDrawable gradientDrawable = (GradientDrawable) drawable.findDrawableByLayerId(R.id.item);


        gradientDrawable.setColor(ContextCompat.getColor(MainActivity.this,R.color.blue_theme));

    }

    // same as the above but opposite
    private void unFocusTab(Button button){
        button.setPressed(false);
        button.setTextColor(getResources().getColor(R.color.text_color));
        LayerDrawable drawable = (LayerDrawable) button.getBackground();

        GradientDrawable gradientDrawable = (GradientDrawable) drawable.findDrawableByLayerId(R.id.item);


        gradientDrawable.setColor(ContextCompat.getColor(MainActivity.this,R.color.white));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.contact_us:
                // tODO just show the contacts of the admin/support of the system, LOW PRIORITY
                break;
            case R.id.change_password:
                // we handle changing password in a separate component to organize the project more
                i = new Intent(MainActivity.this,ChangePasswordActivity.class);
                startActivity(i);
                break;
            case R.id.your_result:
                // just go the results tab, increase accessibility to improve user experience
                manageTabs(resultsTab);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.log_out:
                // just erase user's credentials from the storage and go out
                logout();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(LoginActivity.token_key,"");
                editor.putInt(LoginActivity.id_key,-1);
                editor.apply();
                i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return false;
    }

    private void logout() {
        Retrofit client = API.getClient(LoginActivity.SERVERADD);
        API.APIService service = client.create(API.APIService.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        int id = preferences.getInt(LoginActivity.id_key,-1);
        final String token = preferences.getString(LoginActivity.token_key,"");
        service.logout(id,"Bearer "+token).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body()==null){
                    // execution should not reach here
                    Log.e("errs",token);
                    return;
                }
                Log.e("resp", response.body().toString());
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("fail",call.toString() + " "+ t.getMessage());
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
