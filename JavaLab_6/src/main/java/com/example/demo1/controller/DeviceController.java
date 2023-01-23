package com.example.demo1.controller;


import com.example.demo1.entities.Device;
import com.example.demo1.entities.Record;
import com.example.demo1.repository.RecordRepository;
import com.example.demo1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    RecordRepository recordRepository;

    @GetMapping("/gettingAll")
    public Iterable<Device> getAll() { return deviceService.getAllDevices(); }

    @PostMapping("/createDevice")
    public Device createDevice(@RequestBody Device newDevice) {

       return (deviceService.createDevice(newDevice));
    }

    @GetMapping("/{id}")
    public Device getById(@PathVariable Long id) {
        Device device;
        try {
            device = deviceService.getDevicesById(id);
        } catch (Exception ex) {
            return null;
        }
        return device;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateClient(@PathVariable Long id, @RequestBody Device updatedDevice) {
        ResponseEntity<Device> response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(deviceService.updateDevice(id, updatedDevice));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try{
            deviceService.deleteDevice(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Device");
    }


    @GetMapping("/{id}/measurements")
    public List<Record> getMeasurements(@PathVariable Long id) {
        try {
            return deviceService.measurements(id);

        } catch (Exception ex){
            return null;
        }
    }

    @GetMapping("/measurements/all/{sort}")
    public Page<Record> getAllRecords(@PageableDefault(page = 1, size = 5) Pageable page, @RequestParam(required = true) String sort)
    {
        Sort sortBy = Sort.by(sort);
        Pageable sortPage = PageRequest.of(page.getPageNumber(), page.getPageSize(), sortBy);
        return deviceService.getAllRecords(sortPage);
    }

    @GetMapping("/measurements/sum")
    public int totalByYear(int year) {

        return deviceService.totalByYear(year);
    }

    @GetMapping("/measurements/month")
    public Record oneMonth(@RequestParam(required = true) int month, @RequestParam(required = true) int year) {

        return deviceService.oneMonth(month, year);
    }

    @GetMapping("/measurement/allmonth")
    public List<Record> allMonth(@RequestParam int year) {

        return deviceService.allMonth(year);
    }
    @GetMapping("/allRecordsRead")
    public List<Record> allRecordsRead() {

        return recordRepository.findAll();
    }

    @PostMapping("/makeMeasurement/{id}")
    public void generateMeasurementForDeviceId(@PathVariable long id){
        deviceService.generateMeasurementForDevice(id);
    }

    @RequestMapping(value = "/allRecords", method = RequestMethod.GET)
    public ResponseEntity<List<Record>> readSensors () {return ResponseEntity.ok(deviceService.getRead());}

}