package org.example;

import org.example.data.*;

//import jakarta.persistence.*;
//import jakarta.persistence.criteria.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
public class App
{
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("OSBBPersistenceUnitName");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
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



        criteriaQuery
            .multiselect(
                    residentJoin.get("name"),
                    residentJoin.get("surname"),
                    residentJoin.get("email"),
                    buildingJoin.get("id"),
                    buildingJoin.get("address"),
                    flatJoin.get("apartmentNumber"),
                    flatJoin.get("area"))
            .where(predicateForWhere)
            .groupBy(
                    residentJoin.get("name"),
                    residentJoin.get("surname"),
                    residentJoin.get("email"))
            .having(criteriaBuilder.le(criteriaBuilder.count(root.get("flats")),1));
        System.out.println(entityManager.createQuery(criteriaQuery).getResultList());
    }
}


//.where(criteriaBuilder.isFalse(residentJoin.get("vehicleParkingAccess")))