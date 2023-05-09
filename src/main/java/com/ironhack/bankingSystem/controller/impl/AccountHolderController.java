package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account-holders")
public class AccountHolderController {

    @Autowired
    IAccountHolderService accountHolderService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccountHoldersAccounts(@PathVariable Integer id) {
        return accountHolderService.getAllAccountHoldersAccounts(id);
    }

    @GetMapping("/{id}/accountId")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Integer id, @RequestParam Integer accountId) {
        return accountHolderService.getAccountById(id, accountId);
    }
}
