package com.forumensak.api.model.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.User;
import com.forumensak.api.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends DateAudit {
    @Id
    @GeneratedValue
    private long id;
    @Size(max =1000)
    private String message;
    private boolean status;
    private String ownerName;
    private String ownerImage;
    private long ownersId;
    private String ownerUsername;
    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private User owner;
}
