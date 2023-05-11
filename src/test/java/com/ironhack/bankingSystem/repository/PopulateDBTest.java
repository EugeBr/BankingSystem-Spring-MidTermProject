package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.ironhack.bankingSystem.model.enums.Status.ACTIVE;
import static com.ironhack.bankingSystem.model.enums.Status.FROZEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PopulateDBTest {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    ThirdPartyUserRepository thirdPartyUserrepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        creditCardRepository.deleteAll();
        savingsRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        checkingRepository.deleteAll();
        thirdPartyUserrepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
        adminRepository.deleteAll();

        Admin admin = new Admin("John Perez");
        adminRepository.save(admin);

        Address address1 = new Address("Lyndon Street", "609");
        addressRepository.save(address1);
        LocalDate date1 = LocalDate.of(1981, 7, 7);
        AccountHolder accountHolder1 = new AccountHolder("Lisa J. Dudley", date1, address1);
        accountHolderRepository.save(accountHolder1);
        Address address2 = new Address("Hickman Street", "4255");
        addressRepository.save(address2);
        LocalDate date2 = LocalDate.of(1945, 11, 20);
        AccountHolder accountHolder2 = new AccountHolder("Michael G. Pearson", date2, address2);
        accountHolderRepository.save(accountHolder2);
        Address address3 = new Address("Graystone Lakes", "2664");
        addressRepository.save(address3);
        LocalDate date3 = LocalDate.of(2001, 8, 15);
        AccountHolder accountHolder3 = new AccountHolder("Jennifer C. Lee", date3, address3);   //! under 24 yo
        accountHolderRepository.save(accountHolder3);
        Address address4 = new Address("Lyndon Street", "33");
        addressRepository.save(address4);
        LocalDate date4 = LocalDate.of(1941, 5, 8);
        AccountHolder accountHolder4 = new AccountHolder("Dominic K. Watts", date4, address4);
        accountHolderRepository.save(accountHolder4);
        Address address5 = new Address("Worthington Drive", "248");
        addressRepository.save(address5);
        LocalDate date5 = LocalDate.of(2003, 2, 14);
        AccountHolder accountHolder5 = new AccountHolder("Sarah F. Lopes", date5, address5);    //! under 24 yo
        accountHolderRepository.save(accountHolder5);

        ThirdPartyUser thirdParty1 = new ThirdPartyUser("KJ8754E", "Anthony L. Pattison", admin);
        thirdPartyUserrepository.save(thirdParty1);
        ThirdPartyUser thirdParty2 = new ThirdPartyUser("HG6588E", "Timothy D. Lopez", admin);
        thirdPartyUserrepository.save(thirdParty2);

        Checking checking1 = new Checking(new Money(new BigDecimal(30000)), accountHolder1, admin, ACTIVE, "YUT655D");
        checkingRepository.save(checking1);
        Checking checking2 = new Checking(new Money(new BigDecimal(10000)), accountHolder2, accountHolder1, admin, ACTIVE, "JDY799K");
        checkingRepository.save(checking2);

        StudentChecking studentChecking1 = new StudentChecking(new Money(new BigDecimal(1000)), accountHolder3, admin, ACTIVE, "LAS746L");
        studentCheckingRepository.save(studentChecking1);
        StudentChecking studentChecking2 = new StudentChecking(new Money(new BigDecimal(2300)), accountHolder5, admin, ACTIVE, "EAW664C");
        studentCheckingRepository.save(studentChecking2);

        Savings savings1 = new Savings(new Money(new BigDecimal(54000)), accountHolder2, admin, ACTIVE, "OVJ476F");
        savingsRepository.save(savings1);
        Savings savings2 = new Savings(new Money(new BigDecimal(15000)), accountHolder4, admin, ACTIVE, "FSJ665A");
        savingsRepository.save(savings2);
        Savings savings3 = new Savings(new Money(new BigDecimal(34000)), accountHolder1, accountHolder2, admin, FROZEN, "LFG888E");
        savingsRepository.save(savings3);

        CreditCard creditCard1 = new CreditCard(new Money(new BigDecimal(11500)), accountHolder1, admin, ACTIVE);
        creditCardRepository.save(creditCard1);
        CreditCard creditCard2 = new CreditCard(new Money(new BigDecimal(60000)), accountHolder2, admin, ACTIVE);
        creditCardRepository.save(creditCard2);
        CreditCard creditCard3 = new CreditCard(new Money(new BigDecimal(28000)), accountHolder4, admin, ACTIVE);
        creditCardRepository.save(creditCard3);
    }

    @Test
    public void findAll_allEntities_AllTheLists() {
        List<Admin> adminList = adminRepository.findAll();
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        List<ThirdPartyUser> thirdPartyList = thirdPartyUserrepository.findAll();
        List<Checking> checkingList = checkingRepository.findAll();
        List<StudentChecking> studentCheckingList = studentCheckingRepository.findAll();
        List<Savings> savingsList = savingsRepository.findAll();
        List<CreditCard> creditCardList = creditCardRepository.findAll();

        assertEquals(1, adminList.size());
        assertEquals(5, accountHolderList.size());
        assertEquals(2, thirdPartyList.size());
        assertEquals(2, checkingList.size());
        assertEquals(2, studentCheckingList.size());
        assertEquals(3, savingsList.size());
        assertEquals(3, creditCardList.size());
    }

}