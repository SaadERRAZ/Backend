package com.forumensak.api.payload;

import com.forumensak.api.model.User;
import com.forumensak.api.model.social.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ConversationPayload {
    long id;
    String Username;
    String img;
    Message lastMessage;
    Instant date;

}