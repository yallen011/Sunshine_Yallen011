package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }

        /*
        ActionBar actionBar = getSupportActionBar();
       //actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        */
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        //option to view default location on a map
        if(id == R.id.action_map){
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap(){

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String location = preferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default_value));

        String locationUri = "geo:0,0?";
        Uri intentUri = Uri.parse(locationUri)
                .buildUpon()
                .appendQueryParameter("q",location)
                .build();

        Intent locationIntent = new Intent(Intent.ACTION_VIEW);
        locationIntent.setData(intentUri);

        //only execute the activity if it resolves successfull; if there is an activity to handle the intent
        if(locationIntent.resolveActivity(getPackageManager()) !=null){
            startActivity(locationIntent);
        }else{
            Log.i(LOG_TAG, "Couldn't call "+ location +", no receiving apps installed");
        }

    }
}
