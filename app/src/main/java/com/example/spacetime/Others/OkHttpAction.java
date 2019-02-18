package com.example.spacetime.Others;

import okhttp3.MediaType;

public class OkHttpAction {
    public final String web = "http://59.110.172.61/v2/api-docs";
    private static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    private static final MediaType URLENCODED = MediaType
            .parse("application/x-www-form-urlencoded; charset=UTF-8");
}
