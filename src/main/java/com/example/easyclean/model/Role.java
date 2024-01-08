package com.example.easyclean.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    private Set<Permission> permissions = new HashSet<>();

    @Embedded
    private CreateAndUpdateDetails createAndUpdateDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateAndUpdateDetails getCreateAndUpdateDetails() {
        return createAndUpdateDetails;
    }

    public void setCreateAndUpdateDetails(CreateAndUpdateDetails createAndUpdateDetails) {
        this.createAndUpdateDetails = createAndUpdateDetails;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Roles{" + "id=" + id + ", name=" + name + '}';
    }

}
