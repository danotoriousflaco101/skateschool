package com.flaco.skateschool.dto;

import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO extends UserDTO {
    @NotNull
    private SkateStyle skateStyle;

    @NotNull
    private SkillLevel skillLevel;

    private Integer bookingsCount;
}