package com.jvgohil.userhub.repolist;

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

    // Constructor
    public RepoListFragmentViewModel() {
        fetchRepos();
    }

    // Getters have a return type of LiveData<T> instead of MutableLiveData<T> to prevent instances
    // from setting class variable values unless a setter method is defined manually

    // Getter
    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    // Getter
    public LiveData<Boolean> getError() {
        return repoLoadError;
    }

    // Getter
    public LiveData<Boolean> getLoading() {
        return loading;
    }

    // fetch data using Retrofit / Update Instance Variables
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

    // Cancel any ongoing requests and set repoCall to null when Class instance is destroyed
    @Override
    protected void onCleared() {
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
