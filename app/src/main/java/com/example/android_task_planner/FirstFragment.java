package com.example.android_task_planner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.android_task_planner.databinding.FragmentFirstBinding;
import com.example.android_task_planner.network.RetrofitGenerator;
import com.example.android_task_planner.network.dto.LoginDto;
import com.example.android_task_planner.network.dto.TaskDto;
import com.example.android_task_planner.network.dto.TokenDto;
import com.example.android_task_planner.network.service.AuthService;
import com.example.android_task_planner.network.service.TaskService;
import com.example.android_task_planner.network.storage.SharedPreferencesStorage;
import com.example.android_task_planner.network.storage.Storage;

import java.util.List;

import retrofit2.Retrofit;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    Storage storage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                sendAuthRequest();
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                viewListTask();
            }
        });


        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SHARED_preferences", Context.MODE_PRIVATE);
        storage = new SharedPreferencesStorage(sharedPreferences);
    }

    private void viewListTask() {
        Retrofit retrofit= RetrofitGenerator.getInstace(storage);
        TaskService taskService = retrofit.create(TaskService.class);
        Action1<List<TaskDto>> successAction = list ->  listOfTask(list);
        Action1<Throwable> failedAction = throwable ->  Log.e("Developer", "Auth error:",throwable);
        taskService.listTasks()
                .observeOn(Schedulers.from(ContextCompat.getMainExecutor(requireContext())))
                .subscribe(successAction, failedAction);
    }

    private void listOfTask(List<TaskDto> list) {
        Log.d("Developer", "list:" + list.toString());
    }

    private void sendAuthRequest() {
        Retrofit retrofit= RetrofitGenerator.getInstace(storage);
        AuthService authService= retrofit.create(AuthService.class);
        LoginDto loginDto = new LoginDto("santiago@mail.com", "passw0rd");
        Action1<TokenDto> successAction = tokenDto ->  onSuccess(tokenDto.getAccessToken());
        Action1<Throwable> failedAction = throwable ->  Log.e("Developer", "Auth error:",throwable);
        authService.auth(loginDto)
                .observeOn(Schedulers.from(ContextCompat.getMainExecutor(requireContext())))
                .subscribe(successAction, failedAction);

    }

    private void onSuccess(String token){
        Log.d("Developer", "TOKERDTO:" + token);
        //getActivity().runOnUiThread(() -> binding.textviewFirst.setText(token));
        binding.textviewFirst.setText(token);
        storage.setToken(token);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}