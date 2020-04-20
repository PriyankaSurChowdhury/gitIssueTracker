package com.task.priyanka.gitIssueTracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.task.priyanka.gitIssueTracker.R;
import com.task.priyanka.gitIssueTracker.service.UpdateIssueDataService;
import com.task.priyanka.gitIssueTracker.utils.AppConstant;
import com.task.priyanka.gitIssueTracker.utils.SharedPrefController;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 4000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        updateDataService();
        splashHandler();
    }

    private void updateDataService() {
        long lastSyncTime = SharedPrefController.getSharedPreferencesController(context).getLongValue(AppConstant.LastSyncTime.LAST_SYNC_TIME);
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastSyncTime;

        //24 hours check
        if (difference > AppConstant.Time.HOUR24) {
            UpdateIssueDataService.startFetchDataService(context);
        }
        //Discards data after every 15 days
        UpdateIssueDataService.discardOldData(context);

    }

    private void splashHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, IssueActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
