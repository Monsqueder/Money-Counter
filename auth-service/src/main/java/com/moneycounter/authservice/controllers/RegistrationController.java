package com.moneycounter.authservice.controllers;

import com.moneycounter.authservice.models.Account;
import com.moneycounter.authservice.services.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/account")
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String getAccount() {
        return accountService.getAccount();
    }

    @PostMapping("/")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
}
