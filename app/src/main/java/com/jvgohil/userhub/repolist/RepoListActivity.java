package com.jvgohil.userhub.repolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jvgohil.userhub.R;

public class RepoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);

        // Check if the activity is being loaded for the first time into the memory
        if (savedInstanceState == null) {
            // Attach fragment to the activity
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.fragment_container, new RepoListFragment())
                                       .commit();
        }
    }
}
