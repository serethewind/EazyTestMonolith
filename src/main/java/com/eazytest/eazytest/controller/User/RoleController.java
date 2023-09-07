package com.eazytest.eazytest.controller.User;

import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.service.user.role.RoleServiceInteface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/eazytest/role")
@AllArgsConstructor
public class RoleController {
    private RoleServiceInteface roleService;

    @PostMapping("/examiner")
    ResponseEntity<ResponseDto> assignExaminerRole(@RequestParam Long userId) {
        return new ResponseEntity<>(roleService.assignExaminerRole(userId), HttpStatus.OK);
    }

    @PostMapping("/participant")
    ResponseEntity<ResponseDto> assignParticipantRole(@RequestParam Long userId) {
        return new ResponseEntity<>(roleService.assignParticipantRole(userId), HttpStatus.OK);
    }
}
