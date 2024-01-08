package com.example.easyclean.dto.input;

import java.util.HashSet;
import java.util.Set;

public class RoleInputDTO {
    private String name;
    private Set<Long> permissionsId = new HashSet<>();

    public RoleInputDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Set<Long> permissionsId) {
        this.permissionsId = permissionsId;
    }




}
