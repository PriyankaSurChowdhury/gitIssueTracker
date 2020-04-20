package com.task.priyanka.gitIssueTracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.priyanka.gitIssueTracker.R;
import com.task.priyanka.gitIssueTracker.model.CommentModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.CommentsViewHolder> {
    private Context context;
    List<CommentModel> commentModelList;

    public CommentsRecyclerAdapter(Context context, List<CommentModel> commentModelList) {
        this.context = context;
        this.commentModelList = commentModelList;
    }

    @NonNull
    @Override
    public CommentsRecyclerAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsRecyclerAdapter.CommentsViewHolder holder, int position) {
        CommentModel commentModel = commentModelList.get(position);

        holder.tv_username.setText(context.getResources().getString(R.string.posted_by)+commentModel.getUser().getLogin());
        holder.tv_comment.setText(commentModel.getBody());
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_comment;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_comment  = itemView.findViewById(R.id.tv_comment);
        }
    }
}
