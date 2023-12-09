package org.example.data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Role {
    @Enumerated(EnumType.STRING)
    member, worker, boardMember, chairmen;
}
