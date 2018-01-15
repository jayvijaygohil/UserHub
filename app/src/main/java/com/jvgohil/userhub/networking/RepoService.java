package com.jvgohil.userhub.networking;

import com.jvgohil.userhub.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jayvijay on 2018-01-14.
 */

public interface RepoService {
    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();
}
