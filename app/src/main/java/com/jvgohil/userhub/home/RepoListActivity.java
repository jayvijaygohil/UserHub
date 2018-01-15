package com.jvgohil.userhub.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jvgohil.userhub.R;

public class RepoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.RepoListFragmentContainer, new RepoListFragment())
                                       .commit();
        }
    }
}
