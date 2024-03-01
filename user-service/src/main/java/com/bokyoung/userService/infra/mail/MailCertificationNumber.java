package com.bokyoung.userService.infra.mail;

public class MailCertificationNumber {

    public static String getCertificationNumber () {

        String certificationNumber = "";

        for (int count = 0;  count < 4; count++) certificationNumber += (int) (Math.random() * 10);

        return certificationNumber;
    }
}
