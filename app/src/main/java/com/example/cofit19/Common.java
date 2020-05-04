package com.example.cofit19;

import Model.MyPlaces;
import Model.Results;
import Remote.IGoogleAPIService;
import Remote.RetrofitClient;

public class Common {

    public static Results currentResult;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleAPIService getGoogleAPIService(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
