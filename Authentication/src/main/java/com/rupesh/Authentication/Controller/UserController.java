package com.rupesh.Authentication.Controller;

import com.rupesh.Authentication.DTOs.LoginRequestDTO;
import com.rupesh.Authentication.DTOs.LoginResponseDTO;
import com.rupesh.Authentication.DTOs.RegisterRequestDTO;
import com.rupesh.Authentication.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/")
public class UserController {

    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> Register(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.CreateAccount(registerRequestDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return ResponseEntity.ok(userService.UserLogin(loginRequestDTO));
    }


}
