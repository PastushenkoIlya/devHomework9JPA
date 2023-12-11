package org.example.data;

import javax.persistence.*;
import javax.persistence.criteria.Expression;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residents")
public class Resident implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String tel;
    private String email;
    @Column(name = "vehicle_parking_access")
    private boolean vehicleParkingAccess;
    //setters and getters
    @ManyToMany(mappedBy = "residents")
    private List<Flat> flats = new ArrayList<>();
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isVehicleParkingAccess() {
        return vehicleParkingAccess;
    }
    public void setVehicleParkingAccess(boolean vehicleParkingAccess) {
        this.vehicleParkingAccess = vehicleParkingAccess;
    }
}
