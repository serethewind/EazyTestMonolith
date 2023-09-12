package com.eazytest.eazytest.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoUpdateDto {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
