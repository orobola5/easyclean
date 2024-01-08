package com.example.easyclean.service.authentication;

import com.example.easyclean.dto.input.LoginRequest;
import com.example.easyclean.dto.input.RegisterInputDTO;
import com.example.easyclean.dto.response.ApiResponse;
import com.example.easyclean.dto.response.DefaultResponses;
import com.example.easyclean.model.CreateAndUpdateDetails;
import com.example.easyclean.model.DateTimeUtil;
import com.example.easyclean.model.Role;
import com.example.easyclean.model.Users;
import com.example.easyclean.repository.RoleRepository;
import com.example.easyclean.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UsersRepository userRepository;
//    @Autowired
//    private  PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public ApiResponse registerUser(RegisterInputDTO userDto) {
        // Check if the user with the given email already exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        //user.setAddress(userDto.getAddress());
        //set Role
        Optional<Role> role = roleRepository.findById( userDto.getRole());
        role.ifPresent(user::setRole);
        // Encode the user's password before saving it to the database
//        String rawPassword = "userPassword"; // Replace with the actual user password
//        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(userDto.getPassword());
        //set Creation details
        user.setCreateAndUpdateDetails(new CreateAndUpdateDetails(DateTimeUtil.getCurrentDate()));
        // Save the user to the database
        userRepository.save(user);
        return new ApiResponse(true, HttpStatus.OK.value(), "User created successfully", user);
    }

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        // Find the user by email
        Users user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("User with this email does not exist.");
        }
        // Check if the provided password matches the encoded password in the database
//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Invalid password.");
//        }
        // Generate and return a JWT token for authentication
        AuthenticationManager authenticationManager = null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //set authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return DefaultResponses.response200("Successful",user);
    }
}
