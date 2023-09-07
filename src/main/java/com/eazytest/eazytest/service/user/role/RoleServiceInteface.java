package com.eazytest.eazytest.service.user.role;


import com.eazytest.eazytest.dto.general.ResponseDto;

public interface RoleServiceInteface {

    ResponseDto assignParticipantRole(Long userId);

    ResponseDto assignExaminerRole(Long userId);
}
