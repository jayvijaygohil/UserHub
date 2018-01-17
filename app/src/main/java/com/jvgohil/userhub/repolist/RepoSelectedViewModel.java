package com.jvgohil.userhub.repolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jvgohil.userhub.model.Repo;

/**
 * Created by Jayvijay on 2018-01-17.
 */

public class RepoSelectedViewModel extends ViewModel {
    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();

    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    void setSelectedRepo (Repo repo) {
        selectedRepo.setValue(repo);
    }

}
