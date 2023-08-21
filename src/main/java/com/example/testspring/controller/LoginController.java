package com.example.testspring.controller;

import com.example.testspring.dto.ResponseDTO;
import com.example.testspring.dto.UserDTO;
import com.example.testspring.entity.User;
import com.example.testspring.repository.UserRepo;
import com.example.testspring.services.JwTokenService;
import com.example.testspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class LoginController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @Autowired
    JwTokenService jwTokenService;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDTO me(Principal p
                           // doc userdetails tra ve tu ham
                           // userDetailsService.loadUserByUsername(username);
                           // da hinh ep kieu ve loai class con
                           ) {
		String username = p.getName();
		UserDTO user = userService.findByUsername(username);
		return user;

    }

    @PostMapping("/login")
    public ResponseDTO<String> login(HttpSession session,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password){
        //authen
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username,password));
        // if login success,jwt gen token string   else throw exception above
       return ResponseDTO.<String>builder()
               .status(200)
               .data(jwTokenService.createToken(username))
               .msg("OK").build();
    }
}
