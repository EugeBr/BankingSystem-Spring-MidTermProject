package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.AccountHolder;
import com.ironhack.bankingSystem.model.Address;
import com.ironhack.bankingSystem.model.Admin;
import com.ironhack.bankingSystem.model.Checking;
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
class CheckingRepositoryTest {

    @Autowired
    CheckingRepository checkingRepository;

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

        BigDecimal amount = new BigDecimal(3000);
        Money balance = new Money(amount);
        Checking checking = new Checking(balance, accountHolder1, admin, ACTIVE, "YUT655D");
        checkingRepository.save(checking);
    }

    @AfterEach
    public void tearDown() {
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    public void findAll_checking_checkingList() {
        List<Checking> checkingList = checkingRepository.findAll();
        System.out.println(checkingList);
        assertEquals(1, checkingList.size());
    }

    @Test
    public void findById_validId_correctChecking() {
        Optional<Checking> checkingOptional = checkingRepository.findById(1);
        assertTrue(checkingOptional.isPresent());
        System.out.println(checkingOptional.get());
        assertEquals("Lisa J. Dudley", checkingOptional.get().getPrimaryOwner().getName());
    }

    @Test
    public void findById_invalidId_checkingNotPresent() {
        Optional<Checking> checkingOptional = checkingRepository.findById(999);
        assertTrue(checkingOptional.isEmpty());
    }

    @Test
    public void checkMinimumBalance_checking_applyFee() {
        Optional<Checking> checkingOptional = checkingRepository.findById(1);
        System.out.println(checkingOptional.get());

        checkingOptional.get().setBalance(new Money(new BigDecimal(200)));
        checkingRepository.save(checkingOptional.get());
        checkingOptional.get().checkMinimumBalance();
        System.out.println(checkingOptional.get());
        BigDecimal value = new BigDecimal("160.00");

        assertEquals(value, checkingOptional.get().getBalance().getAmount());
    }

    @Test
    public void checkMonthlyFee_checking_applyFee() {
        Optional<Checking> checkingOptional = checkingRepository.findById(1);
        System.out.println(checkingOptional.get());
        LocalDate date = LocalDate.of(2023, 4, 1);
        checkingOptional.get().setLastMonthlyFeeDate(date);
        checkingRepository.save(checkingOptional.get());
        System.out.println(checkingOptional.get());

        checkingOptional.get().checkMonthlyFee();
        System.out.println(checkingOptional.get());
        BigDecimal value = new BigDecimal("2988.00");

        assertEquals(value, checkingOptional.get().getBalance().getAmount());
    }

}