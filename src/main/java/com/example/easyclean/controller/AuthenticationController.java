package com.example.easyclean.controller;

import com.example.easyclean.dto.input.RegisterInputDTO;
import com.example.easyclean.dto.response.ApiResponse;
import com.example.easyclean.exception.UserExistsException;
import com.example.easyclean.service.authentication.AuthenticationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private  AuthenticationServiceImpl authenticationService;

    private static final Logger L = LogManager.getLogger(AuthenticationController.class);

    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody @Valid RegisterInputDTO u) {
        try {
            return ResponseEntity.ok(authenticationService.registerUser(u));
        } catch (NullPointerException e) {
            L.error(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, HttpStatus.UNAUTHORIZED.value(), "You are not logged in."));
        } catch (DataIntegrityViolationException e) {
            L.error("Error creating user", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, HttpStatus.FORBIDDEN.value(), "This User already exists"));
        } catch (Exception e) {
            L.error("Error creating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error Creating User"));
        }
    }
}
