package com.example.productexpirationreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splashlogo extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashlogo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

                    String email = sharedPreferences.getString("email", null);
                    String pass = sharedPreferences.getString("pass", null);
                    if ((!email.equals(null)) && ( !pass.equals(null))) {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }
                }catch (Exception e) {

                    Intent intent = new Intent(getApplicationContext(), Start.class);
                    startActivity(intent);
                    finish();

                }


            }
        }, 3000);



    }
}
