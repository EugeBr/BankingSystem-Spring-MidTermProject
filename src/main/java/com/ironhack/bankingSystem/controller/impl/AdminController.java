package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.controller.interfaces.IAdminController;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.repository.AccountRepository;
import com.ironhack.bankingSystem.service.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admins")
public class AdminController implements IAdminController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AdminService adminService;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
