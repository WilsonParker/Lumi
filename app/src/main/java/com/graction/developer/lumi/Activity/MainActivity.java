package com.graction.developer.lumi.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.graction.developer.lumi.Adapter.FragmentAdapter;
import com.graction.developer.lumi.Fragment.AlarmFragment;
import com.graction.developer.lumi.Fragment.Forecast5DayFragment;
import com.graction.developer.lumi.Fragment.HomeFragment;
import com.graction.developer.lumi.Fragment.TestFragment;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;
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
        initViewPager();
    }

    private void initViewPager() {
        /*
            binding.activityMainVP.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ViewPagerTabAdapter viewPagerTabAdapter = new ViewPagerTabAdapter((index, item) -> {
                binding.activityMainTab.getTabAt(index).setIcon(item.getResIcon());
            });
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(HomeFragment.getInstance(), R.drawable.ic_home_black_24dp));
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(Forecast5DayFragment.getInstance(), R.drawable.ic_dashboard_black_24dp));
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(AlarmFragment.getInstance(), R.drawable.ic_notifications_black_24dp));
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(TestFragment.getInstance(), R.drawable.ic_dashboard_black_24dp));
            TabLayoutSupport.setupWithViewPager(binding.activityMainTab, binding.activityMainVP, viewPagerTabAdapter);
        */

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        ArrayList<FragmentAdapter.TabItem> items = new ArrayList<>();
        items.add(fragmentAdapter.new TabItem(HomeFragment.getInstance(), R.drawable.tab_home));
        items.add(fragmentAdapter.new TabItem(Forecast5DayFragment.getInstance(), R.drawable.ic_dashboard_black_24dp));
        items.add(fragmentAdapter.new TabItem(AlarmFragment.getInstance(), R.drawable.ic_notifications_black_24dp));
        items.add(fragmentAdapter.new TabItem(TestFragment.getInstance(), R.drawable.dust_on));
        fragmentAdapter.setItems(items);
        fragmentAdapter.setOnTabSelected((index, item) -> {
            logger.log(HLogger.LogType.INFO,"FragmentAdapter","TabItem : "+item.getResIcon());
//            binding.activityMainTab.getTabAt(index).setIcon(item.getResIcon());
        });
        binding.activityMainTab.setupWithViewPager(binding.activityMainVP);
        binding.activityMainVP.setAdapter(fragmentAdapter);
        for(int i=0; i<items.size();i++){
            binding.activityMainTab.getTabAt(i).setIcon(items.get(i).getResIcon());
        }
    }

    private void initNavigation() {
        //        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //        binding.navigation.setSelectedItemId(R.id.navigation_current);
        /*ArrayList<CustomNavigationView.NavigationItem> items = new ArrayList<>();
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
        binding.activityMainCNV.actionItem(0);*/
    }

    private void replaceContent() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_FL, fragment).commit();
    }

    private void replaceContent(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_FL, fragment).commit();
    }

}
