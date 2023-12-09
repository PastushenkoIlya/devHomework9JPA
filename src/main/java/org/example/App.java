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
        Root<Resident> root = criteriaQuery.from(Resident.class);

        Join<Resident, InhabitantsToFlats> inhabitantsToFlatsJoin = root.join("inhabitants_to_flats");
        Join<InhabitantsToFlats, Flat> flatJoin = root.join("flats");
        Join<Flat, Building> buildingJoin = root.join("buildings");

        Join<Resident, OwnersToFlats> ownersToFlatsJoin = root.join("owners_to_flats");
        Join<OwnersToFlats, MemberOSBB> memberOSBBJoin = root.join("members_OSBB");

        criteriaQuery.multiselect(
                root.get("name"),
                root.get("surname"),
                root.get("tel"),
                root.get("email"),
                root.get("vehicle_parking_access"),
                buildingJoin.get("id"),
                buildingJoin.get("address"),
                flatJoin.get("apartment_number"),
                flatJoin.get("area"));
        System.out.println(entityManager.createQuery(criteriaQuery).getResultList());
    }
}
