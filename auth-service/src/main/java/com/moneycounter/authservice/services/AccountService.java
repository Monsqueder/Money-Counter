package com.moneycounter.authservice.services;

import com.moneycounter.authservice.exceptions.UsernameAlreadyExistsException;
import com.moneycounter.authservice.models.Account;
import com.moneycounter.authservice.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new UsernameAlreadyExistsException(account.getUsername());
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public String getAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
