package com.jvgohil.userhub.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.jvgohil.userhub.model.Repo;
import com.jvgohil.userhub.networking.RepoApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jayvijay on 2018-01-14.
 */

public class RepoListFragmentViewModel extends ViewModel {
    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private Call<List<Repo>> repoCall;

    public RepoListFragmentViewModel() {
        fetchRepos();
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public LiveData<Boolean> getError() {
        return repoLoadError;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchRepos() {
        loading.setValue(true);
        repoCall = RepoApi.getInstance().getRepositories();
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repoLoadError.setValue(false);
                repos.setValue(response.body());
                loading.setValue(false);
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error Loading Repos: ", t);
                repoLoadError.setValue(true);
                loading.setValue(false);
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
