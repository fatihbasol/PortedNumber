package com.fatihb.example.Entities;

import com.fatihb.example.Repositories.OperatorRepository;
import com.fatihb.example.Repositories.PortedNumberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortedNumberTest {

    @Autowired
    private PortedNumberRepository portedNumberRepository;
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private EntityManager entityManager;
    private PortedNumber portedNumber = new PortedNumber();

    @Test
    public void updatePortedNumber() {
        long id = 10;
        String expectedPortId = "15000000000000";
        String expectedServiceNumber = "555555555";
        String expectedActivity = "Port_Disconnect";

        PortedNumber portedNumber = new PortedNumber();
        portedNumber.setId(id);
        portedNumber.setPortId(expectedPortId);
        portedNumber.setServiceNumber(expectedServiceNumber);
        portedNumber.setActivity(expectedActivity);

        this.portedNumber.updatePortedNumber(portedNumber, portedNumberRepository);

        Optional<PortedNumber> actualPortedNumber = portedNumberRepository.findById(id);

        Assert.assertEquals("port should be updated.", expectedPortId, actualPortedNumber.get().getPortId());
        Assert.assertEquals("serviceNumber didn't match", expectedServiceNumber, actualPortedNumber.get().getServiceNumber());
        Assert.assertEquals("activity didn't match", expectedActivity, actualPortedNumber.get().getActivity());

    }

    @Test
    public void addPortedNumber() {
        String expectedRn = "1C88";
        String expectedPortId = "220000000548546Vodafone";
        String expectedServiceNumber = "5458492886";
        String expectedActivity = "Port";
        String expectedRouteNumber = "B003";

        PortedNumber portedNumber = new PortedNumber();

        portedNumber.setServiceNumber(expectedServiceNumber);
        portedNumber.setPortId(expectedPortId);
        portedNumber.setRn(expectedRn);
        portedNumber.setActivity(expectedActivity);
        portedNumber.setRouteNumber(expectedRouteNumber);

        this.portedNumber.addPortedNumber(portedNumber, portedNumberRepository, operatorRepository.findByRn(expectedRn));

        PortedNumber actualPortedNumber = portedNumberRepository.findTopByOrderByIdDesc();

        Assert.assertEquals("serviceNumber didnt match", expectedServiceNumber, actualPortedNumber.getServiceNumber());
        Assert.assertEquals("portId didnt match", expectedPortId, actualPortedNumber.getPortId());
        Assert.assertEquals("rn didnt match", expectedRn, actualPortedNumber.getRecipientOperator().getRn());
        Assert.assertEquals("activity didnt match", expectedActivity, actualPortedNumber.getActivity());
        Assert.assertEquals("routeNumber didnt match", expectedRouteNumber, actualPortedNumber.getRouteNumber());

    }

    @Test
    public void deleteById() {
        PortedNumber expectedPortedNumber = portedNumberRepository.findTopByOrderByIdDesc(); // get last data in db

        long expectedId = expectedPortedNumber.getId();

        this.portedNumber.deleteById(expectedId, portedNumberRepository);
        PortedNumber actualPortedNumber = this.portedNumberRepository.findTopByOrderByIdDesc();

        long actualId = actualPortedNumber.getId();

        Assert.assertNotEquals("id didnt match", expectedId, actualId);

    }

    @Test
    public void queryPortedNumberList() {
        long id = 55;

        Optional<PortedNumber> expectedPortedNumber = portedNumberRepository.findById(id);

        String expectedRn = expectedPortedNumber.get().getRn();
        String expectedActivity = expectedPortedNumber.get().getActivity();
        String expectedPortId = expectedPortedNumber.get().getPortId();
        String expectedServiceNumber = expectedPortedNumber.get().getServiceNumber();

        PortedNumber actualPortedNumber = this.portedNumber.queryPortedNumberList(expectedRn, expectedActivity, expectedPortId, expectedServiceNumber, this.entityManager).get(0);

        Assert.assertEquals("portedNumber didnt match", expectedPortedNumber.get(), actualPortedNumber);

    }
}