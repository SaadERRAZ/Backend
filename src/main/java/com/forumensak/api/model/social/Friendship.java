package com.forumensak.api.model.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.User;
import com.forumensak.api.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship extends DateAudit {
    @EmbeddedId
    private FriendshipKey id;
    @ManyToOne
    @MapsId("senderId")
    @JoinColumn(name="user1_id")
    @JsonIgnore
    private User sender;
    @ManyToOne
    @MapsId("receiverId")
    @JoinColumn(name="user2_id")
    @JsonIgnore
    private User receiver;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private Conversation conversation;
    private boolean status;
}
