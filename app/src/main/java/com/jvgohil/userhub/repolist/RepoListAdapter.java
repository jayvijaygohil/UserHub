package com.jvgohil.userhub.repolist;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    // Provide a suitable constructor
    public RepoListAdapter(RepoListFragmentViewModel viewModel, LifecycleOwner lifecycleOwner) {
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            data.clear();
            if (repos != null) {
                data.addAll(repos);
                notifyDataSetChanged(); //TODO: Implement DiffUtils after learning AutoValue
            }
        });

        // Tell RecyclerView not to generate unique IDs for each view
        // Used when the data set can be represented with a unique identifier
        setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list, parent, false);
        return new RepoViewHolder(view);
    }

    // Replace the contents of a view with real data (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }


    // Return the stable ID for the item at position
    // Needed when setHasStableIds is set to true
    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    // Provide a reference to the views for each data item
    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name) TextView repoName;
        @BindView(R.id.tv_repo_description) TextView repoDescription;
        @BindView(R.id.tv_forks) TextView repoForks;
        @BindView(R.id.tv_stars) TextView repoStars;

        RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Method to populate views with its custom data
        void bind(Repo repo) {
            repoName.setText(repo.name);
            repoDescription.setText(repo.description);
            repoForks.setText(String.valueOf(repo.forks));
            repoStars.setText(String.valueOf(repo.stars));
        }
    }
}
