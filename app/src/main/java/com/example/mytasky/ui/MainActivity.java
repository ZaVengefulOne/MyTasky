package com.example.mytasky.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytasky.ui.fragments.AuthFragment;
import com.example.mytasky.ui.fragments.MenuFragment;
import com.example.mytasky.R;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, AuthFragment.class, null)
                    .commit();
        }
    }

}
