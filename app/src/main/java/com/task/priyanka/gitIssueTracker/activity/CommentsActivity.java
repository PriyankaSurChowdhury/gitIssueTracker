package com.task.priyanka.gitIssueTracker.activity;

import android.os.Bundle;

import com.task.priyanka.gitIssueTracker.R;
import com.task.priyanka.gitIssueTracker.adapter.CommentsRecyclerAdapter;
import com.task.priyanka.gitIssueTracker.model.CommentModel;
import com.task.priyanka.gitIssueTracker.viewModel.CommentViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsActivity extends AppCompatActivity {
    public final static String TAG = CommentsActivity.class.getSimpleName();
    RecyclerView rv_comment;
    CommentsRecyclerAdapter commentsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        initRecyclerSetup();
        callCommentViewModel();

    }

    private void callCommentViewModel() {
        CommentViewModel model = ViewModelProviders.of(this).get(CommentViewModel.class);

        model.getCommentsList().observe(this, new Observer<List<CommentModel>>() {
            @Override
            public void onChanged(@Nullable List<CommentModel> commentModels) {
                commentsRecyclerAdapter = new CommentsRecyclerAdapter(CommentsActivity.this, commentModels);
                rv_comment.setAdapter(commentsRecyclerAdapter);
                commentsRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerSetup() {
        rv_comment = findViewById(R.id.rv_comment);
        rv_comment.setHasFixedSize(true);
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
    }
}
