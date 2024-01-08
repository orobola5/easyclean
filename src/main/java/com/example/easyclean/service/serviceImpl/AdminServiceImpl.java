package com.example.easyclean.service.serviceImpl;

import com.example.easyclean.repository.PermissionRepository;
import com.example.easyclean.repository.RoleRepository;
import com.example.easyclean.repository.UsersRepository;
import com.example.easyclean.dto.response.ApiResponse;
import com.example.easyclean.dto.response.DefaultResponses;
import com.example.easyclean.dto.input.RegisterInputDTO;
import com.example.easyclean.dto.input.RoleInputDTO;
import com.example.easyclean.exception.UserNotFoundException;
import com.example.easyclean.model.*;
import com.example.easyclean.service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    private EmailService emailService;

    public ApiResponse getUsers(int page, int size) {
        Page<Users> users = usersRepository.findAll(pageRequestForIdDesc(page, size));
        return DefaultResponses.response200("Successfully fetched all users", users.getContent(),
                users.getTotalElements());
    }
    private PageRequest pageRequestForIdDesc(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    }

    public ApiResponse getUsersByName(int page, int size, String userName) {

        if (userName != null && userName.toCharArray().length > 2) {
            System.out.println(userName);
            Page<Users> users = usersRepository.filterByName(userName, pageRequestForIdDesc(page, size));
            return DefaultResponses.response200("Successfully fetched all users with name :" + userName, users.getContent(),
                    users.getTotalElements());
        }

        Page<Users> users = usersRepository.findAll(pageRequestForIdDesc(page, size));
        return DefaultResponses.response200("Successfully fetched all users", users.getContent(),
                users.getTotalElements());
    }

    public ApiResponse getUsersByEmail(int page, int size, String email) {

        if (email != null && email.toCharArray().length > 2) {
            Page<Users> users = usersRepository.filterByEmail(email, pageRequestForIdDesc(page, size));
            return DefaultResponses.response200("Successfully fetched all users with email :" + email, users.getContent(),
                    users.getTotalElements());
        }

        Page<Users> users = usersRepository.findAll(pageRequestForIdDesc(page, size));
        return DefaultResponses.response200("Successfully fetched all users", users.getContent(),
                users.getTotalElements());
    }

    public ApiResponse getUsersByPhone(int page, int size, String phoneNumber) {

        if (phoneNumber != null && phoneNumber.toCharArray().length > 2) {
            Page<Users> users = usersRepository.filterByPhone(phoneNumber, pageRequestForIdDesc(page, size));
            return DefaultResponses.response200("Successfully fetched all users with phone :" + phoneNumber, users.getContent(),
                    users.getTotalElements());

        }

        Page<Users> users = usersRepository.findAll(pageRequestForIdDesc(page, size));
        return DefaultResponses.response200("Successfully fetched all users", users.getContent(),
                users.getTotalElements());
    }

    public ApiResponse updateUser(RegisterInputDTO u, Long id) throws UserNotFoundException {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This user does not exist"));

        // update values
        user.setEmail(u.getEmail());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setPhone(u.getPhone());
        Optional<Role> role = roleRepository.findById((long) u.getRole());

        role.ifPresent(user::setRole);
        user.getCreateAndUpdateDetails().setUpdatedTime(DateTimeUtil.getCurrentDate());
        user.getCreateAndUpdateDetails().setUpdatedby(new Users());

        return DefaultResponses.response200("Successfully Updated User With Id: " + id,
                usersRepository.save(user));
    }


    public ApiResponse deleteUser(Long userId) throws UserNotFoundException {
        usersRepository.findById(userId).ifPresent(user -> usersRepository.delete(user));
        return DefaultResponses.response200("Successfully deleted user: " + userId);
    }

    public ApiResponse getUserById(Long id) throws UserNotFoundException {
        return DefaultResponses.response200("successfully fetched user with id " + id,
                usersRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    // ROLES
    public ApiResponse createRole(RoleInputDTO roleInput) {
        Role role = new Role();
        role.setName(roleInput.getName());

        CreateAndUpdateDetails createAndUpdateDetails = new CreateAndUpdateDetails(DateTimeUtil.getCurrentDate());

        role.setCreateAndUpdateDetails(createAndUpdateDetails);

        Set<Permission> permissions = new HashSet<>();
        for (Long permissionId : roleInput.getPermissionsId()) {
            Optional<Permission> rolePermission = permissionRepository.findById(permissionId);

            rolePermission.ifPresent(permissions::add);

        }
        role.setPermissions(permissions);

        return DefaultResponses.response200("Successfully Created Role", roleRepository.save(role));
    }

    public ApiResponse getRoles(int page, int size) {
        Page<Role> roles = roleRepository.findAll(pageRequestForIdDesc(page, size));
        return DefaultResponses.response200("Successfully Fetched All Roles", roles.getContent(),
                roles.getTotalElements());
    }

    public ApiResponse updateRole(RoleInputDTO r, Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            return DefaultResponses.response400("Role with id : " + id + " does not exist");
        }
        role.get().getCreateAndUpdateDetails().setUpdatedTime(DateTimeUtil.getCurrentDate());
        role.get().getCreateAndUpdateDetails().setUpdatedby(new Users());

        role.get().setName(r.getName());

        Set<Permission> permissions = new HashSet<>();
        for (Long permissionId : r.getPermissionsId()) {
            Optional<Permission> rolePermission = permissionRepository.findById(permissionId);

            if (rolePermission.isPresent()) {
                permissions.add(rolePermission.get());
            }

        }

        role.get().setPermissions(permissions);
        return DefaultResponses.response200("Successfully updated role with id: " + id,
                roleRepository.save(role.get()));
    }

    // PERMISSION
    public ApiResponse createPermission(Permission permission) {
        return DefaultResponses.response200("Successfully Created Role", permissionRepository.save(permission));
    }

    public ApiResponse getPermissions(int page, int size) {
        Page<Permission> permissions = permissionRepository.findAll(pageRequestForIdDesc(page, size));
        return DefaultResponses.response200("Successfully Fetched All Permissions", permissions.getContent(),
                permissions.getTotalElements());
    }

    public ApiResponse updatePermissions(Permission p) {
        Permission permission = permissionRepository.findById(p.getId()).orElseThrow(EntityNotFoundException::new);
        permission.setName(p.getName());
        return new ApiResponse(true, "Successfully Updated permission with Id " + p.getId(),
                permissionRepository.save(permission));
    }







}
