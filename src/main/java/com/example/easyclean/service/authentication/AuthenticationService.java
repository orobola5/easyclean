package com.example.easyclean.service.authentication;


import com.example.easyclean.dto.input.LoginRequest;
import com.example.easyclean.dto.input.RegisterInputDTO;
import com.example.easyclean.dto.response.ApiResponse;
import com.example.easyclean.model.Users;

public interface AuthenticationService {
    ApiResponse registerUser(RegisterInputDTO userDto);
    ApiResponse login(LoginRequest loginRequest);
}