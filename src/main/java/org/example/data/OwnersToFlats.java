package org.example.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "owners_to_flats")
public class OwnersToFlats implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "owner_id")
    @OneToOne
    @JoinColumn(name = "owners_to_flats_ibfk_1")
    private long ownerId;
    @Column(name = "flat_id")
    @OneToOne
    @JoinColumn(name = "owners_to_flats_ibfk_2")
    private long flatId;
    //setters and getters
    public long getId() {return id;}
    public long getOwnerId() {return ownerId;}
    public void setOwnerId(long ownerId) {this.ownerId = ownerId;}
    public long getFlatId() {return flatId;}
    public void setFlatId(long flatId) {this.flatId = flatId;}
}