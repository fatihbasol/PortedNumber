package com.fatihb.example.Repositories;

import com.fatihb.example.Entities.PortedNumber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PortedNumberRepository extends JpaRepository<PortedNumber, Long>  {
    PortedNumber findTopByOrderByIdDesc(); // for PortedNumberTest

}
