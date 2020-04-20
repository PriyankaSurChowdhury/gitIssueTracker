package com.task.priyanka.gitIssueTracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.task.priyanka.gitIssueTracker.R;

import androidx.annotation.NonNull;

public class DialogNoComments extends Dialog {
    Context context;
    TextView tv_checkLater;

    public DialogNoComments(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        setContentView(R.layout.dialog_no_comments);

        tv_checkLater = findViewById(R.id.tv_checkLater);
        tv_checkLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
