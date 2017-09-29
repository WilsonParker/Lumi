package com.graction.developer.lumi.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Xml.Weather;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.Parser.XmlPullParserManager;

import java.util.HashMap;

public class Intro extends AppCompatActivity {
    private Context context;
    private Handler handler = new Handler();
    private HLogger logger;
    private int completeState = 1, runState, errorState;
    private Thread iThread = new Thread(new Runnable() {
        @Override
        public void run() {
            XmlPullParserManager xmlPullParserManager = XmlPullParserManager.getInstance();
            xmlPullParserManager.setContext(context);
            try {
                DataStorage.weathers = new HashMap<>();
                for (Weather weather : xmlPullParserManager.<Weather>xmlParser(Weather.class, R.xml.weathers))
                    DataStorage.weathers.put(weather.getId(), weather);
            } catch (Exception e) {
                logger.log(HLogger.LogType.ERROR, "iThread error", e);
                errorState = 1;
                return;
            }
            runState++;
        }
    }), cThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (completeState == runState) {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                    break;
                } else if (errorState == 1) {
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    logger.log(HLogger.LogType.ERROR, "cThread error", e);
                    errorState = 1;
                }
            }
            Looper.prepare();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (errorState == 1)
                        Toast.makeText(Intro.this, "초기화에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        init();
    }

    private void init() {
        logger = new HLogger(getClass());
        context = this;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iThread.start();
                cThread.start();
            }
        }, 250);
    }
}
