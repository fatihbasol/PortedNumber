package com.fatihb.example.Entities;

import com.fatihb.example.Repositories.PortedNumberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PORTED_NUMBER")

public class PortedNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // @SequenceGenerator(name = "number_seq", sequenceName = "HIBERNATE_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACTIVITY")
    private String activity;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Column(name = "INTERNAL_NUMBER")
    private String internalNumber;

    @Column(name = "LAST_MODIFIED_DATE")
    private Timestamp lastModifiedDate;

    @Column(name = "PORT_ID")
    private String portId;

    @Column(name = "PORTING_TIME")
    private Timestamp portingTime;

    @Column(name = "ROUTE_NUMBER")
    private String routeNumber;

    @Column(name = "SERVICE_NUMBER")
    private String serviceNumber;

    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "CREATED_BY_ID")
    private Long createdById;

    @Column(name = "LAST_MODIFIED_BY_ID")
    private Long lastModifiedById;

    @Transient
    private String rn;

    @ManyToOne
    @JoinColumn(name = "RECIPIENT_OPERATOR_ID", referencedColumnName = "ID")
    private Operator recipientOperator;

    @ManyToOne
    @JoinColumn(name = "DONOR_OPERATOR_ID", referencedColumnName = "ID")
    private Operator donorOperator;

    public void addPortedNumber(PortedNumber portedNumber, PortedNumberRepository repository, Operator operator) {
        long id = 1;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        portedNumber.setCreatedById(id);
        portedNumber.setLastModifiedById(id);
        portedNumber.setStatusId((long) 1);
        portedNumber.setCreatedDate(timestamp);
        portedNumber.setLastModifiedDate(timestamp);
        portedNumber.setRn("1C88");
        portedNumber.setRecipientOperator(operator);
        repository.save(portedNumber);
    }

    public void updatePortedNumber(PortedNumber portedNumber, PortedNumberRepository repository) {
        Optional<PortedNumber> portedNumberFromDb = repository.findById(portedNumber.getId());
        portedNumberFromDb.get().setActivity(portedNumber.getActivity());
        portedNumberFromDb.get().setPortId(portedNumber.getPortId());
        portedNumberFromDb.get().setServiceNumber(portedNumber.getServiceNumber());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());   // current system date
        portedNumberFromDb.get().setLastModifiedDate(currentTime);
        repository.save(portedNumberFromDb.get());
    }

    public void deleteById(Long id, PortedNumberRepository repository) {
        repository.deleteById(id);
    }

    public List<PortedNumber> queryPortedNumberList(String rn, String activity, String portId, String serviceNumber, EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PortedNumber> cq = cb.createQuery(PortedNumber.class);
        Root<PortedNumber> portedNumber = cq.from(PortedNumber.class);
        List<Predicate> predicates = new ArrayList<>();

        if (rn != null) {
            predicates.add(cb.equal(portedNumber.get("recipientOperator").get("rn"), rn));
        }
        if (activity != null) {
            predicates.add(cb.equal(portedNumber.get("activity"), activity));
        }
        if (portId != null) {
            predicates.add(cb.equal(portedNumber.get("portId"), portId));
        }
        if (serviceNumber != null) {
            predicates.add(cb.equal(portedNumber.get("serviceNumber"), serviceNumber));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getResultList();
    }


}
