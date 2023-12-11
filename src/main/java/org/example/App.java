package org.example;

import org.example.data.*;

//import javax.persistence.*;
//import javax.persistence.criteria.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
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
        //subquery
        Subquery<Integer> residentSubquery = criteriaQuery.subquery(Integer.class);
        Root<Resident> residentRoot = residentSubquery.from(Resident.class);
        residentSubquery.select(residentRoot.<Integer>get("id"));
/*
        Predicate personIsResident = criteriaBuilder
                .equal(ownersJoin.get("personId"), criteriaBuilder.any(residentSubquery));

        Predicate vehicleAccess = criteriaBuilder.isFalse(residentJoin.<Boolean>get("vehicleParkingAccess"));
        Predicate hasLessThenTwoFlat = criteriaBuilder.le(criteriaBuilder.count(root), 1);
        //Predicate isResident = criteriaBuilder.equal(ownersJoin.get("personId"), residentJoin.get("id"));

        Predicate predicateAllClauses = criteriaBuilder.and(vehicleAccess,hasLessThenTwoFlat, personIsResident);
*/
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