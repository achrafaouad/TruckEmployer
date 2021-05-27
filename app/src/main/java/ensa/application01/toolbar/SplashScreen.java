package ensa.application01.toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

public class SplashScreen extends Activity {

    protected ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ;i< 10 ;i++){
                    progressBar.incrementProgressBy(10);

                    SystemClock.sleep(500);

                }
                mshow();

            }
        });
        thread.start();
    }

    public void mshow(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }
}