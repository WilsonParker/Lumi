package com.graction.developer.lumi.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.graction.developer.lumi.Fragment.AlarmFragment;
import com.graction.developer.lumi.Fragment.Forecast5DayFragment;
import com.graction.developer.lumi.Fragment.HomeFragment;
import com.graction.developer.lumi.Fragment.TestFragment;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.UI.UIFactory;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private HLogger logger;
    private ActivityMainBinding binding;
    private UIFactory uiFactory;
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
                case R.id.navigation_notifications:
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
        init();
    }

    private void init() {
        logger = new HLogger(getClass());
        uiFactory = UIFactory.getInstance(this);

        BottomNavigationView navigation = uiFactory.createView(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_current);
    }

    private void replaceContent(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

}
