package com.fly.firefly.api;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {

    @Override
    public String getUrl() {
        //return "https://api.github.com";
       //return "http://api.github.com";
       return "http://fyapidev.me-tech.com.my/api";
        //return "http://sheetsu.com";

      //  return "http://103.249.85.102";

    }

    @Override
    public String getName() {
        return "Production Endpoint";
    }
    //
}

//http://sheetsu.com/apis/c4182617