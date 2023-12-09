package org.example.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flats")
public class Flat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(mappedBy = "flatId")

    private int id;
    @Column(name = "apartment_number")
    private int apartmentNumber;
    private byte floor;
    private double area;
    @Column(name = "number_of_bedrooms")
    private byte numberOfBedrooms;
    @Column(name = "building_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flats_ibfk_1")
    private Building building;
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
