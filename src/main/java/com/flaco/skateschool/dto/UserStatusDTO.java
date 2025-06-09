package com.flaco.skateschool.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusDTO { // DTO for user status
    private Long userId;
    private boolean active;
}