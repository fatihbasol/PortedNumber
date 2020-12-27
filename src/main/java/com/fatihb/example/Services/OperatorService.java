package com.fatihb.example.Services;

import com.fatihb.example.Entities.Operator;
import com.fatihb.example.Repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorService {
    @Autowired
    private final OperatorRepository operatorRepository;

    public OperatorService(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    public List<Operator> getAllOperators(){
        return this.operatorRepository.findAll();
    }
    public Operator findByRn(String rn){
        return this.operatorRepository.findByRn(rn);
    }

}
