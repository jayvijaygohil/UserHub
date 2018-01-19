package com.jvgohil.userhub.repolist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jvgohil.userhub.R;
import com.jvgohil.userhub.model.Repo;
import com.jvgohil.userhub.repodetail.RepoDetailFragment;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jayvijay on 2018-01-13.
 */

public class RepoListFragment extends Fragment implements RepoSelectedListener {


    @BindView(R.id.repo_list_rv)
    RecyclerView repoListView;
    @BindView(R.id.error_message_tv)
    TextView errorMessageTextView;
    @BindView(R.id.repo_list_loading_pb)
    ProgressBar repoListLoadingView;

    private Unbinder unbinder;
    private RepoListFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout into the fragment
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);

        // Store Unbinder for releasing it from the memory when fragment is destroyed
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // Initialize a RepoListFragmentViewModel object
        viewModel = ViewModelProviders.of(this).get(RepoListFragmentViewModel.class);

        // Setup RecyclerView
        repoListView
                .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        repoListView.setAdapter(new RepoListAdapter(viewModel, this, this));
        repoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        repoListView.setHasFixedSize(true);

        // Set Observables
        observeViewModel();
    }

    @Override
    public void onRepoSelected(Repo repo) {
        // Get activity's  RepoSelectedViewModel to save the selected repo
        RepoSelectedViewModel repoSelectedViewModel = ViewModelProviders.of(getActivity()).get(RepoSelectedViewModel.class);
        repoSelectedViewModel.setSelectedRepo(repo);

        // Load detail fragment when a repo is selected
        getActivity().getSupportFragmentManager().beginTransaction()
                     .replace(R.id.fragment_container, new RepoDetailFragment())
                     .addToBackStack(null)
                     .commit();
    }

    private void observeViewModel() {

        // Update UI in case of: new data has been fetched
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                repoListView.setVisibility(View.VISIBLE);
            }
        });

        // Update UI in case of: an error while fetching new data
        viewModel.getError().observe(this, isError -> {
            //noinspection ConstantConditions
            if (isError) {
                errorMessageTextView.setVisibility(View.VISIBLE);
                errorMessageTextView.setText(R.string.api_error_repos);
                repoListView.setVisibility(View.GONE);
            } else {
                errorMessageTextView.setVisibility(View.GONE);
                errorMessageTextView.setText(null);
            }
        });

        // Update UI in case of: new data is in the process of being fetched
        viewModel.getLoading().observe(this, isLoading -> {
            //noinspection ConstantConditions
            repoListLoadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                repoListView.setVisibility(View.GONE);
                errorMessageTextView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unbind views to prevent views from leaking when fragment view is destroyed
        // Note: The Fragment view is destroyed and not the Fragment as a whole
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
