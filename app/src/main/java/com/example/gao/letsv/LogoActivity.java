package com.example.gao.letsv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gangchang on 2018/4/24.
 */

public class LogoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasiduo);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(LogoActivity.this, MainActivity.class);
                LogoActivity.this.startActivity(mainIntent);
                LogoActivity.this.finish();
            }
        }, 3000);
    }
}
