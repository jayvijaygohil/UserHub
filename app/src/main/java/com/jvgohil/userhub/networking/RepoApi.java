package com.jvgohil.userhub.networking;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Jayvijay on 2018-01-14.
 */

public class RepoApi {

    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit;
    private static RepoService repoService;

    private RepoApi() {

    }

    private static void initializeRetrofit() {
        // initialize retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public static RepoService getInstance() {
        // return repoService if already initialized
        if (repoService != null) {
            return repoService;
        }

        // initialize retrofit if null
        if (retrofit == null) {
            initializeRetrofit();
        }

        // initialize repoService
        repoService = retrofit.create(RepoService.class);

        // return repoService
        return repoService;
    }
}
