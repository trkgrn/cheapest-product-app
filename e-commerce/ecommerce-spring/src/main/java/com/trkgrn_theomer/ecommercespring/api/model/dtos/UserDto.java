package com.trkgrn_theomer.ecommercespring.api.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String username;
    private String name;
    private String role;
    private String mail;
    private String telNumber;
    private Long userId;
}
