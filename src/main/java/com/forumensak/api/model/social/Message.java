package com.forumensak.api.model.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.Cv;
import com.forumensak.api.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String message;
    private boolean sended;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private Conversation conversation;
}
