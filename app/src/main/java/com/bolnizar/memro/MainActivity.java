package com.bolnizar.memro;

import com.bolnizar.memro.fragments.SearchFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new SearchFragment()).commit();
    }
}
