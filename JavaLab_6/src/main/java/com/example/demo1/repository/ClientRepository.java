package com.example.demo1.repository;

import com.example.demo1.entities.Address;
import com.example.demo1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByAddress(Address address);
}
