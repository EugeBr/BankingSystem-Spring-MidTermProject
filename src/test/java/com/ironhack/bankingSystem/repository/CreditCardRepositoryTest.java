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
class CreditCardRepositoryTest {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("Calle Falsa", "123");
        LocalDate date1 = LocalDate.of(1981, 7, 7);
        AccountHolder accountHolder1 = new AccountHolder("Lisa J. Dudley", "lisa", "1234", date1, address1);
        Admin admin = new Admin("Michael G. Pearson", "michael", "1234");

        BigDecimal amount = new BigDecimal(2000);
        Money balance = new Money(amount);
        CreditCard creditCard = new CreditCard(balance, accountHolder1, admin, ACTIVE);
        creditCardRepository.save(creditCard);
    }

    @AfterEach
    public void tearDown() {
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    public void findAll_checking_checkingList() {
        List<CreditCard> creditCardList = creditCardRepository.findAll();
        System.out.println(creditCardList);
        assertEquals(1, creditCardList.size());
    }

    @Test
    public void findById_validId_correctCreditCard() {
        Optional<CreditCard> checkingOptional = creditCardRepository.findById(1);
        assertTrue(checkingOptional.isPresent());
        System.out.println(checkingOptional.get());
        assertEquals("Lisa J. Dudley", checkingOptional.get().getPrimaryOwner().getName());
    }

    @Test
    public void findById_invalidId_creditCardNotPresent() {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(999);
        assertTrue(creditCardOptional.isEmpty());
    }

    @Test
    public void checkInterest_creditCard_updatedBalance() {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(1);
        System.out.println(creditCardOptional.get());
        LocalDate date = LocalDate.of(2023, 4, 1);
        creditCardOptional.get().setLastInterestDate(date);
        creditCardRepository.save(creditCardOptional.get());
        System.out.println(creditCardOptional.get());

        creditCardOptional.get().checkInterest();
        System.out.println(creditCardOptional.get());
        BigDecimal value = new BigDecimal("1600.00");

        assertEquals(value, creditCardOptional.get().getBalance().getAmount());
    }

}