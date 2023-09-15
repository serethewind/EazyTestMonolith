package com.eazytest.eazytest.service.user.role;


import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.ResponseUserTypeDto;

public interface RoleServiceInteface {

    ResponseUserTypeDto assignParticipantRole(Long userId);

    ResponseUserTypeDto assignExaminerRole(Long userId);
}
