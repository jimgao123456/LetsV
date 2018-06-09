package com.example.gao.letsv;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by dk150 on 2018/6/6.
 */

public class reading_BaseActivity extends AppCompatActivity {

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActionBar getSupportActiongBar(){
        ActionBar actionBar = super.getSupportActionBar();
        if(actionBar == null){
            throw new NullPointerException("ActiongBar was not initialized");
        }
        return actionBar;
    }
}
