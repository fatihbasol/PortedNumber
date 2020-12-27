package com.fatihb.example.Repositories;

import com.fatihb.example.Entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperatorRepository extends JpaRepository<Operator,Long> {
     Operator findByRn(String rn);
}
