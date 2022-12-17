package com.fortna.routing.gateway.api.controller;

import com.fortna.routing.gateway.api.dto.UserData;
import com.fortna.routing.gateway.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserData userCreateRequest) {
        userService.createUser(userCreateRequest);
    }


    @GetMapping( produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasRole('admin')")
    public String getUser() {
        return "dupa";
    }


    @GetMapping("/a3")
    @PreAuthorize("hasAuthority('authority-1')")
    public String getUser3() {
        return "hasAuthority(ad)";
    }



}