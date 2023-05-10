package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.controller.interfaces.IAdminController;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.model.Checking;
import com.ironhack.bankingSystem.model.CreditCard;
import com.ironhack.bankingSystem.model.Savings;
import com.ironhack.bankingSystem.repository.AccountRepository;
import com.ironhack.bankingSystem.service.impl.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Integer id) {
        return adminService.getAccountById(id);
    }

    @PostMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage saveCheckingAccount(@RequestBody @Valid Checking checking) {
        return adminService.saveCheckingAccount(checking);
    }

    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage saveSavingsAccount(@RequestBody @Valid Savings savings) {
        return adminService.saveSavingsAccount(savings);
    }

    @PostMapping("/accounts/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage saveCreditCardAccount(@RequestBody @Valid CreditCard creditCard) {
        return adminService.saveCreditCardAccount(creditCard);
    }
}
