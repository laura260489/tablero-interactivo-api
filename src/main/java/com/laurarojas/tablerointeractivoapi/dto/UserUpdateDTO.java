package com.laurarojas.tablerointeractivoapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserUpdateDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private List<String> roles;
    private String message;
    private int statusCode;
}
