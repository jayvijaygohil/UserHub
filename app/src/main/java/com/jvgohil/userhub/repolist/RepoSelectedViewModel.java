package com.jvgohil.userhub.repolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

import com.jvgohil.userhub.model.Repo;
import com.jvgohil.userhub.networking.RepoApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jayvijay on 2018-01-17.
 */

public class RepoSelectedViewModel extends ViewModel {
    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();

    // Get the selected repository's object
    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    // Set selected repository's object
    void setSelectedRepo (Repo repo) {
        selectedRepo.setValue(repo);
    }

    private Call<Repo> repoCall;

    // Save to bundle data necessary for restoring selectedRepo in case of a process death
    public void saveToBundle(Bundle outState) {
        if (selectedRepo.getValue() != null) {
            outState.putStringArray("repo_details", new String[]{selectedRepo.getValue().owner.login, selectedRepo.getValue().name});
        }
    }


    // Restore selectedRepo from savedInstanceState to restore detail fragment to its state before process death
    public void restoreFromBundle(Bundle savedInstanceState) {
        if (selectedRepo.getValue() == null) {
            // We only care about restoring if we don't have a selected Repo already set
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_details")) {
                loadRepo(savedInstanceState.getStringArray("repo_details"));
            }
        }
    }

    // Load selectedRepo data in situations where fragment is recreated
    private void loadRepo(String[] repoDetails) {
        repoCall = RepoApi.getInstance().getRepo(repoDetails[0], repoDetails[1]);
        repoCall.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                selectedRepo.setValue(response.body());
                repoCall = null;
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error Loading Selected Repo: ", t);
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
