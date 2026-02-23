package com.store.user_service.dto;


import com.store.user_service.entity.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto addressDto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
