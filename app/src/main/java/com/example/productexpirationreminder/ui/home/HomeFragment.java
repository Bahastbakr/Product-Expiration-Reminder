package com.example.productexpirationreminder.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productexpirationreminder.AddItem;
import com.example.productexpirationreminder.ExampleAdapter;
import com.example.productexpirationreminder.Home;
import com.example.productexpirationreminder.R;
import com.example.productexpirationreminder.Start;
import com.example.productexpirationreminder.ui.gallery.GalleryViewModel;
import com.example.productexpirationreminder.ui.send.SendViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);







        return root;
    }


}
