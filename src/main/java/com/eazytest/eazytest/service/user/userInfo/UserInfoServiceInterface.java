package com.eazytest.eazytest.service.user.userInfo;

import com.eazytest.eazytest.dto.user.UserInfoUpdateDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.UserResponseDto;

import java.util.List;

public interface UserInfoServiceInterface {

    ResponseDto updateUserInformation(Long userId, UserInfoUpdateDto userInfoUpdateDto);
    List<UserResponseDto> fetchAllUsers();
    UserResponseDto findUserById(Long id);
    /*
    findAllParticipantsByExamSessionId

     */
}
