package com.bokyoung.userService.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckCertificationRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String certificationNumber;

    public CheckCertificationRequest() {

    }
}
