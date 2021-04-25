package com.algorand.auction.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController("user")
public class UserController {

    @GetMapping("/users/")
    public ResponseEntity getUserInfo() {
        return ok().build();
    }
}
