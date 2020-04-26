package com.example.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    tempFragment tempFrag;
    distFragment distFrag;
    SharedPreferences sharedPreferences;
    String PREF_KEY = "SAVE_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempFrag = new tempFragment();
        setFragment(tempFrag);
        SharedPreferences sharedPreferences= getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.dist)
        {
            Toast.makeText(this, "Distance Converter Selected", Toast.LENGTH_LONG).show();
            distFrag = new distFragment();
            setFragment(distFrag);
        }
        else if (item.getItemId() == R.id.temp)
        {
            Toast.makeText(this, "Temperature Converter Selected", Toast.LENGTH_LONG).show();
            tempFrag = new tempFragment();
            setFragment(tempFrag);
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    public void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
}
