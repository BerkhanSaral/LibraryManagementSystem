package com.tpe.controller;


import com.tpe.domain.Owner;
import com.tpe.dto.OwnerDTO;
import com.tpe.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    //save an Owner
    //http://localhost:8080/owners/save + json + post
    //return : message
    @PostMapping("/save")
    public ResponseEntity<String>saveOwner(@RequestBody OwnerDTO owner){
        ownerService.saveOwner(owner);
        return new ResponseEntity<>("Üye başarılı bir şekilde oluşturuldu", HttpStatus.CREATED);
    }

    //find all owner
    @GetMapping
    public ResponseEntity<List<Owner>>getAllOwners(){
        List<Owner>ownerList=ownerService.getAll();
        return ResponseEntity.ok(ownerList);
    }

    //find an owner by id--ödev
    //delete-update
}