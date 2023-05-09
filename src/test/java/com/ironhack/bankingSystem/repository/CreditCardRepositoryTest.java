package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.AccountHolder;
import com.ironhack.bankingSystem.model.Address;
import com.ironhack.bankingSystem.model.Admin;
import com.ironhack.bankingSystem.model.CreditCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        AccountHolder accountHolder1 = new AccountHolder("Lisa J. Dudley", date1, address1);
        Admin admin = new Admin("Michael G. Pearson");

        BigDecimal amount = new BigDecimal(2000);
        Money balance = new Money(amount);
        CreditCard creditCard = new CreditCard(balance, accountHolder1, admin);
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
        List<CreditCard> checkingList = creditCardRepository.findAll();
        System.out.println(checkingList);
        assertEquals(1, checkingList.size());
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

}