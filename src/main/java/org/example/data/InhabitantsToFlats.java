package org.example.data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "inhabitants_to_flats")
public class InhabitantsToFlats implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;
    @OneToOne
    @JoinColumn(name = "flat_id")
    private Flat flatId;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Resident getResident() {
        return resident;
    }
    public void setResident(Resident resident) {
        this.resident = resident;
    }
    public Flat getFlatId() {
        return flatId;
    }
    public void setFlatId(Flat flatId) {
        this.flatId = flatId;
    }
}
