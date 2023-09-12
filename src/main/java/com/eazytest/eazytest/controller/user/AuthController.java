package com.eazytest.eazytest.controller.user;

import com.eazytest.eazytest.config.LogoutService;
import com.eazytest.eazytest.dto.user.UserLoginDto;
import com.eazytest.eazytest.dto.user.UserRegisterDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.service.user.auth.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/eazytest/auth")
@AllArgsConstructor
public class AuthController {

    private AuthServiceImpl authService;
    private LogoutService logoutService;

    @PostMapping("/login")
    ResponseEntity<ResponseDto> userLogin(@RequestBody UserLoginDto userLoginDto) {
        return authService.loginUser(userLoginDto);
    }

    @PostMapping("/signup")
    ResponseEntity<ResponseDto> userSignup(@RequestBody UserRegisterDto userRegisterDto) {
        return authService.registerUser(userRegisterDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Authentication authentication) {
        logoutService.logout(request, response, authentication);
        ResponseDto responseDto = ResponseDto.builder().message("Logout successful")
                .userResponseDto(null)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
