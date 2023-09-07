package com.eazytest.eazytest.service.user.auth;

import com.eazytest.eazytest.dto.User.UserLoginDto;
import com.eazytest.eazytest.dto.User.UserRegisterDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthServiceInterface {

   ResponseEntity<ResponseDto> registerUser(UserRegisterDto userRegister);
   ResponseEntity<ResponseDto> loginUser(UserLoginDto userLoginDto);
}
