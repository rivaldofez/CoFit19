package com.example.cofit19;

import com.example.cofit19.Model.Results;
import com.example.cofit19.Remote.IGoogleAPIService;
import com.example.cofit19.Remote.RetrofitClient;
import com.example.cofit19.Remote.RetrofitScalarsClient;

public class Common {

    public static Results currentResult;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleAPIService getGoogleAPIService(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }

    public static IGoogleAPIService getGoogleAPIServiceScalars(){
        return RetrofitScalarsClient.getScalarClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
