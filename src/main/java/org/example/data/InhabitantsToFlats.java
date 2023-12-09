package org.example.data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "inhabitants_to_flats")
public class InhabitantsToFlats implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "resident_id")
    @OneToOne
    @JoinColumn(name = "inhabitants_to_flats_ibfk_2")
    private int residentId;
    @Column(name = "flat_id")
    @OneToOne
    @JoinColumn(name = "inhabitants_to_flats_ibfk_1")
    private int flatId;
    //setters and getters
    public long getId() {return id;}
    public int getResidentId() {return residentId;}
    public void setResidentId(int residentId) {this.residentId = residentId;}
    public int getFlatId() {return flatId;}
    public void setFlatId(int flatId) {this.flatId = flatId;}
}
