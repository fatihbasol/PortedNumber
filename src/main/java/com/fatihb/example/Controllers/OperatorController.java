package com.fatihb.example.Controllers;

import com.fatihb.example.Entities.Operator;
import com.fatihb.example.Services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/operators")
public class OperatorController {
    @Autowired
    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Operator> getAllOperators() {
        return this.operatorService.getAllOperators();
    }
}
