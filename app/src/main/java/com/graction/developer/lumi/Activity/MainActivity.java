package com.graction.developer.lumi.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.graction.developer.lumi.Fragment.AlarmFragment;
import com.graction.developer.lumi.Fragment.Forecast5DayFragment;
import com.graction.developer.lumi.Fragment.HomeFragment;
import com.graction.developer.lumi.Fragment.TestFragment;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.UI.Layout.CustomNavigationView;
import com.graction.developer.lumi.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_current:
                    fragment = HomeFragment.getInstance();
                    break;
                case R.id.navigation_daily:
                    fragment = Forecast5DayFragment.getInstance();
                    break;
                case R.id.navigation_alarm:
                    fragment = AlarmFragment.getInstance();
                    break;
                case R.id.navigation_alarmtest:
                    fragment = TestFragment.getInstance();
                    break;
            }
            replaceContent();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void init() {
//        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        binding.navigation.setSelectedItemId(R.id.navigation_current);
        ArrayList<CustomNavigationView.NavigationItem> items = new ArrayList<>();
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(HomeFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(Forecast5DayFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(AlarmFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(TestFragment.getInstance());
        }));
        binding.activityMainCNV.bindItemView(this, items, (imageView, resId) -> {
            Glide.with(MainActivity.this).load(resId).into(imageView);
        });
        binding.activityMainCNV.actionItem(0);
    }

    private void replaceContent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    private void replaceContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

}
