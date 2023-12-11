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
        Root<Flat> root = criteriaQuery.from(Flat.class);
        //joins
        Join<Flat, Building> buildingJoin = root.join("building");
        Join<Flat, Resident> residentJoin = root.join("residents");
        Join<Flat, MemberOSBB> ownersJoin = root.join("owners");
        //subquery
        Subquery<Integer> residentSubquery = criteriaQuery.subquery(Integer.class);
        Root<Resident> residentRoot = residentSubquery.from(Resident.class);
        residentSubquery.select(residentRoot.<Integer>get("id"));

        Predicate personIsResident = criteriaBuilder
                .equal(ownersJoin.get("personId"), criteriaBuilder.any(residentSubquery));

        Predicate vehicleAccess = criteriaBuilder.isFalse(residentJoin.<Boolean>get("vehicleParkingAccess"));
        Predicate hasLessThenTwoFlat = criteriaBuilder.le(criteriaBuilder.count(root), 1);
        //Predicate isResident = criteriaBuilder.equal(ownersJoin.get("personId"), residentJoin.get("id"));

        Predicate predicateAllClauses = criteriaBuilder.and(vehicleAccess,hasLessThenTwoFlat, personIsResident);

        criteriaQuery.multiselect(
                residentJoin.get("name"),
                residentJoin.get("surname"),
                residentJoin.get("tel"),
                residentJoin.get("email"),
                residentJoin.get("vehicleParkingAccess"),
                buildingJoin.get("id"),
                buildingJoin.get("address"),
                root.get("area"),
                root.get("apartmentNumber")
        ).where(predicateAllClauses); //.groupBy(ownersJoin.get("personId")).having(predicateAllClauses);
        System.out.println(entityManager.createQuery(criteriaQuery).getResultList());
    }
}


//.where(criteriaBuilder.isFalse(residentJoin.get("vehicleParkingAccess")))