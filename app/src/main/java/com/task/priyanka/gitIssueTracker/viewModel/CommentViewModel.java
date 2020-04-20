package com.task.priyanka.gitIssueTracker.viewModel;

import android.app.Application;
import android.util.Log;

import com.task.priyanka.gitIssueTracker.R;
import com.task.priyanka.gitIssueTracker.client.ApiClient;
import com.task.priyanka.gitIssueTracker.model.CommentModel;
import com.task.priyanka.gitIssueTracker.utils.SharedPrefController;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CommentViewModel extends AndroidViewModel {
    private final static String TAG = CommentViewModel.class.getSimpleName();
    private MutableLiveData<List<CommentModel>> mutableLiveDataComment;

    public CommentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CommentModel>> getCommentsList() {

        if (mutableLiveDataComment == null) {
            mutableLiveDataComment = new MutableLiveData<List<CommentModel>>();

            String url = SharedPrefController.getSharedPreferencesController(getApplication()).getStringValue(getApplication().getResources().getString(R.string.comment_list));
            String commentUrl = url.replace(getApplication().getResources().getString(R.string.remove_url), "");

            //we will load it asynchronously from server in this method
            loadComments(commentUrl);
        }

        //finally we will return the list
        return mutableLiveDataComment;
    }

    private void loadComments(String commentUrl) {
        ApiClient.ApiInterface apiInterface = ApiClient.get(ApiClient.ApiInterface.BASE_URL).create(ApiClient.ApiInterface.class);
        apiInterface.getCommentsList(commentUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<CommentModel>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<CommentModel>> response) {
                        try {
                            if (response.code() == 200) {
                                mutableLiveDataComment.setValue(response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error---" + e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
