package com.task.priyanka.gitIssueTracker.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.task.priyanka.gitIssueTracker.viewModel.IssueViewModel;

import androidx.annotation.Nullable;

public class UpdateIssueDataService extends IntentService {

    public UpdateIssueDataService() {
        super("UpdateIssueDataService");
    }

    public static void startFetchDataService(Context context) {
        try {
            Intent intent = new Intent(context, UpdateIssueDataService.class);
            context.startService(intent);
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void discardOldData(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences("IssueData", Context.MODE_PRIVATE);
        if (!(sharedpreferences.getLong("ExpiredDate", -1) > System.currentTimeMillis())) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            IssueViewModel issueViewModel = new IssueViewModel(getApplication());
            issueViewModel.getIssueList();
        }
    }
}
