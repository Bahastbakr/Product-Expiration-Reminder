package com.example.productexpirationreminder;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Start extends AppCompatActivity {
    Button login,cancel ,loginD;
    EditText email,pass;

 public static SharedPreferences sharedPreferences;
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        login=findViewById(R.id.login2);



    login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(Start.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("چوونەژوورەوە");
        dialog.setCancelable(true);
        dialog.show();
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        loginD=dialog.findViewById(R.id.loginD);
        loginD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=dialog.findViewById(R.id.email);
                pass=dialog.findViewById(R.id.pass);


                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("email",email.getText().toString());
                editor.putString("pass",pass.getText().toString());
                editor.commit();

                Intent intent=new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });

    }
});

    }
    public void onBackPressed() {


       final AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setTitle("!!")
                .setMessage("چوونەدەرەوە لە پرۆگرام ؟")
                .setIcon(R.drawable.ic_menu_send)
                .setCancelable(true)
               .setPositiveButton("بەڵێ", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Start.super.onBackPressed();

                   }
               })
               .setNegativeButton("نەخێر", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               })

.show();
     }
}