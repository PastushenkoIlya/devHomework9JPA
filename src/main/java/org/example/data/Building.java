package org.example.data;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "buildings")
public class Building implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    //setters and getters
    public long getId() {return id;}
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
