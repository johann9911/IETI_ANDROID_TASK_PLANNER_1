package com.example.android_task_planner.network.dto;

import java.util.Date;

public class TokenDto {
    String accessToken;

    public TokenDto( String accessToken )
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
