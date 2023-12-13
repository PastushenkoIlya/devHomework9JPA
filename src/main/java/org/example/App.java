package org.example;

import org.example.data.*;

import java.io.FileWriter;
import java.io.PrintWriter;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.List;

public class App
{
    public static void main(String[] args) {

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
                        //criteriaBuilder.count(flatJoin.get("owners"))
                )
                .where(predicateForWhere)
                .groupBy(
                        root
                )
                        /*residentJoin.get("name"),
                        residentJoin.get("surname"),
                        residentJoin.get("email"))*/
                .having(predicate);
        List<Tuple> results = entityManager.createQuery(criteriaQuery).getResultList();
        try (PrintWriter writer = new PrintWriter(new FileWriter("resultFile.txt"))) {
            for (Tuple result : results) {
                writer.print("Name: " + result.get(0));
                writer.print(", Surname: " + result.get(1));
                writer.print(", Email: " + result.get(2));
                writer.print(", Building ID: " + result.get(3));
                writer.print(", Building Address: " + result.get(4));
                writer.print(", Apartment Number: " + result.get(5));
                writer.println(", Area: " + result.get(6));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}