package com.ayomi.splash;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends Activity {
    private long ms=0, splashT=1000;
    private boolean splashActive=true, paused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor(R.color.colorfunfun);

        final ConstraintLayout cl = findViewById(R.id.cl);
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms<splashT) {
                        if (!paused)
                            ms=ms+100;
                        sleep(100);
                    }
                } catch (Exception e) {

                } finally {
                   if (!isOnline()) {
                       Snackbar snackbar = Snackbar.make(cl,"No Network", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               recreate();
                           }
                       });
                       snackbar.show();
                   } else {
                       goMain();
                   }
                }
            }
        };
        thread.start();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void setStatusBarColor(@ColorRes int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = ContextCompat.getColor(this, statusBarColor);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private void goMain() {
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
        finish();
    }
}