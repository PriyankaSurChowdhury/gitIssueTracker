package com.task.priyanka.gitIssueTracker.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.task.priyanka.gitIssueTracker.client.ApiClient;
import com.task.priyanka.gitIssueTracker.model.IssueModel;
import com.task.priyanka.gitIssueTracker.utils.AppConstant;
import com.task.priyanka.gitIssueTracker.utils.SharedPrefController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class IssueViewModel extends AndroidViewModel {

    public final static String TAG = IssueViewModel.class.getSimpleName();
    private MutableLiveData<List<IssueModel>> mutableLiveDataIssue;
    List<IssueModel> issueModelList = new ArrayList<>();

    public IssueViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<IssueModel>> getIssueList() {
        if (mutableLiveDataIssue == null) {
            mutableLiveDataIssue = new MutableLiveData<List<IssueModel>>();

            //fetching data from server
            loadIssues();
        }

        return mutableLiveDataIssue;
    }

    private void loadIssues() {
        ApiClient.ApiInterface apiInterface = ApiClient.get(ApiClient.ApiInterface.BASE_URL).create(ApiClient.ApiInterface.class);
        apiInterface.getIssueList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<IssueModel>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<IssueModel>> response) {
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                mutableLiveDataIssue.setValue(response.body());
                                issueModelList = response.body();

                                SharedPrefController.getSharedPreferencesController(getApplication()).setLongValue(AppConstant.LastSyncTime.LAST_SYNC_TIME, System.currentTimeMillis());

                                for(int i = 0; i < response.body().size(); i++) {
                                    SharedPreferences sharedpreferences = getApplication().getSharedPreferences("IssueData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("issueTitle", issueModelList.get(i).getTitle());
                                    editor.putString("issueBody", issueModelList.get(i).getBody());
                                    editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.DAYS.toDays(AppConstant.Time.DAYS15));
                                    editor.apply();
                                }
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
