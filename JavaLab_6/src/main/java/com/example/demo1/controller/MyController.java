package com.example.demo1.controller;

import com.example.demo1.entities.Device;
import com.example.demo1.entities.Record;
import com.example.demo1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {
    @Autowired
    DeviceService deviceService;



/*
    @GetMapping("/new")
    public ModelAndView getAllSensors() {

        ModelAndView mav = new ModelAndView("device");
        mav.addObject("record", deviceService.allRecordsRead());
        return mav;
    }
*/
@GetMapping("/new")
public String getAllDevices(Model model){

    String url="http://localhost:8080/devices/allRecordsRead";
    RestTemplate resttemplate=new RestTemplate();
    Record[] record=resttemplate.getForObject(url,Record[].class);
    model.addAttribute("record", record);
    return "record";
}

    @GetMapping("/addDevice")
    public String addDeviceAll (Model model) {

        model.addAttribute("record", new Record());

        return "form";
    }

    @PostMapping("/addDevice")
    public String post(@ModelAttribute Record record, Model model) throws Exception {

            Device d=deviceService.getDevicesById(record.getFlag());
            record.setDevice(d);
            deviceService.createRecord(record);

            return "form";
        }

}
