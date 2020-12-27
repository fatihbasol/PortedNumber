package com.fatihb.example.Controllers;

import com.fatihb.example.Entities.Filter;
import com.fatihb.example.Entities.PortedNumber;
import com.fatihb.example.Services.PortedNumberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/ported-numbers")
public class PortedNumberController {


    private final PortedNumberService portedNumberService;

    public PortedNumberController(PortedNumberService portedNumberService) {
        this.portedNumberService = portedNumberService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<PortedNumber> getAllPortedNumbers() {
        return this.portedNumberService.getAllPortedNumbers();
    }

    @PostMapping("/filter")
    @ResponseBody
    public List<PortedNumber> filter(@RequestBody Filter filter) {
        return this.portedNumberService.queryPortedNumberList(filter);
    }

    @RequestMapping(value = "/update-number", method = RequestMethod.POST)
    @ResponseBody
    public void updatePortedNumber(@RequestBody PortedNumber portedNumber) {
        this.portedNumberService.updatePortedNumber(portedNumber);
    }

    @RequestMapping(value = "/delete-number", method = RequestMethod.POST)
    @ResponseBody
    public void deleteNumberById(@RequestBody PortedNumber portedNumber) {
            this.portedNumberService.deleteById(portedNumber.getId());
    }

    @RequestMapping(value = "add-number", method = RequestMethod.POST)
    @ResponseBody
    public void addPortedNumber(@RequestBody PortedNumber portedNumber) {
            this.portedNumberService.addPortedNumber(portedNumber);
    }
}
