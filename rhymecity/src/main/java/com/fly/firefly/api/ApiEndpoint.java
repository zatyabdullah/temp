package com.fly.firefly.api;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {

    @Override
    public String getUrl() {
        //return "https://api.github.com";
       //return "http://api.github.com";
       return "http://fyapi.me-tech.com.my";
        //return "http://sheetsu.com";

    }

    @Override
    public String getName() {
        return "Production Endpoint";
    }
    //
}

//http://sheetsu.com/apis/c4182617