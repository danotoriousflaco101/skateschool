package com.flaco.skateschool.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusDTO {
    private Long userId;
    private boolean active;
}