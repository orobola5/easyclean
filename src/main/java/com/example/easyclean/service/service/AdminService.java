package com.example.easyclean.service.service;

import com.example.easyclean.dto.input.RegisterInputDTO;
import com.example.easyclean.dto.input.RoleInputDTO;
import com.example.easyclean.dto.response.ApiResponse;
import com.example.easyclean.exception.UserNotFoundException;
import com.example.easyclean.model.Permission;

public interface AdminService {
    ApiResponse getUsers(int page, int size);
    ApiResponse getUsersByName(int page, int size, String userName);
    ApiResponse getUsersByEmail(int page, int size, String email);
    ApiResponse getUsersByPhone(int page, int size, String phoneNumber);
    ApiResponse updateUser(RegisterInputDTO u, Long id) throws UserNotFoundException;
    ApiResponse deleteUser(Long userId) throws UserNotFoundException;
    ApiResponse getUserById(Long id) throws UserNotFoundException;
    ApiResponse createRole(RoleInputDTO roleInput);
    ApiResponse getRoles(int page, int size);
    ApiResponse updateRole(RoleInputDTO r, Long id);
    ApiResponse createPermission(Permission permission);
    ApiResponse getPermissions(int page, int size);
    ApiResponse updatePermissions(Permission p);
}

