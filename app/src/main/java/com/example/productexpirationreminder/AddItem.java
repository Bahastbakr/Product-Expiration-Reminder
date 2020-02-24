package com.example.productexpirationreminder;

import android.net.Uri;
import android.widget.ImageView;

public class AddItem {
    private Uri imageView ;
    private String  textView,textView1;



    public AddItem(Uri imageView, String textView, String textView1) {
        this.imageView = imageView;
        this.textView = textView;
        this.textView1 = textView1;
    }


    public Uri getImageView() {
        return imageView;
    }

    public String getTextView() {
        return textView;
    }

    public String getTextView1() {
        return textView1;
    }



}
