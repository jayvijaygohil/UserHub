package com.jvgohil.userhub.home;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jayvijay on 2018-01-13.
 */

public class RepoListFragment extends Fragment {


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
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(RepoListFragmentViewModel.class);

        repoListView
                .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        repoListView.setAdapter(new RepoListAdapter(viewModel, this));
        repoListView.setLayoutManager(new LinearLayoutManager(getContext()));

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                repoListView.setVisibility(View.VISIBLE);
            }
        });
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
        viewModel.getLoading().observe(this, isLoading -> {
            //noinspection ConstantConditions
            repoListLoadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                repoListView.setVisibility(View.GONE);
                errorMessageTextView.setVisibility(View.GONE);
                repoListLoadingView.setVisibility(View.VISIBLE);
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
