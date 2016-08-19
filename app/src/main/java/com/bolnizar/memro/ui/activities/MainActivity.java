package com.bolnizar.memro.ui.activities;

import com.bolnizar.memro.R;
import com.bolnizar.memro.events.OpenMemeCaption;
import com.bolnizar.memro.ui.fragments.SearchFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchFragment(new SearchFragment(), false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenMemeCaption event) {
        startActivity(CaptionMemeActivity.createIntent(event.memeId, this));
    }

    private void switchFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
        if (addToBackstack) {
            transaction.addToBackStack(fragment.toString());
        }
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
