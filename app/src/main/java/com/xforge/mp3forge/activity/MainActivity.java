package com.xforge.mp3forge.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xforge.mp3forge.R;
import com.xforge.mp3forge.databinding.ActivityMainBinding;
import com.xforge.mp3forge.fragment.HomeFragment;
import com.xforge.mp3forge.fragment.PlayListFragment;
import com.xforge.mp3forge.fragment.PlayerFragment;
import com.xforge.mp3forge.player.MediaPlayerAction;
import com.xforge.mp3forge.player.MediaPlayerService;
import com.xforge.mp3forge.player.PlayerControls;
import com.xforge.mp3forge.vm.MainViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeFragment homeFragment;
    private PlayerFragment playerFragment;
    private PlayListFragment playListFragment;

    @Inject
    MainViewModel mainViewModel;

    @Inject
    PlayerControls playerControls;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showTab(homeFragment);
                    return true;
                case R.id.navigation_play:
                    showTab(playerFragment);
                    return true;
                case R.id.navigation_play_list:
                    showTab(playListFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mainViewModel);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeFragment = new HomeFragment();
        playerFragment = new PlayerFragment();
        playListFragment = new PlayListFragment();
        playListFragment.setFm(getSupportFragmentManager());
        showTab(homeFragment);
        Intent stopIntent = new Intent(MainActivity.this, MediaPlayerService.class);
        stopIntent.setAction(MediaPlayerAction.START_SERVICE);
        startService(stopIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAppPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        playerControls.stopForground();
    }

    @Override
    public void onPause() {
        super.onPause();
       playerControls.startForground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerControls.stop();
    }

    public void showTab(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideOtherTabs(fragmentManager, transaction, fragment);
        if (!fragmentManager.getFragments().contains(fragment)) {
            fragment.setUserVisibleHint(true);
            transaction.add(R.id.content_fragment, fragment);
        }

        transaction.commitAllowingStateLoss();
    }

    private void hideOtherTabs(FragmentManager fragmentManager, FragmentTransaction transaction, Fragment fragment) {
        for (Fragment frag : fragmentManager.getFragments()) {
            if (frag.equals(fragment)) {
                transaction.show(frag);
                frag.setUserVisibleHint(true);
                continue;
            }
            transaction.hide(frag);
            frag.setUserVisibleHint(false);
        }
    }

    private void checkAppPermissions(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }
    }
}
