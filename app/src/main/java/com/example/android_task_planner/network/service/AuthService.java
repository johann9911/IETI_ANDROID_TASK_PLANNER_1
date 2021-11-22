package com.example.android_task_planner.network.service;

import com.example.android_task_planner.network.dto.LoginDto;
import com.example.android_task_planner.network.dto.TokenDto;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthService {
    @POST("auth")
    Observable<TokenDto> auth(@Body LoginDto loginDto);
}
