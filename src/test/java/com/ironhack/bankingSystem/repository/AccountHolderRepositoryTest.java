package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.AccountHolder;
import com.ironhack.bankingSystem.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AccountHolderRepositoryTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("Calle Falsa", "123");
        Address address2 = new Address("Calle Falsa", "321");

        LocalDate date1 = LocalDate.of(1981, 7, 7);
        AccountHolder accountHolder1 = new AccountHolder("Lisa J. Dudley", date1, address1);
        accountHolderRepository.save(accountHolder1);
        LocalDate date2 = LocalDate.of(1945, 11, 20);
        AccountHolder accountHolder2 = new AccountHolder("Michael G. Pearson", date2, address2);
        accountHolderRepository.save(accountHolder2);
    }

    @AfterEach
    public void tearDown() {
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void findAll_accountHolders_accountHoldersList() {
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        System.out.println(accountHolderList);
        assertEquals(2, accountHolderList.size());
    }

    @Test
    public void findById_validId_correctAccountHolder() {
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(1);
        assertTrue(accountHolderOptional.isPresent());
        System.out.println(accountHolderOptional.get());
        assertEquals("Lisa J. Dudley", accountHolderOptional.get().getName());
    }

    @Test
    public void findById_invalidId_accountHolderNotPresent() {
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(999);
        assertTrue(accountHolderOptional.isEmpty());
    }

}