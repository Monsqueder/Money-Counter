package com.moneycounter.gatewayservice;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/username")
    public String getCurrentUserUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
