package com.eazytest.eazytest.controller.user;

import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.ResponseUserTypeDto;
import com.eazytest.eazytest.dto.general.UserResponseDto;
import com.eazytest.eazytest.dto.user.UserInfoUpdateDto;
import com.eazytest.eazytest.service.user.userInfo.UserInfoServiceInterface;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/eazytest/user")
@AllArgsConstructor
public class UserInfoController {

    private UserInfoServiceInterface userInfoService;

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> fetchAllUsers(){
        return new ResponseEntity<>(userInfoService.fetchAllUsers(), HttpStatus.OK);
    }


    @PutMapping("{user_id}")
    public ResponseEntity<ResponseDto> updateUserDto(@PathVariable("user_id") Long userId, @RequestBody UserInfoUpdateDto userInfoUpdateDto){
        return new ResponseEntity<>(userInfoService.updateUserInformation(userId, userInfoUpdateDto), HttpStatus.OK);
    }

    @GetMapping("find-by-user-type")
    public ResponseEntity<List<ResponseUserTypeDto>> findUserByUserType(@RequestParam(value = "userId") String userType){
        return new ResponseEntity<>(userInfoService.findUserByUserType(userType), HttpStatus.OK);
    }

    @GetMapping("{user_id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("user_id") Long userId){
        return new ResponseEntity<>(userInfoService.findUserById(userId), HttpStatus.OK);
    }


}
