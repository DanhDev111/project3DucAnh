package com.example.testspring.controller;

import java.security.Principal;

import com.example.testspring.dto.LoginUserDTO;
import com.example.testspring.services.JwTokenService;
import com.example.testspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/")
public class LoginAPI {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwTokenService jwtTokenService;

    @Autowired
    UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public LoginUserDTO me(Principal p,
                           // doc userdetails tra ve tu ham
                           // userDetailsService.loadUserByUsername(username);
                           // da hinh ep kieu ve loai class con
                           @AuthenticationPrincipal LoginUserDTO user) {
//		String username = p.getName();
//		UserDTO user = userService.findByUsername(username);
//		return user;
//		return (LoginUserDTO) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
        return user;
    }
    /// java -jar <ten_file.jar>
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/refresh-token")
//	public String login(
//			@RequestParam("refresh-token") String refresh) {
    // select from fresh_token
    // check expire time
    // lay username -> tao token moi
//		return jwtTokenService.createToken(username);
//	}

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // refreshToken = UUID.randomUUID().toString();
        // save refresh token - table(refresh_token)(username, refreshtoken,expired)
        // class TokenDTO(refreshToken,accesstoken)
        return jwtTokenService.createToken(username);
    }
}
