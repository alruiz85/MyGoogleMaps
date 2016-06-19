package es.alruiz.mymaps.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by AlfonsoRuiz on 19/06/2016.
 */

public class BaseActivity extends AppCompatActivity {

    public void showSnackBar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .show();
    }

}
