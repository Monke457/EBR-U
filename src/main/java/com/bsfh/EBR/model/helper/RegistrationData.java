package com.bsfh.EBR.model.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationData {
    private String username;
    private String email;
    private String password;
    private String passwordRepeat;
}
