package com.example.api_disney.controller;

import com.example.api_disney.config.utility.JWTUtility;
import com.example.api_disney.controller.payload.ApiResponse;
import com.example.api_disney.controller.payload.LoginRequest;
import com.example.api_disney.controller.payload.SignUpRequest;
import com.example.api_disney.model.UserEntity;
import com.example.api_disney.service.CustomUserDetailsService;
import com.example.api_disney.service.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private MailService mailService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginRequest loginRequest) throws Exception{
        System.out.println("-----------------------------x-----------------------------");
        System.out.println("Te estas logeando");
        System.out.println("Username: "+loginRequest.getUsername());
        System.out.println("Password: "+loginRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {

            System.out.println("Datos invalidos");
            System.out.println("-----------------------------x-----------------------------");
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Invalid password or username"),HttpStatus.BAD_REQUEST);
        }

        System.out.println("Logeo Exitoso");
        System.out.println("-----------------------------x-----------------------------");
        final UserDetails userDetails
                = userService.loadUserByUsername(loginRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,token),HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> saveUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
        if (Boolean.TRUE.equals(userService.existsByUsername(signUpRequest.getUsername()))) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Username is already taken"),HttpStatus.BAD_REQUEST);
        }
        if (Boolean.TRUE.equals(userService.existsByEmail(signUpRequest.getEmail()))) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Email is already taken"),HttpStatus.BAD_REQUEST);
        }
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(signUpRequest,UserEntity.class);

        userService.save(userEntity);
        mailService.sendTextEmail(signUpRequest.getFirst_name(), signUpRequest.getLast_name(), signUpRequest.getUsername(),signUpRequest.getEmail());

        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"User registered successfully"),HttpStatus.OK);
    }
}
