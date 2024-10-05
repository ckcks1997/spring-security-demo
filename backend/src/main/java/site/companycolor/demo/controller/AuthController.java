package site.companycolor.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import site.companycolor.demo.dto.ApiResponse;
import site.companycolor.demo.dto.AuthenticationRequest;
import site.companycolor.demo.dto.AuthenticationResponse;
import site.companycolor.demo.dto.UserDto;
import site.companycolor.demo.security.JwtUtil;
import site.companycolor.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
            UserDto userByUserId = userService.getUserByUserId(userDetails.getUsername());

            UserDto userDto = new UserDto();
            userDto.setUserId(userByUserId.getUserId());
            userDto.setName(userByUserId.getName());
            userDto.setAuthority(userDetails.getAuthorities().iterator().next().getAuthority());

            return ResponseEntity.ok(ApiResponse.success(userDto));
        }
        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(ApiResponse.success(new AuthenticationResponse(jwt)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signupUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(ApiResponse.success(createdUser));
    }
}