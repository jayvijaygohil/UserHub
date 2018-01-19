package com.jvgohil.userhub.repolist;

import android.support.v7.util.DiffUtil;

import com.jvgohil.userhub.model.Repo;

import java.util.List;

/**
 * Created by Jayvijay on 2018-01-19.
 */

public class RepoItemDiffCallbacks extends DiffUtil.Callback {

    private List<Repo> oldRepoList, newRepoList;

    public RepoItemDiffCallbacks(List<Repo> oldRepoList, List<Repo> newRepoList) {
        this.oldRepoList = oldRepoList;
        this.newRepoList = newRepoList;
    }

    @Override
    public int getOldListSize() {
        return oldRepoList == null ? 0 : oldRepoList.size();
    }

    @Override
    public int getNewListSize() {
        return newRepoList == null ? 0 : newRepoList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRepoList.get(oldItemPosition).equals(newRepoList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }
}
