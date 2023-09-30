package com.example.hitgaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hitgaming.fragments.HomeFragment;
import com.example.hitgaming.fragments.ResultsFragment;

public class MainActivity extends AppCompatActivity {
    private Button showAllBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment newFragment = new ResultsFragment();
        showAllBtn = findViewById(R.id.btn_showAll);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.frame_layout, homeFragment); // R.id.fragmentContainer is the ID of your container view

        fragmentTransaction.commit();

        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}