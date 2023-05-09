package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.Admin;
import com.ironhack.bankingSystem.model.ThirdPartyUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThirdPartyUserRepositoryTest {

    @Autowired
    ThirdPartyUserRepository thirdPartyUserrepository;

    @Autowired
    AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        Admin admin = new Admin("John Perez");
        ThirdPartyUser thirdParty1 = new ThirdPartyUser("KJ8754E", "Lisa J. Dudley", admin);
        thirdPartyUserrepository.save(thirdParty1);
        ThirdPartyUser thirdParty2 = new ThirdPartyUser("HG6588E", "Michael G. Pearson", admin);
        thirdPartyUserrepository.save(thirdParty2);
    }

    @AfterEach
    public void tearDown() {
        thirdPartyUserrepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    public void findAll_thirdParty_thirdPartyList() {
        List<ThirdPartyUser> thirdPartyList = thirdPartyUserrepository.findAll();
        System.out.println(thirdPartyList);
        assertEquals(2, thirdPartyList.size());
    }

    @Test
    public void findById_validId_correctThirdPartyUser() {
        Optional<ThirdPartyUser> thirdPartyOptional = thirdPartyUserrepository.findById("KJ8754E");
        assertTrue(thirdPartyOptional.isPresent());
        System.out.println(thirdPartyOptional.get());
        assertEquals("Lisa J. Dudley", thirdPartyOptional.get().getName());
    }

    @Test
    public void findById_invalidId_thirdPartyNotPresent() {
        Optional<ThirdPartyUser> thirdPartyOptional = thirdPartyUserrepository.findById("554hhhh");
        assertTrue(thirdPartyOptional.isEmpty());
    }
}