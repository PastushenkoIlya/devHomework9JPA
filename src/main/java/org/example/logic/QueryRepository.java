package org.example.logic;

import org.example.data.Building;
import org.example.data.Flat;
import org.example.data.MemberOSBB;
import org.example.data.Resident;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;

public class QueryRepository {
    public List<Tuple> getMembers(){
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("OSBBPersistenceUnitName");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
    Root<MemberOSBB> root = criteriaQuery.from(MemberOSBB.class);

    //joins
    Join<MemberOSBB, Flat> flatJoin = root.join("flats");
    Join<Flat, Building> buildingJoin = flatJoin.join("building");
    Join<Flat, Resident> residentJoin = flatJoin.join("residents");

    //subquery needed for operator 'any' to be used in 'isResident' predicate
    Subquery<Integer> residentSubquery = criteriaQuery.subquery(Integer.class);
    Root<Resident> residentRoot = residentSubquery.from(Resident.class);
        residentSubquery.select(residentRoot.<Integer>get("id"));

    Predicate isResident = criteriaBuilder.equal(root.get("personId"), criteriaBuilder.any(residentSubquery));
    Predicate noParkingAccess = criteriaBuilder.isFalse(residentJoin.<Boolean>get("vehicleParkingAccess"));

    Predicate predicateForWhere = criteriaBuilder.and(isResident, noParkingAccess);
    Predicate predicate = criteriaBuilder.lessThan(criteriaBuilder.countDistinct(flatJoin.get("id")), 2L);

        criteriaQuery
                .multiselect(
                residentJoin.get("name"),
                residentJoin.get("surname"),
                residentJoin.get("email"),
                buildingJoin.get("id"),
                buildingJoin.get("address"),
                flatJoin.get("apartmentNumber"),
                flatJoin.get("area")
                )
                .where(predicateForWhere)
                .groupBy(root)
                .having(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
