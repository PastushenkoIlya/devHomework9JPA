package org.example.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flats")
public class Flat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "apartment_number")
    private int apartmentNumber;
    private byte floor;
    private double area;
    @Column(name = "number_of_bedrooms")
    private byte numberOfBedrooms;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "inhabitants_to_flats",
            joinColumns = { @JoinColumn (name = "flat_id")},
            inverseJoinColumns = {@JoinColumn(name = "resident_id")}
    )
    private List<Resident> residents = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "owners_to_flats",
            joinColumns = {@JoinColumn(name = "flat_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")}
    )
    private List<MemberOSBB> owners = new ArrayList<>();
    //setters and getters
    public int getId() {
        return id;
    }
    public int getApartmentNumber() {
        return apartmentNumber;
    }
    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
    public byte getFloor() {
        return floor;
    }
    public void setFloor(byte floor) {
        this.floor = floor;
    }
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public byte getNumberOfBedrooms() {
        return numberOfBedrooms;
    }
    public void setNumberOfBedrooms(byte numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }
    public Building getBuilding() {
        return building;
    }
    public void setBuildingId(Building building) {
        this.building = building;
    }
}
