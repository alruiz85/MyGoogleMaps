package es.alruiz.mymaps.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by AlfonsoRuiz on 14/07/2016.
 */
public class MyApplication extends Application {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}