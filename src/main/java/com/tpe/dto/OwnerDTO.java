package com.tpe.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OwnerDTO {

    @NotBlank(message = "Geçerli bir isim giriniz")
    private String name;

    @NotBlank(message = "Geçerli bir soyad giriniz")
    private String lastName;

    private String phone;

    @Email(message = "Geçerli bir email giriniz")
    private String email;
}