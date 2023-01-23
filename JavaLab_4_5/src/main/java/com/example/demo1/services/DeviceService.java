package com.example.demo1.services;

import com.example.demo1.entities.Device;
import com.example.demo1.entities.Record;
import com.example.demo1.repository.ClientRepository;
import com.example.demo1.repository.DeviceRepository;
import com.example.demo1.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<Device> getAllDevices() {return deviceRepository.findAll(); }

    public Device getDevicesById(Long id) throws Exception{

        Device Device = deviceRepository.findById(id).orElse(null);
        if(Device == null) {
            throw new Exception("Could not find Device");
        }

        return Device;
    }

    public int generateValue(){
        Random r=new Random();
        return 1000*r.nextInt();
    }

    public int generateMonth(){return (int)((Math.random()*11)+1);}
    public int generateYear(){return (int)((Math.random()*23)+1998);}

    public String generateMeasurementForDevice(long id){
        Device device = deviceRepository.findById((long) id).orElse(null);
        Record record = new Record();
        record.setDevice(device);
        record.setMonth(generateMonth());
        record.setYear(generateYear());
        record.setValue(generateValue());

        recordRepository.save(record);
        List<Record> tmp = device.getRecords();
        tmp.add(record);
        device.setRecords(tmp);
        deviceRepository.save(device);
        return "Measurement generated!";

    }

    public void createDevice(Device newDevice){

        deviceRepository.save(newDevice);
    }

    public Device updateDevice(Long id, Device updatedDevice) throws Exception {
        Device oldDevice = deviceRepository.findById(id).orElse(null);
        if(oldDevice == null) {
            throw new Exception("Could not find Device");
        }
        updatedDevice.setId(oldDevice.getId());
        return deviceRepository.save(updatedDevice);
    }

    public void deleteDevice(Long id) throws Exception{
        Device deleteDevice = deviceRepository.findById(id).orElse(null);
        if(deleteDevice == null) {
            throw new Exception("Could not find Device");
        }

        deviceRepository.delete(deleteDevice);
    }



    public List<Record> measurements(Long id) throws Exception {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }

        return recordRepository.findRecordsbyDevice(id);
    }


    public int totalByYear(int year){
        int sum=(int) recordRepository.totalByYear(year);

        return sum;
    }

    public Record oneMonth(int month, int year){
        return recordRepository.oneMonth(month, year);
    }

    public List<Record> allMonth(int year){
        return recordRepository.allMonth(year);
    }

   public Page<Record> getAllRecords(Pageable pageable)
    {
        return recordRepository.findAll(pageable);
    }





}
