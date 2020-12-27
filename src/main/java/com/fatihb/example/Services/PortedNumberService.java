package com.fatihb.example.Services;

import com.fatihb.example.Entities.Filter;
import com.fatihb.example.Entities.PortedNumber;
import com.fatihb.example.Repositories.PortedNumberRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class PortedNumberService {

    private final PortedNumberRepository portedNumberRepository;
    private final EntityManager entityManager;

    private final PortedNumber portedNumber;
    private final OperatorService operatorService;

    public PortedNumberService(PortedNumberRepository portedNumberRepository, EntityManager entityManager, OperatorService operatorService) {
        this.portedNumberRepository = portedNumberRepository;
        this.entityManager = entityManager;
        this.operatorService = operatorService;
        this.portedNumber = new PortedNumber();
    }

    public List<PortedNumber> getAllPortedNumbers() {
        return this.portedNumberRepository.findAll();
    }

    public List<PortedNumber> queryPortedNumberList(Filter filter) {
        return this.portedNumber.queryPortedNumberList(filter.getRn(), filter.getActivity(), filter.getPortId(), filter.getServiceNumber(), entityManager);
    }

    public void updatePortedNumber(PortedNumber portedNumber) {
        this.portedNumber.updatePortedNumber(portedNumber, portedNumberRepository);
    }

    public void deleteById(Long id) {
        this.portedNumber.deleteById(id, portedNumberRepository);
    }

    public void addPortedNumber(PortedNumber portedNumber) {
        this.portedNumber.addPortedNumber(portedNumber, portedNumberRepository, operatorService.findByRn(portedNumber.getRn()));
    }

}
