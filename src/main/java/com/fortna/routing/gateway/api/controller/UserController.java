package com.fortna.routing.gateway.api.controller;

import com.fortna.routing.gateway.api.dto.UserData;
import com.fortna.routing.gateway.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser (@RequestBody UserData userCreateRequest) {
        userService.createUser(userCreateRequest);
    }


    @GetMapping
    public String getUser(){
        return "dupa";
    }

}