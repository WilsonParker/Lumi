package com.graction.developer.lumi.Activity;

import android.support.v7.app.AppCompatActivity;

import com.graction.developer.lumi.Util.Log.HLogger;

abstract class BaseActivity extends AppCompatActivity {
    protected HLogger logger;

    @Override
    protected void onStart() {
        super.onStart();
        baseInit();
        init();
    }

    private void baseInit(){
        logger = new HLogger(getClass());
    }

    protected abstract void init();

}
