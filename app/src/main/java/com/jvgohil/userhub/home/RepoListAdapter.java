package com.jvgohil.userhub.home;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jvgohil.userhub.R;
import com.jvgohil.userhub.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jayvijay on 2018-01-14.
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private final List<Repo> data = new ArrayList<>();

    public RepoListAdapter(RepoListFragmentViewModel viewModel, LifecycleOwner lifecycleOwner) {
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            data.clear();
            if (repos != null) {
                data.addAll(repos);
                notifyDataSetChanged(); //TODO: Implement DiffUtils after learning AutoValue
            }
        });

    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name) TextView repoName;
        @BindView(R.id.tv_repo_description) TextView repoDescription;
        @BindView(R.id.tv_forks) TextView repoForks;
        @BindView(R.id.tv_stars) TextView repoStars;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Repo repo) {
            repoName.setText(repo.name);
            repoDescription.setText(repo.description);
            repoForks.setText(String.valueOf(repo.forks));
            repoStars.setText(String.valueOf(repo.stars));
        }
    }
}
