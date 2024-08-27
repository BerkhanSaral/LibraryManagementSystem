package com.tpe.domain;

import com.tpe.dto.OwnerDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_owner")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Geçerli bir isim giriniz")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Geçerli bir soyad giriniz")
    @Column(nullable = false)
    private String lastName;

    private String phone;

    @Email(message = "Geçerli bir email giriniz")
    @Column(nullable = false,unique = true)
    private String email;

    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationDate=LocalDateTime.now();

    @OneToMany(mappedBy = "owner")
    private List<Book> books=new ArrayList<>();

//    public Owner(String name,String lastName,String phone,String email){
//        this.name=name;
//        this.lastName=lastName;
//        this.phone=phone;
//        this.email=email;
//    }


    public Owner(OwnerDTO ownerDTO) {
        this.name=ownerDTO.getName();
        this.lastName=ownerDTO.getLastName();
        this.phone=ownerDTO.getPhone();
        this.email=ownerDTO.getEmail();
    }
}