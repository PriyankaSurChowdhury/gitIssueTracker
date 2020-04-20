package com.task.priyanka.gitIssueTracker.activity;

import android.content.Context;
import android.os.Bundle;

import com.task.priyanka.gitIssueTracker.R;
import com.task.priyanka.gitIssueTracker.adapter.IssueRecyclerViewAdapter;
import com.task.priyanka.gitIssueTracker.model.IssueModel;
import com.task.priyanka.gitIssueTracker.viewModel.IssueViewModel;

import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IssueActivity extends AppCompatActivity {
    private RecyclerView rv_issue;
    Context context;
    IssueRecyclerViewAdapter issueRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        context = this;

        initRecyclerSetup();
        callIssueViewModel();
    }

    private void callIssueViewModel() {
        IssueViewModel model = ViewModelProviders.of(this).get(IssueViewModel.class);

        model.getIssueList().observe(this, new Observer<List<IssueModel>>() {
            @Override
            public void onChanged(@Nullable List<IssueModel> issueModels) {

                //NOTE
                //The sorting is done on the basis of UpdateDate for which I have implemented Comparable in IssueModel
                Collections.sort(issueModels);
                issueRecyclerViewAdapter = new IssueRecyclerViewAdapter(IssueActivity.this, issueModels);
                rv_issue.setAdapter(issueRecyclerViewAdapter);
            }
        });
    }

    private void initRecyclerSetup() {
        rv_issue = findViewById(R.id.rv_issue);
        rv_issue.setHasFixedSize(true);
        rv_issue.setLayoutManager(new LinearLayoutManager(this));
    }
}
