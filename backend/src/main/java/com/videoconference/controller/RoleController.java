package com.videoconference.controller;

import com.videoconference.entity.Role;
import com.videoconference.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getRoles() {
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }
}
