package org.romain.demo2.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailValidationDto {

    protected String token;
    protected Boolean consentToDataUse;
    //protected String password;//
}
