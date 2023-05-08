package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.AccountHolder;
import com.ironhack.bankingSystem.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("Calle Falsa", "123");
        addressRepository.save(address1);
        Address address2 = new Address("Calle Falsa", "321");
        addressRepository.save(address2);

    }

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    public void findAll_addresses_addressList() {
        List<Address> addressList = addressRepository.findAll();
        System.out.println(addressList);
        assertEquals(2, addressList.size());
    }

}