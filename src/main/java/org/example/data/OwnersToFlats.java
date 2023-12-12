package org.example.data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Member;

@Entity
@Table(name = "owners_to_flats")
public class OwnersToFlats implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "owner_id")
    private MemberOSBB owner;
    @OneToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;
    //setters and getters
    public long getId() {return id;}
    public MemberOSBB getOwner() {return owner;}
    public void setOwner(MemberOSBB owner) {this.owner = owner;}
    public Flat getFlat() {return flat;}
    public void setFlatId(Flat flat) {this.flat = flat;}
}