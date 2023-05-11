package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ironhack.bankingSystem.model.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SavingsRepositoryTest {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("Calle Falsa", "123");
        LocalDate date1 = LocalDate.of(1981, 7, 7);
        AccountHolder accountHolder1 = new AccountHolder("Lisa J. Dudley", date1, address1);
        Admin admin = new Admin("Michael G. Pearson");

        BigDecimal amount = new BigDecimal(1000);
        Money balance = new Money(amount);
        Savings savings = new Savings(balance, accountHolder1, admin, ACTIVE, "YUT655D");
        savingsRepository.save(savings);
    }

    @AfterEach
    public void tearDown() {
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    public void findAll_savings_savingsList() {
        List<Savings> savingsList = savingsRepository.findAll();
        System.out.println(savingsList);
        assertEquals(1, savingsList.size());
    }

    @Test
    public void findById_validId_correctSavings() {
        Optional<Savings> savingsOptional = savingsRepository.findById(1);
        assertTrue(savingsOptional.isPresent());
        System.out.println(savingsOptional.get());
        assertEquals("Lisa J. Dudley", savingsOptional.get().getPrimaryOwner().getName());
    }

    @Test
    public void findById_invalidId_savingsNotPresent() {
        Optional<Savings> savingsOptional = savingsRepository.findById(999);
        assertTrue(savingsOptional.isEmpty());
    }

    @Test
    public void checkMinimumBalance_savings_applyFee() {
        Optional<Savings> savingsOptional = savingsRepository.findById(1);
        System.out.println(savingsOptional.get());
        savingsOptional.get().setBalance(new Money(new BigDecimal(200)));
        savingsRepository.save(savingsOptional.get());
        savingsOptional.get().checkMinimumBalance();
        System.out.println(savingsOptional.get());
        BigDecimal value = new BigDecimal("160.00");

        assertEquals(value, savingsOptional.get().getBalance().getAmount());
    }

    @Test
    public void checkInterest_savings_updatedBalance() {
        Optional<Savings> savingsOptional = savingsRepository.findById(1);
        System.out.println(savingsOptional.get());
        LocalDate date = LocalDate.of(2022, 4, 1);
        savingsOptional.get().setLastInterestDate(date);
        savingsRepository.save(savingsOptional.get());
        System.out.println(savingsOptional.get());

        savingsOptional.get().checkInterest();
        System.out.println(savingsOptional.get());
        BigDecimal value = new BigDecimal("1002.50");

        assertEquals(value, savingsOptional.get().getBalance().getAmount());
    }

}