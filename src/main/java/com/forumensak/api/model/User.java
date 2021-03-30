package com.forumensak.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.audit.DateAudit;
import com.forumensak.api.model.social.Comment;
import com.forumensak.api.model.social.Friendship;
import com.forumensak.api.model.social.Notification;
import com.forumensak.api.model.social.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @Size(max = 40)
    private String companyName;

    @NotBlank(message = "username must not be blank")
    @Size(max = 15)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank(message = "Password must not be black")
    @JsonIgnore
    @Size(max = 100, min = 6, message = "Password must be between 6 and 100 characters")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    private Boolean enabled;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    private Cv cv;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comp_id", referencedColumnName = "id")
    private Company company;
    @OneToMany(mappedBy = "owner")
    private List<Post> posts;
    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Comment> comments;
    @OneToMany(mappedBy = "owner")
    private List<Notification> notifications ;
    @OneToMany(mappedBy = "sender")
    private List<Friendship> friendshipSended;
    @OneToMany(mappedBy = "receiver")
    private List<Friendship> friendshipReceived;
    public User(String name, String companyName, String username, String email, String password) {
        this.name = name;
        this.companyName = companyName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = false;
    }
}
