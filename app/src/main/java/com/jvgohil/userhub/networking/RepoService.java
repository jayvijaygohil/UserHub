package com.jvgohil.userhub.networking;

import com.jvgohil.userhub.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jayvijay on 2018-01-14.
 */

public interface RepoService {
    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();

    @GET("orgs/{owner}/{name}")
    Call<Repo> getRepo(@Path("owner") String repoOwner, @Path("name") String repoName);
}
