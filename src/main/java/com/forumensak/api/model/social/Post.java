package com.forumensak.api.model.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.User;
import com.forumensak.api.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends DateAudit {
    @Id
    @GeneratedValue
    private long id;
//    @OneToMany
//    private List<User> likers;
    @Size(max =1000)
    private String message;
    private String ownerFirstName;
    private String ownerLastName;
    private String companyName;
    private String ownerImage;
    private long ownersId;
    private String ownerUsername;
    private int role;
    @OneToMany(mappedBy="post", cascade = CascadeType.REMOVE)
    private List<Comment> comment;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private User owner;

}
