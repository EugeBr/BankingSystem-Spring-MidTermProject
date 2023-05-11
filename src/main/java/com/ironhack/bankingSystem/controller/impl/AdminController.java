package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.controller.dto.AccountBalanceDto;
import com.ironhack.bankingSystem.controller.interfaces.IAdminController;
import com.ironhack.bankingSystem.model.*;
import com.ironhack.bankingSystem.repository.AccountRepository;
import com.ironhack.bankingSystem.repository.ThirdPartyUserRepository;
import com.ironhack.bankingSystem.service.impl.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/admins")
public class AdminController implements IAdminController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ThirdPartyUserRepository thirdPartyUserRepository;

    @Autowired
    AdminService adminService;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(/*Principal principal,*/ @PathVariable Integer id) {
        return adminService.getAccountById(id);
    }

    @GetMapping("/third-party-users")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdPartyUser> getAllThirdPartyUsers() {
        return thirdPartyUserRepository.findAll();
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

    @PostMapping("/third-party-users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage saveThirdPartyUser(@RequestBody @Valid ThirdPartyUser thirdPartyUser) {
        return adminService.saveThirdPartyUser(thirdPartyUser);
    }

    // replaced status.NO_CONTENT with status.OK, so I can return a message
    @PatchMapping("/accounts/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage updateAccountBalance(@PathVariable Integer id, @RequestBody @Valid AccountBalanceDto accountBalanceDto) {
        return adminService.updateAccountBalance(id, accountBalanceDto.getBalance());
    }

    // same here
    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage deleteAccount(@PathVariable Integer id) {
        return adminService.deleteAccount(id);
    }

    @DeleteMapping("/third-party-users/{hashedKey}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage deleteThirdPartyUser(@PathVariable String hashedKey) {
        return adminService.deleteThirdPartyUser(hashedKey);
    }
}
