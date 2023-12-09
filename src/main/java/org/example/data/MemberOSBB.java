package org.example.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "members_osbb")
public class MemberOSBB implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "person_id")
    @OneToOne(mappedBy = "ownerId")
    private int personId;
    @Enumerated
    private Role role;
    //setters and getters
    public long getId() {
        return id;
    }
    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
