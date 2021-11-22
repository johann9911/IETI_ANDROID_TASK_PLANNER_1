package com.example.android_task_planner.network.service;

import com.example.android_task_planner.network.dto.TaskDto;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface TaskService {

    @GET("api/task/all")
    Observable<List<TaskDto>> listTasks();

    @POST("api/task")
    Observable<TaskDto> saveTask(@Body TaskDto taskDto);

    @PUT("api/task/{id}")
    Observable<TaskDto> updateTask(@Path("id") String id, @Body TaskDto taskDto);

    @POST("api/task/{id}/assign/{assignedTo}")
    Observable<TaskDto> assignTask(@Path ("id") String id, @Path("assignedTo") String assignedTo, @Body TaskDto taskDto);

    @DELETE("api/task/{id}")
    Observable<TaskDto> deleteTask(@Path ("id") String id,@Body TaskDto taskDto);
}
