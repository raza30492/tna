package com.jazasoft.tna.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    private String name;

    private String username;

    @NotNull
    @Pattern(regexp=".+@.+\\..+", message="Incorrect email!")
    private String email;

    @JsonIgnore
    private String password;

    @NotNull
    private String role;

    @NotNull
    @Pattern(regexp="[0-9]{10}", message="Incorrect mobile")
    private String mobile;

    private boolean freesed;

    public UserDto() {
    }

    public UserDto(String name, String username, String email, String role, boolean freesed) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.freesed = freesed;
    }

    public UserDto(String name, String username, String email, String role, String mobile) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.mobile = mobile;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFreesed() {
        return freesed;
    }

    public void setFreesed(boolean freesed) {
        this.freesed = freesed;
    }

    @Override
    public String toString() {
        return "UserDto{" + "id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + ", mobile=" + mobile + '}';
    }
}
