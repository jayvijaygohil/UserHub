package com.jvgohil.userhub.repodetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jvgohil.userhub.R;
import com.jvgohil.userhub.repolist.RepoSelectedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jayvijay on 2018-01-17.
 */

public class RepoDetailFragment extends Fragment {


    @BindView(R.id.repo_name_tv)
    TextView repoNameTextView;
    @BindView(R.id.forks_tv)
    TextView forksTextView;
    @BindView(R.id.starts_tv)
    TextView starsTextView;

    private Unbinder unbinder;
    private RepoSelectedViewModel repoSelectedViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout into the fragment
        View view = inflater.inflate(R.layout.fragment_repo_detail, container, false);

        // Store Unbinder for releasing it from the memory when fragment is destroyed
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // Create instance of RepoSelectedViewModel
        // Note: As communication is between different fragments in the same activity,
        // activity context is passed and not fragment context
        repoSelectedViewModel = ViewModelProviders.of(getActivity())
                                                  .get(RepoSelectedViewModel.class);

        // For restoring fragment state in case of a process death
        repoSelectedViewModel.restoreFromBundle(savedInstanceState);

        // Update UI
        displayRepo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Send bundle to ViewModel
        repoSelectedViewModel.saveToBundle(outState);
    }

    private void displayRepo() {
        // Update UI in case of: data change in repoSelectedViewModel instance
        repoSelectedViewModel.getSelectedRepo().observe(this, repo -> {
            if (repo != null) {
                repoNameTextView.setText(repo.name);
                forksTextView.setText(String.valueOf(repo.forks));
                starsTextView.setText(String.valueOf(repo.stars));
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
