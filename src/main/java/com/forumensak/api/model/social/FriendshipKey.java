package com.forumensak.api.model.social;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipKey implements Serializable {
    @Column(name = "user1_id")
    Long senderId;
    @Column(name="user2_id")
    Long receiverId;
}
