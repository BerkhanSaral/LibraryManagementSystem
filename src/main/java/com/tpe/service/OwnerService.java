package com.tpe.service;

import com.tpe.domain.Owner;
import com.tpe.dto.OwnerDTO;
import com.tpe.exceptions.ConflictException;
import com.tpe.exceptions.OwnerNotFoundException;
import com.tpe.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;


    public void saveOwner(OwnerDTO ownerDTO){
        boolean isExistsByEmail=ownerRepository.existsByEmail(ownerDTO.getEmail());
        if (isExistsByEmail){
            throw new ConflictException("This Email is Exist : "+ownerDTO.getEmail());
        }
        Owner newOwner =new Owner();
        newOwner.setName(ownerDTO.getName());
        newOwner.setLastName(ownerDTO.getLastName());
        newOwner.setPhone(ownerDTO.getPhone());
        newOwner.setEmail(ownerDTO.getEmail());

        //Owner yeniOwner=new Owner(ownerDTO);


        ownerRepository.save(newOwner);
    }

    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }

    public Owner getOwnerById(Long id){
        return ownerRepository.findById(id).orElseThrow(()-> new OwnerNotFoundException("Üye Bulunamadı. ID : "+id));
    }
}