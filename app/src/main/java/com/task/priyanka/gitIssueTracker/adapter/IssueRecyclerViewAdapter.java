package com.task.priyanka.gitIssueTracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.priyanka.gitIssueTracker.R;
import com.task.priyanka.gitIssueTracker.activity.CommentsActivity;
import com.task.priyanka.gitIssueTracker.dialog.DialogNoComments;
import com.task.priyanka.gitIssueTracker.model.IssueModel;
import com.task.priyanka.gitIssueTracker.utils.SharedPrefController;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class IssueRecyclerViewAdapter extends RecyclerView.Adapter<IssueRecyclerViewAdapter.IssueViewHolder> {
    Context context;
    List<IssueModel> issueModelList;

    public IssueRecyclerViewAdapter(Context context, List<IssueModel> issueModelList) {
        this.context = context;
        this.issueModelList = issueModelList;
    }

    @NonNull
    @Override
    public IssueRecyclerViewAdapter.IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_issue_list, parent, false);
        return new IssueViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        IssueModel issueModel = issueModelList.get(position);

        holder.tv_issueHeading.setText(issueModel.getTitle());
        holder.tv_issueBody.setText(issueModel.getBody());
        holder.tv_numOfComments.setText(context.getResources().getString(R.string.comments) + issueModel.getComments());
        holder.tv_reportedBy.setText(context.getResources().getString(R.string.reported_by)+issueModel.getUser().getLogin());
    }

    @Override
    public int getItemCount() {
        return issueModelList.size();
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_issueHeading, tv_issueBody, tv_tap, tv_numOfComments, tv_reportedBy;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_issueHeading = itemView.findViewById(R.id.tv_issueHeading);
            tv_issueBody = itemView.findViewById(R.id.tv_issueBody);
            tv_tap = itemView.findViewById(R.id.tv_tap);
            tv_numOfComments = itemView.findViewById(R.id.tv_numOfComments);
            tv_reportedBy = itemView.findViewById(R.id.tv_reportedBy);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = this.getAdapterPosition();

            if (issueModelList.get(pos).getComments() == 0) {

                DialogNoComments dialogNoComments = new DialogNoComments(context);
                if (context != null && !((Activity) context).isFinishing())
                    dialogNoComments.setCanceledOnTouchOutside(false);
                    dialogNoComments.show();

            } else {
                Intent intent = new Intent(context, CommentsActivity.class);
                String url = issueModelList.get(pos).getCommentsUrl();
                SharedPrefController.getSharedPreferencesController(context).setString(context.getResources().getString(R.string.comment_list), url);
                context.startActivity(intent);
            }
        }
    }
}
