package com.example.midigiwordapps;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/json/contacts.json")
    Call<List<Model>> getGetails();
}

