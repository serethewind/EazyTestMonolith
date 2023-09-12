package com.eazytest.eazytest.service.user.auth;

import com.eazytest.eazytest.config.JWTService;
import com.eazytest.eazytest.dto.user.UserLoginDto;
import com.eazytest.eazytest.dto.user.UserRegisterDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.UserResponseDto;
import com.eazytest.eazytest.entity.userType.RoleEnum;
import com.eazytest.eazytest.entity.userType.UserType;
import com.eazytest.eazytest.entity.security.Token;
import com.eazytest.eazytest.entity.security.TokenType;
import com.eazytest.eazytest.repository.user.UserRepository;
import com.eazytest.eazytest.repository.security.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthServiceInterface {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private TokenRepository tokenRepository;

    @Override
    public ResponseEntity<ResponseDto> registerUser(UserRegisterDto userRegister) {

        //check whether the username and the email are unique
        if (userRepository.existsByUsername(userRegister.getUsername()) || userRepository.existsByEmail(userRegister.getEmail())) {
            return new ResponseEntity<>(
                    ResponseDto
                            .builder()
                            .message("The username or email is already in use by another user.")
                            .userResponseDto(null)
                            .build()
                    , HttpStatus.CONFLICT);
        }

        UserType user = UserType.builder()
                .username(userRegister.getUsername())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .roleEnums(Collections.singleton(RoleEnum.USER))
                .build();

        userRepository.save(user);

        return new ResponseEntity<>(ResponseDto
                .builder()
                .message("Account successfully created")
                .userResponseDto(UserResponseDto
                        .builder()
                        .userId(user.getId())
                        .userName(user.getUsername())
                        .build())
                .build(),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDto> loginUser(UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserType user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        String token = jwtService.generateToken(authentication.getName());

        revokeValidTokens(user);
        Token tokenEntity = Token.builder()
                .userType(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(tokenEntity);

        return ResponseEntity.ok(ResponseDto.builder()
                .message("Logged in successfully. Token is " + token)
                .userResponseDto(UserResponseDto.builder()
                        .userId(user.getId())
                        .userName(user.getUsername())
                        .build())
                .build());
    }

    private void revokeValidTokens(UserType user) {
        List<Token> tokenList = tokenRepository.findAllValidTokensByUser(user.getId());
        if (tokenList.isEmpty())
            return;
        tokenList.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(tokenList);
    }
}
