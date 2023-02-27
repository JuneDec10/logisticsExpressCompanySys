package com.example.springboot.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String mobile;
    private String username;
    private String password;
    private Long id;
    private Integer roleId;
    private String token;
}
