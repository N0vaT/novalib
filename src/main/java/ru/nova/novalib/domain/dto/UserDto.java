package ru.nova.novalib.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;
    @NotBlank
    private String repeatUserPassword;
}
