package com.example.easyclean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
@Component
@Entity
@Table(name = "users")
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

//    @Column(name = "address", nullable = false, length = 100)
//    private String address;
    @Embedded
    @JsonIgnore
    private CreateAndUpdateDetails createAndUpdateDetails;

    public Users(String firstName, String lastName, String email, String password, String phone, Role role,String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
       // this.address=address;
    }
    public Users(Long id) {
        this.id = id;
    }

    public Users() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public CreateAndUpdateDetails getCreateAndUpdateDetails() {
        return createAndUpdateDetails;
    }

    public void setCreateAndUpdateDetails(CreateAndUpdateDetails createAndUpdateDetails) {
        this.createAndUpdateDetails = createAndUpdateDetails;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
             //   ", address='" + address + '\'' +
                ", role=" + role +
                ", createAndUpdateDetails=" + createAndUpdateDetails +
                '}';
    }
}
