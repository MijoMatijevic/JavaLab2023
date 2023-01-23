package com.example.demo1.repository;

import com.example.demo1.entities.Client;
import com.example.demo1.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository <Device, Long> {
    Device findDeviceByClient(Client client);

}
